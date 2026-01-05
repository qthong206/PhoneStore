package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO {

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

    /**
     * Lấy tất cả Brand (cho Header)
     */
    public List<Brand> getAllBrands() {
        List<Brand> brands = new ArrayList<>();
        String query = "SELECT * FROM Brand ORDER BY name ASC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Brand b = new Brand();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setSlug(rs.getString("slug"));
                b.setLogoUrl(rs.getString("logo_url"));
                brands.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return brands;
    }

    /**
     * Lấy danh sách Brand có sản phẩm thuộc Category cụ thể
     * Logic: Join bảng Brand -> Product -> check category_id
     */
    public List<Brand> getBrandsByCategoryId(int categoryId) {
        List<Brand> brands = new ArrayList<>();
        // Chỉ lấy những hãng ĐANG CÓ sản phẩm thuộc danh mục này
        String query = "SELECT DISTINCT b.* " +
                "FROM Brand b " +
                "JOIN Product p ON b.id = p.brand_id " +
                "WHERE p.category_id = ? " +
                "ORDER BY b.name ASC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Brand b = new Brand();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setSlug(rs.getString("slug"));
                b.setLogoUrl(rs.getString("logo_url"));
                brands.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return brands;
    }

    /**
     * Lấy Brand bằng 'slug' (VD: "apple")
     */
    public Brand getBrandBySlug(String slug) {
        String query = "SELECT * FROM Brand WHERE slug = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, slug);
            rs = ps.executeQuery();
            if (rs.next()) {
                Brand b = new Brand();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setSlug(rs.getString("slug"));
                b.setLogoUrl(rs.getString("logo_url"));
                return b;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    // --- CÁC HÀM CHO ADMIN ---

    public Brand getBrandById(int id) {
        String sql = "SELECT * FROM Brand WHERE id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Brand b = new Brand();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setSlug(rs.getString("slug"));
                b.setLogoUrl(rs.getString("logo_url"));
                return b;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    public void insertBrand(Brand b) {
        String sql = "INSERT INTO Brand(name, slug, logo_url) VALUES(?, ?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, b.getName());
            ps.setString(2, b.getSlug());
            ps.setString(3, b.getLogoUrl());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public void updateBrand(Brand b) {
        // [ĐÃ SỬA]: Xóa category_id và chỉnh lại chỉ số tham số (1, 2, 3, 4)
        String sql = "UPDATE Brand SET name=?, slug=?, logo_url=? WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, b.getName());
            ps.setString(2, b.getSlug());
            ps.setString(3, b.getLogoUrl());
            ps.setInt(4, b.getId()); // Chỉ số là 4 (không phải 5)
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public void deleteBrand(int id) {
        String sql = "DELETE FROM Brand WHERE id=?";
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
}