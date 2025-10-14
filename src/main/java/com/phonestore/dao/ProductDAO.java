package com.phonestore.dao;

import com.phonestore.model.Brand;
import com.phonestore.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {

    public Map<Brand, List<Product>> getProductsGroupedByBrand() {
        Map<Brand, List<Product>> productMap = new LinkedHashMap<>();
        String sql = "SELECT p.*, b.name as brand_name " +
                "FROM Product p JOIN Brand b ON p.brand_id = b.id " +
                "ORDER BY b.id, p.id";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Tạo đối tượng Brand
                int brandId = rs.getInt("brand_id");
                String brandName = rs.getString("brand_name");
                Brand brand = new Brand(brandId, brandName);

                // Tạo đối tượng Product
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setSalePrice(rs.getDouble("sale_price"));
                product.setThumbnailUrl(rs.getString("thumbnail_url"));
                product.setDescription(rs.getString("description"));
                product.setBrand(brand);

                // Thêm product vào Map, nhóm theo brand
                // Dùng computeIfAbsent để tự động tạo list mới nếu brand chưa có trong map
                productMap.computeIfAbsent(brand, k -> new ArrayList<>()).add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productMap;
    }

    // Thêm phương thức này vào bên trong lớp ProductDAO.java

    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT p.*, b.name as brand_name " +
                "FROM Product p JOIN Brand b ON p.brand_id = b.id " +
                "WHERE p.id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId); // Gán id vào dấu ? trong câu lệnh SQL

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Tạo đối tượng Brand
                    Brand brand = new Brand();
                    brand.setId(rs.getInt("brand_id"));
                    brand.setName(rs.getString("brand_name"));

                    // Tạo đối tượng Product từ dữ liệu lấy được
                    product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setSalePrice(rs.getDouble("sale_price"));
                    product.setThumbnailUrl(rs.getString("thumbnail_url"));
                    product.setDescription(rs.getString("description"));
                    product.setBrand(brand);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product; // Trả về sản phẩm tìm được, hoặc null nếu không có
    }
}