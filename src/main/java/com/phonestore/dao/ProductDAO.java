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

    // --- [MỚI] HÀM LẤY TẤT CẢ SẢN PHẨM (Dùng cho trang danh sách mặc định) ---
    public List<Product> getAllProducts(String sortType) {
        List<Product> products = new ArrayList<>();
        // Query tương tự getProductsByCategoryId nhưng không có WHERE
        String query = "SELECT " +
                "    p.*, b.name as brand_name, b.logo_url, " +
                "    AVG(pr.rating) as avgRating, " +
                "    COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "GROUP BY p.id" +
                getOrderByClause(sortType);

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = mapRowToProduct(rs); // Tách hàm map row cho gọn
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return products;
    }

    // --- CÁC HÀM LẤY CHI TIẾT (Giữ nguyên) ---
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

    // ... (Giữ nguyên các hàm getVariants, getColors, getGallery...) ...

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

    // Lấy danh sách sản phẩm nhóm theo Thương hiệu (Dùng cho trang chủ)
    public Map<Brand, List<Product>> getProductsGroupedByBrand() {
        Map<Brand, List<Product>> productMap = new LinkedHashMap<>();
        String query = "SELECT " +
                "    p.*, b.name as brand_name, b.logo_url, b.slug as brand_slug, " +
                "    AVG(pr.rating) as avgRating, " +
                "    COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "WHERE p.status = 1 " +

                "GROUP BY p.id " +
                "ORDER BY b.id, p.price DESC";
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
                    currentBrand.setSlug(rs.getString("brand_slug"));

                    currentProductList = new ArrayList<>();
                    productMap.put(currentBrand, currentProductList);
                }

                Product product = mapRowToProduct(rs);
                product.setBrand(currentBrand);
                currentProductList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return productMap;
    }

    // --- HÀM DÀNH RIÊNG CHO ADMIN (Lấy cả sản phẩm ẩn) ---
    public Map<Brand, List<Product>> getAllProductsGroupedByBrandForAdmin() {
        Map<Brand, List<Product>> productMap = new LinkedHashMap<>();

        // Query này KHÔNG CÓ "WHERE p.status = 1"
        String query = "SELECT " +
                "    p.*, b.name as brand_name, b.logo_url, b.slug as brand_slug, " +
                "    AVG(pr.rating) as avgRating, " +
                "    COUNT(pr.id) as reviewCount " +
                "FROM Product p " +
                "JOIN Brand b ON p.brand_id = b.id " +
                "LEFT JOIN ProductReview pr ON p.id = pr.product_id " +
                "GROUP BY p.id " +
                "ORDER BY b.id, p.id DESC"; // Sắp xếp theo ID giảm dần để thấy sp mới thêm

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
                    currentBrand.setSlug(rs.getString("brand_slug"));

                    currentProductList = new ArrayList<>();
                    productMap.put(currentBrand, currentProductList);
                }

                Product product = mapRowToProduct(rs);
                product.setBrand(currentBrand);
                currentProductList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return productMap;
    }

    // ... (Giữ nguyên getRelatedProductsByBrand) ...
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

    // --- CÁC HÀM LẤY LIST THEO ĐIỀU KIỆN (Có Sort) ---

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
                getOrderByClause(sortType);

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return products;
    }

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
                getOrderByClause(sortType);

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, brandSlug);
            rs = ps.executeQuery();
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return products;
    }

    // --- CÁC HÀM ADMIN CRUD (Giữ nguyên) ---
    // ... (insert, update, delete, toggleStatus giữ nguyên như code cũ) ...
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


    // --- HÀM HELPER: MAP ROW TO PRODUCT OBJECT ---
    // (Giúp code gọn hơn, tránh lặp lại logic gán dữ liệu)
    private Product mapRowToProduct(ResultSet rs) throws Exception {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        // Check if description column exists in ResultSet to avoid error in some queries
        try { p.setDescription(rs.getString("description")); } catch(Exception e) {}
        p.setPrice(rs.getDouble("price"));
        p.setSalePrice(rs.getDouble("sale_price"));
        p.setThumbnailUrl(rs.getString("thumbnail_url"));
        p.setSeriesId(rs.getInt("series_id"));
        p.setModel(rs.getString("model"));
        p.setStorage(rs.getString("storage"));
        p.setStatus(rs.getInt("status"));

        // Map rating & review count
        p.setAvgRating(rs.getDouble("avgRating"));
        p.setReviewCount(rs.getInt("reviewCount"));

        // Map brand (nếu có join)
        try {
            Brand b = new Brand();
            b.setId(rs.getInt("brand_id"));
            b.setName(rs.getString("brand_name"));
            b.setLogoUrl(rs.getString("logo_url"));
            p.setBrand(b);
        } catch (Exception e) {}

        return p;
    }

    private String getOrderByClause(String sortType) {
        if (sortType == null) return " ORDER BY reviewCount DESC, avgRating DESC";
        switch (sortType) {
            case "price_asc": return " ORDER BY COALESCE(p.sale_price, p.price) ASC";
            case "price_desc": return " ORDER BY COALESCE(p.sale_price, p.price) DESC";
            case "popular": return " ORDER BY reviewCount DESC, avgRating DESC";
            default: return " ORDER BY reviewCount DESC, avgRating DESC";
        }
    }

    public List<String> searchKeywords(String keyword, int limit) {
        List<String> keywords = new ArrayList<>();
        String pattern = "%" + keyword.toLowerCase() + "%";

        // Câu lệnh SQL: Tìm tên Thương hiệu hoặc Danh mục khớp với từ khóa
        String query = "SELECT name FROM Brand WHERE LOWER(name) LIKE ? " +
                "UNION " +
                "SELECT name FROM Category WHERE LOWER(name) LIKE ? " +
                "LIMIT ?";

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setInt(3, limit);
            rs = ps.executeQuery();

            while (rs.next()) {
                keywords.add(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return keywords;
    }

    /**
     * Tìm kiếm sản phẩm có giới hạn số lượng (Dùng cho Gợi ý Live Search)
     * @param keyword Từ khóa tìm kiếm
     * @param limit Số lượng kết quả tối đa muốn lấy (ví dụ: 5)
     */
    public List<Product> searchProductsLimit(String keyword, int limit) {
        List<Product> products = new ArrayList<>();
        // SQL: Tìm theo tên HOẶC model, và dùng LIMIT để giới hạn số dòng
        String query = "SELECT id, name, price, sale_price, thumbnail_url " +
                "FROM Product " +
                "WHERE LOWER(name) LIKE ? OR LOWER(model) LIKE ? " +
                "LIMIT ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            String pattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setInt(3, limit); // Tham số giới hạn (VD: 5)

            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setSalePrice(rs.getDouble("sale_price"));
                p.setThumbnailUrl(rs.getString("thumbnail_url"));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return products;
    }

    // --- [MỚI] Hàm lấy màu chỉ của riêng sản phẩm đó ---
    public List<Color> getColorsByProductId(int productId) {
        List<Color> colors = new ArrayList<>();
        // Query này chỉ lấy màu được map trực tiếp với Product ID
        String query = "SELECT c.id, c.name, c.hex_code " +
                "FROM Color c " +
                "JOIN ProductColor pc ON c.id = pc.color_id " +
                "WHERE pc.product_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
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

}

