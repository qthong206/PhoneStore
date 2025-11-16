package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.Brand;
import com.phonestore.model.Color;
import com.phonestore.model.Product;
import com.phonestore.model.ProductSeries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- (Các hàm getProductById, ... giữ nguyên) ---

    public Product getProductById(int productId) {
        String query = "SELECT p.*, b.name as brand_name, b.logo_url " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "WHERE p.id = ?";
        try {
            conn = DBContext.getConnection();
            if (conn == null) return null;
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("brand_id"));
                brand.setName(rs.getString("brand_name"));
                brand.setLogoUrl(rs.getString("logo_url"));
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setSalePrice(rs.getDouble("sale_price"));
                product.setThumbnailUrl(rs.getString("thumbnail_url"));
                product.setBrand(brand);
                product.setSeriesId(rs.getInt("series_id"));
                product.setModel(rs.getString("model"));
                product.setStorage(rs.getString("storage"));
                return product;
            }
        } catch (Exception e) {
            System.err.println("LỖI TRONG ProductDAO.getProductById:");
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    public ProductSeries getProductSeriesById(int seriesId) {
        String query = "SELECT * FROM ProductSeries WHERE id = ?";
        try {
            conn = DBContext.getConnection();
            if (conn == null) return null;
            ps = conn.prepareStatement(query);
            ps.setInt(1, seriesId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ProductSeries series = new ProductSeries();
                series.setId(rs.getInt("id"));
                series.setName(rs.getString("name"));
                return series;
            }
        } catch (Exception e) {
            System.err.println("LỖI TRONG ProductDAO.getProductSeriesById:");
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    public List<Product> getVariantsBySeriesAndModel(int seriesId, String model) {
        List<Product> variants = new ArrayList<>();
        String query = "SELECT id, storage, price, sale_price FROM Product " +
                "WHERE series_id = ? AND model = ? " +
                "ORDER BY id";
        try {
            conn = DBContext.getConnection();
            if (conn == null) return variants;
            ps = conn.prepareStatement(query);
            ps.setInt(1, seriesId);
            ps.setString(2, model);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product variant = new Product();
                variant.setId(rs.getInt("id"));
                variant.setStorage(rs.getString("storage"));
                variant.setPrice(rs.getDouble("price"));
                variant.setSalePrice(rs.getDouble("sale_price"));
                variants.add(variant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return variants;
    }

    public List<Color> getColorsBySeriesId(int seriesId) {
        List<Color> colors = new ArrayList<>();
        String query = "SELECT DISTINCT c.id, c.name, c.hex_code " +
                "FROM Color c " +
                "JOIN ProductColor pc ON c.id = pc.color_id " +
                "JOIN Product p ON pc.product_id = p.id " +
                "WHERE p.series_id = ?";
        try {
            conn = DBContext.getConnection();
            if (conn == null) return colors;
            ps = conn.prepareStatement(query);
            ps.setInt(1, seriesId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Color color = new Color();
                color.setId(rs.getInt("id"));
                color.setName(rs.getString("name"));
                color.setHexCode(rs.getString("hex_code"));
                colors.add(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return colors;
    }

    public List<String> getGalleryImagesByProductId(int productId) {
        List<String> galleryImages = new ArrayList<>();
        String query = "SELECT image_url FROM ProductGallery " +
                "WHERE product_id = ? " +
                "ORDER BY sort_order ASC";
        try {
            conn = DBContext.getConnection();
            if (conn == null) return galleryImages;
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                galleryImages.add(rs.getString("image_url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return galleryImages;
    }


    /**
     * Lấy sản phẩm cho Trang chủ (ĐÃ CẬP NHẬT)
     */
    public Map<Brand, List<Product>> getProductsGroupedByBrand() {
        Map<Brand, List<Product>> productMap = new LinkedHashMap<>();

        // 1. SỬA QUERY: Thêm LEFT JOIN và GROUP BY
        String query = "SELECT " +
                "    p.*, b.name as brand_name, b.logo_url, " +
                "    AVG(pr.rating) as avgRating, " +
                "    COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "GROUP BY p.id, b.name, b.logo_url " +
                "ORDER BY b.id, p.id";

        // (GROUP BY p.id là đủ cho MySQL)
        query = "SELECT " +
                "    p.*, b.name as brand_name, b.logo_url, " +
                "    AVG(pr.rating) as avgRating, " +
                "    COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "GROUP BY p.id " +
                "ORDER BY b.id, p.id";

        try {
            conn = DBContext.getConnection();
            if (conn == null) return productMap;

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            Brand currentBrand = null;
            List<Product> currentProductList = null;

            while (rs.next()) {
                int brandId = rs.getInt("brand_id");

                if (currentBrand == null || brandId != currentBrand.getId()) {
                    currentBrand = new Brand();
                    currentBrand.setId(brandId);
                    currentBrand.setName(rs.getString("brand_name"));
                    currentBrand.setLogoUrl(rs.getString("logo_url"));
                    currentProductList = new ArrayList<>();
                    productMap.put(currentBrand, currentProductList);
                }

                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setSalePrice(rs.getDouble("sale_price"));
                product.setThumbnailUrl(rs.getString("thumbnail_url"));
                product.setBrand(currentBrand);
                product.setSeriesId(rs.getInt("series_id"));
                product.setModel(rs.getString("model"));
                product.setStorage(rs.getString("storage"));
                product.setAvgRating(rs.getDouble("avgRating"));
                product.setReviewCount(rs.getInt("reviewCount"));

                currentProductList.add(product);
            }
        } catch (Exception e) {
            System.err.println("LỖI TRONG ProductDAO.getProductsGroupedByBrand:");
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return productMap;
    }
}