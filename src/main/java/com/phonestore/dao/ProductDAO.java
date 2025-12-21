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

    // Đóng kết nối để tránh leak resource
    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * LẤY CHI TIẾT SẢN PHẨM (ĐÃ NÂNG CẤP)
     * Thêm b.slug và b.category_id để phục vụ Breadcrumbs
     */
    public Product getProductById(int productId) {
        String query = "SELECT p.*, " +
                "       b.name as brand_name, b.logo_url, " +
                "       b.slug as brand_slug, b.category_id " +
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
                brand.setSlug(rs.getString("brand_slug"));
                brand.setCategoryId(rs.getInt("category_id"));

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
                product.setStatus(rs.getInt("status"));
                return product;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    // Lấy thông tin Dòng sản phẩm (Series) theo ID
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
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    // Lấy danh sách các biến thể (Variant) cùng Series và Model (ví dụ: iPhone 15 Pro 128GB, 256GB...)
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

    // Lấy danh sách màu sắc có sẵn của một Dòng sản phẩm (Series)
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

    // Lấy danh sách ảnh Gallery (ảnh phụ) của sản phẩm
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

    // Lấy danh sách sản phẩm nhóm theo Thương hiệu (Dùng cho trang chủ hoặc trang Brand tổng hợp)
    public Map<Brand, List<Product>> getProductsGroupedByBrand() {
        Map<Brand, List<Product>> productMap = new LinkedHashMap<>();
        String query = "SELECT " +
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
                product.setStatus(rs.getInt("status"));
                currentProductList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return productMap;
    }

    // Lấy các sản phẩm liên quan cùng Thương hiệu (Hiển thị ở trang chi tiết sản phẩm)
    public List<Product> getRelatedProductsByBrand(int brandId, int currentProductId, int limit) {
        List<Product> relatedProducts = new ArrayList<>();
        String query = "SELECT " +
                "    p.*, b.name as brand_name, b.logo_url, " +
                "    AVG(pr.rating) as avgRating, " +
                "    COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "WHERE p.brand_id = ? AND p.id != ? " +
                "GROUP BY p.id " +
                "LIMIT ?";

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, brandId);
            ps.setInt(2, currentProductId);
            ps.setInt(3, limit);
            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setSalePrice(rs.getDouble("sale_price"));
                product.setThumbnailUrl(rs.getString("thumbnail_url"));
                product.setAvgRating(rs.getDouble("avgRating"));
                product.setReviewCount(rs.getInt("reviewCount"));
                relatedProducts.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return relatedProducts;
    }

    /**
     * Lấy danh sách sản phẩm theo Danh mục (Category) có hỗ trợ SẮP XẾP
     * @param categoryId ID danh mục
     * @param sortType Kiểu sắp xếp (price_asc, price_desc, popular)
     */
    public List<Product> getProductsByCategoryId(int categoryId, String sortType) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT " +
                "    p.*, b.name as brand_name, b.logo_url, " +
                "    AVG(pr.rating) as avgRating, " +
                "    COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "WHERE b.category_id = ? " +
                "GROUP BY p.id" +
                getOrderByClause(sortType); // Thêm mệnh đề ORDER BY động

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setSalePrice(rs.getDouble("sale_price"));
                product.setThumbnailUrl(rs.getString("thumbnail_url"));
                product.setAvgRating(rs.getDouble("avgRating"));
                product.setReviewCount(rs.getInt("reviewCount"));
                product.setStatus(rs.getInt("status"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return products;
    }

    /**
     * Lấy danh sách sản phẩm theo Slug Thương hiệu (Brand) có hỗ trợ SẮP XẾP
     * @param brandSlug Slug thương hiệu (ví dụ: 'apple', 'samsung')
     * @param sortType Kiểu sắp xếp
     */
    public List<Product> getProductsByBrandSlug(String brandSlug, String sortType) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT " +
                "    p.*, b.name as brand_name, b.logo_url, " +
                "    AVG(pr.rating) as avgRating, " +
                "    COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "WHERE b.slug = ? " +
                "GROUP BY p.id" +
                getOrderByClause(sortType); // Thêm mệnh đề ORDER BY động

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, brandSlug);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setSalePrice(rs.getDouble("sale_price"));
                product.setThumbnailUrl(rs.getString("thumbnail_url"));
                product.setAvgRating(rs.getDouble("avgRating"));
                product.setReviewCount(rs.getInt("reviewCount"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return products;
    }

    // --- CÁC HÀM CRUD DÀNH CHO ADMIN ---

    // Thêm sản phẩm mới
    public void insertProduct(Product p) {
        String sql = "INSERT INTO Product(name, description, price, sale_price, thumbnail_url, brand_id, series_id, model, storage, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setDouble(4, p.getSalePrice());
            ps.setString(5, p.getThumbnailUrl());
            ps.setInt(6, p.getBrand().getId());
            ps.setInt(7, p.getSeriesId());
            ps.setString(8, p.getModel());
            ps.setString(9, p.getStorage());
            ps.setInt(10, p.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    // Cập nhật thông tin sản phẩm
    public void updateProduct(Product p) {
        String sql = "UPDATE Product SET name=?, description=?, price=?, sale_price=?, thumbnail_url=?, brand_id=?, series_id=?, model=?, storage=?, status=? WHERE id=?";

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setDouble(4, p.getSalePrice());
            ps.setString(5, p.getThumbnailUrl());
            ps.setInt(6, p.getBrand().getId());
            ps.setInt(7, p.getSeriesId());
            ps.setString(8, p.getModel());
            ps.setString(9, p.getStorage());
            ps.setInt(10, p.getStatus());
            ps.setInt(11, p.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    // Xóa sản phẩm (Xóa cứng khỏi DB)
    public void deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    // Ẩn/Hiện sản phẩm (Thay đổi trạng thái status)
    public void toggleStatus(int id) {
        String sql = "UPDATE Product SET status = CASE WHEN status = 1 THEN 0 ELSE 1 END WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    // --- HÀM PHỤ TRỢ: TẠO CÂU LỆNH SẮP XẾP SQL ---
    private String getOrderByClause(String sortType) {
        if (sortType == null) return " ORDER BY reviewCount DESC, avgRating DESC";

        switch (sortType) {
            case "price_asc":
                return " ORDER BY COALESCE(p.sale_price, p.price) ASC"; // Giá thấp -> cao (ưu tiên giá sale)
            case "price_desc":
                return " ORDER BY COALESCE(p.sale_price, p.price) DESC"; // Giá cao -> thấp
            case "popular":
                return " ORDER BY reviewCount DESC, avgRating DESC"; // Phổ biến (nhiều review, điểm cao)
            default:
                return " ORDER BY reviewCount DESC, avgRating DESC";
        }
    }
}