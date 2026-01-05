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

    // =========================================================================
    // PHẦN 1: CÁC HÀM LỌC & TÌM KIẾM (MASTER FILTER)
    // =========================================================================

    public List<Product> filterProducts(Integer brandId, Double minPrice, Double maxPrice, String search, String sortType, int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.*, b.name as brand_name, b.logo_url, " +
                        "AVG(pr.rating) as avgRating, COUNT(pr.id) as reviewCount " +
                        "FROM Product p " +
                        "JOIN Brand b ON p.brand_id = b.id " +
                        "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                        "WHERE p.status = 1 ");

        if (brandId != null && brandId > 0) sql.append(" AND p.brand_id = ? ");
        if (minPrice != null && minPrice >= 0) sql.append(" AND COALESCE(NULLIF(p.sale_price, 0), p.price) >= ? ");
        if (maxPrice != null && maxPrice > 0) sql.append(" AND COALESCE(NULLIF(p.sale_price, 0), p.price) <= ? ");
        if (search != null && !search.isEmpty()) sql.append(" AND (LOWER(p.name) LIKE ? OR LOWER(p.model) LIKE ?) ");

        sql.append(" GROUP BY p.id ");
        sql.append(getOrderByClause(sortType));
        sql.append(" LIMIT ? OFFSET ? ");

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql.toString());
            int index = 1;
            if (brandId != null && brandId > 0) ps.setInt(index++, brandId);
            if (minPrice != null && minPrice >= 0) ps.setDouble(index++, minPrice);
            if (maxPrice != null && maxPrice > 0) ps.setDouble(index++, maxPrice);
            if (search != null && !search.isEmpty()) {
                String p = "%" + search.toLowerCase() + "%";
                ps.setString(index++, p);
                ps.setString(index++, p);
            }
            ps.setInt(index++, pageSize);
            ps.setInt(index++, (page - 1) * pageSize);

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToProduct(rs));
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return list;
    }

    public int countProducts(Integer brandId, Double minPrice, Double maxPrice, String search) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Product p WHERE p.status = 1 ");

        if (brandId != null && brandId > 0) sql.append(" AND p.brand_id = ? ");
        if (minPrice != null && minPrice >= 0) sql.append(" AND COALESCE(NULLIF(p.sale_price, 0), p.price) >= ? ");
        if (maxPrice != null && maxPrice > 0) sql.append(" AND COALESCE(NULLIF(p.sale_price, 0), p.price) <= ? ");
        if (search != null && !search.isEmpty()) sql.append(" AND (LOWER(p.name) LIKE ? OR LOWER(p.model) LIKE ?) ");

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql.toString());
            int index = 1;
            if (brandId != null && brandId > 0) ps.setInt(index++, brandId);
            if (minPrice != null && minPrice >= 0) ps.setDouble(index++, minPrice);
            if (maxPrice != null && maxPrice > 0) ps.setDouble(index++, maxPrice);
            if (search != null && !search.isEmpty()) {
                String p = "%" + search.toLowerCase() + "%";
                ps.setString(index++, p);
                ps.setString(index++, p);
            }
            rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return count;
    }

    // =========================================================================
    // PHẦN 2: CÁC HÀM GET DỮ LIỆU CƠ BẢN
    // =========================================================================

    public Product getProductById(int productId) {
        // [UPDATE DB] Lấy p.category_id, bỏ b.category_id
        String query = "SELECT p.*, b.name as brand_name, b.logo_url, b.slug as brand_slug " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "WHERE p.id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToProduct(rs);
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return null;
    }

    public List<Product> getAllProducts(String sortType) {
        return filterProducts(null, null, null, null, sortType, 1, 1000);
    }

    public List<Product> getProductsByCategoryId(int categoryId, String sortType) {
        List<Product> products = new ArrayList<>();
        // [UPDATE DB] WHERE p.category_id = ?
        String query = "SELECT p.*, b.name as brand_name, b.logo_url, " +
                "AVG(pr.rating) as avgRating, COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "WHERE p.category_id = ? AND p.status = 1 " +
                "GROUP BY p.id " +
                getOrderByClause(sortType);
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return products;
    }

    public Map<Brand, List<Product>> getProductsGroupedByBrand() {
        Map<Brand, List<Product>> productMap = new LinkedHashMap<>();
        String query = "SELECT p.*, b.name as brand_name, b.logo_url, b.slug as brand_slug, " +
                "AVG(pr.rating) as avgRating, COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "WHERE p.status = 1 " +
                "GROUP BY p.id " +
                "ORDER BY b.id, p.id DESC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            Brand currentBrand = null;
            List<Product> currentList = null;
            while (rs.next()) {
                int brandId = rs.getInt("brand_id");
                if (currentBrand == null || brandId != currentBrand.getId()) {
                    currentBrand = new Brand();
                    currentBrand.setId(brandId);
                    currentBrand.setName(rs.getString("brand_name"));
                    currentBrand.setLogoUrl(rs.getString("logo_url"));
                    currentBrand.setSlug(rs.getString("brand_slug"));
                    currentList = new ArrayList<>();
                    productMap.put(currentBrand, currentList);
                }
                Product p = mapRowToProduct(rs);
                p.setBrand(currentBrand);
                currentList.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return productMap;
    }

    public List<Product> getRelatedProductsByBrand(int brandId, int currentProductId, int limit) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT p.*, b.name as brand_name, b.logo_url, " +
                "AVG(pr.rating) as avgRating, COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "WHERE p.brand_id = ? AND p.id != ? AND p.status = 1 " +
                "GROUP BY p.id LIMIT ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, brandId);
            ps.setInt(2, currentProductId);
            ps.setInt(3, limit);
            rs = ps.executeQuery();
            while(rs.next()) list.add(mapRowToProduct(rs));
        } catch(Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return list;
    }

    // =========================================================================
    // PHẦN 3: LOGIC MÀU SẮC & BIẾN THỂ (QUAN TRỌNG CHO TRANG DETAIL)
    // =========================================================================

    public ProductSeries getProductSeriesById(int seriesId) {
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement("SELECT * FROM ProductSeries WHERE id = ?");
            ps.setInt(1, seriesId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ProductSeries s = new ProductSeries();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                return s;
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return null;
    }

    public List<Product> getVariantsBySeriesAndModel(int seriesId, String model) {
        List<Product> variants = new ArrayList<>();
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement("SELECT id, storage, price, sale_price FROM Product WHERE series_id = ? AND model = ? ORDER BY id");
            ps.setInt(1, seriesId); ps.setString(2, model);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product v = new Product();
                v.setId(rs.getInt("id"));
                v.setStorage(rs.getString("storage"));
                v.setPrice(rs.getDouble("price"));
                v.setSalePrice(rs.getDouble("sale_price"));
                variants.add(v);
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return variants;
    }

    /**
     * [CÁCH 1] Lấy màu của chính sản phẩm này.
     * Dùng để biết sản phẩm đang xem có màu gì (VD: Đen).
     */
    public List<Color> getColorsByProductId(int productId) {
        List<Color> colors = new ArrayList<>();
        String query = "SELECT c.id, c.name, c.hex_code FROM Color c JOIN ProductColor pc ON c.id = pc.color_id WHERE pc.product_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Color c = new Color();
                c.setId(rs.getInt("id")); c.setName(rs.getString("name")); c.setHexCode(rs.getString("hex_code"));
                colors.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return colors;
    }

    /**
     * [CÁCH 2 - MỚI] Lấy danh sách màu của các SP anh em (Cùng Series + Cùng Model).
     * Dùng để hiển thị các nút chọn màu (VD: Đen, Trắng, Vàng).
     */
    public List<Color> getSameModelColors(int seriesId, String model) {
        List<Color> colors = new ArrayList<>();
        String query = "SELECT DISTINCT c.id, c.name, c.hex_code " +
                "FROM Color c " +
                "JOIN ProductColor pc ON c.id = pc.color_id " +
                "JOIN Product p ON pc.product_id = p.id " +
                "WHERE p.series_id = ? AND p.model = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, seriesId);
            ps.setString(2, model);
            rs = ps.executeQuery();
            while (rs.next()) {
                Color c = new Color();
                c.setId(rs.getInt("id")); c.setName(rs.getString("name")); c.setHexCode(rs.getString("hex_code"));
                colors.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return colors;
    }

    public List<String> getGalleryImagesByProductId(int productId) {
        List<String> list = new ArrayList<>();
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement("SELECT image_url FROM ProductGallery WHERE product_id = ? ORDER BY sort_order ASC");
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while(rs.next()) list.add(rs.getString("image_url"));
        } catch(Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return list;
    }

    // =========================================================================
    // PHẦN 4: ADMIN & CRUD
    // =========================================================================

    public Map<Brand, List<Product>> getAllProductsGroupedByBrandForAdmin() {
        Map<Brand, List<Product>> productMap = new LinkedHashMap<>();
        String query = "SELECT p.*, b.name as brand_name, b.logo_url, b.slug as brand_slug " +
                "FROM Product p JOIN Brand b ON p.brand_id = b.id " +
                "ORDER BY b.id, p.id DESC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            Brand currentBrand = null;
            List<Product> currentList = null;
            while(rs.next()){
                int brandId = rs.getInt("brand_id");
                if (currentBrand == null || brandId != currentBrand.getId()){
                    currentBrand = new Brand();
                    currentBrand.setId(brandId);
                    currentBrand.setName(rs.getString("brand_name"));
                    currentList = new ArrayList<>();
                    productMap.put(currentBrand, currentList);
                }
                currentList.add(mapRowToProduct(rs));
            }
        } catch(Exception e){ e.printStackTrace(); } finally { closeConnections(); }
        return productMap;
    }

    public int insertProduct(Product p) {
        String sql = "INSERT INTO Product(name, description, price, sale_price, thumbnail_url, " +
                "brand_id, series_id, model, storage, status, stock_quantity, category_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = 0;
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setDouble(4, p.getSalePrice());
            ps.setString(5, p.getThumbnailUrl());
            ps.setInt(6, p.getBrand().getId());

            if (p.getSeriesId() > 0) {
                ps.setInt(7, p.getSeriesId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }

            ps.setString(8, p.getModel());
            ps.setString(9, p.getStorage());
            ps.setInt(10, p.getStatus());
            ps.setInt(11, p.getStockQuantity());
            ps.setInt(12, p.getCategoryId());

            ps.executeUpdate();

            // Lấy ID vừa chèn vào
            var rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return generatedId;
    }

    public void updateProduct(Product p) {
        String sql = "UPDATE Product SET name=?, description=?, price=?, sale_price=?, " +
                "thumbnail_url=?, brand_id=?, series_id=?, model=?, storage=?, " +
                "status=?, stock_quantity=?, category_id=? WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setDouble(4, p.getSalePrice());
            ps.setString(5, p.getThumbnailUrl());
            ps.setInt(6, p.getBrand().getId());

            if (p.getSeriesId() > 0) {
                ps.setInt(7, p.getSeriesId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }

            ps.setString(8, p.getModel());
            ps.setString(9, p.getStorage());
            ps.setInt(10, p.getStatus());
            ps.setInt(11, p.getStockQuantity());
            ps.setInt(12, p.getCategoryId());
            ps.setInt(13, p.getId());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public void deleteProduct(int id) {
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement("DELETE FROM Product WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
    }

    public void toggleStatus(int id) {
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement("UPDATE Product SET status = CASE WHEN status = 1 THEN 0 ELSE 1 END WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
    }

    // =========================================================================
    // PHẦN 5: HELPER & SEARCH
    // =========================================================================

    public List<String> searchKeywords(String keyword, int limit) {
        List<String> keywords = new ArrayList<>();
        String pattern = "%" + keyword.toLowerCase() + "%";
        String query = "SELECT name FROM Brand WHERE LOWER(name) LIKE ? UNION SELECT name FROM Category WHERE LOWER(name) LIKE ? LIMIT ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, pattern); ps.setString(2, pattern); ps.setInt(3, limit);
            rs = ps.executeQuery();
            while (rs.next()) keywords.add(rs.getString("name"));
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return keywords;
    }

    public List<Product> searchProductsLimit(String keyword, int limit) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, name, price, sale_price, thumbnail_url, model FROM Product WHERE status = 1 AND (LOWER(name) LIKE ? OR LOWER(model) LIKE ?) LIMIT ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            String pattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, pattern); ps.setString(2, pattern); ps.setInt(3, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setSalePrice(rs.getDouble("sale_price"));
                p.setThumbnailUrl(rs.getString("thumbnail_url"));
                p.setModel(rs.getString("model"));
                products.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return products;
    }

    private Product mapRowToProduct(ResultSet rs) throws Exception {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));
        p.setSalePrice(rs.getDouble("sale_price"));
        p.setThumbnailUrl(rs.getString("thumbnail_url"));
        p.setModel(rs.getString("model"));
        p.setStorage(rs.getString("storage"));
        p.setStatus(rs.getInt("status"));

        // [UPDATE DB] Map category_id và stock_quantity
        try { p.setCategoryId(rs.getInt("category_id")); } catch(Exception e) {}
        try { p.setStockQuantity(rs.getInt("stock_quantity")); } catch(Exception e) {}

        try { p.setDescription(rs.getString("description")); } catch (Exception e) {}
        try { p.setSeriesId(rs.getInt("series_id")); } catch (Exception e) {}
        try { p.setAvgRating(rs.getDouble("avgRating")); } catch (Exception e) {}
        try { p.setReviewCount(rs.getInt("reviewCount")); } catch (Exception e) {}

        try {
            Brand b = new Brand();
            b.setId(rs.getInt("brand_id"));
            b.setName(rs.getString("brand_name"));
            b.setLogoUrl(rs.getString("logo_url"));
            try { b.setSlug(rs.getString("brand_slug")); } catch (Exception e) {}
            p.setBrand(b);
        } catch (Exception e) {}
        return p;
    }

    private String getOrderByClause(String sortType) {
        if (sortType == null) return " ORDER BY p.id DESC";
        switch (sortType) {
            case "price_asc": return " ORDER BY COALESCE(NULLIF(p.sale_price, 0), p.price) ASC";
            case "price_desc": return " ORDER BY COALESCE(NULLIF(p.sale_price, 0), p.price) DESC";
            case "name_asc": return " ORDER BY p.name ASC";
            case "popular": return " ORDER BY reviewCount DESC, avgRating DESC";
            default: return " ORDER BY p.id DESC";
        }
    }
}