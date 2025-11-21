package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WishlistDAO {

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
     * Kiểm tra xem User đã "thích" sản phẩm này chưa
     */
    public boolean isProductInWishlist(int userId, int productId) {
        String query = "SELECT 1 FROM Wishlist WHERE user_id = ? AND product_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return false;
    }

    /**
     * Thêm sản phẩm vào Wishlist
     */
    public boolean addToWishlist(int userId, int productId) {
        String query = "INSERT INTO Wishlist (user_id, product_id) VALUES (?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            // Lỗi thường gặp: trùng lặp (Duplicate entry) nếu code check không kỹ
            System.err.println("Lỗi thêm Wishlist: " + e.getMessage());
        } finally {
            closeConnections();
        }
        return false;
    }

    /**
     * Xóa sản phẩm khỏi Wishlist
     */
    public boolean removeFromWishlist(int userId, int productId) {
        String query = "DELETE FROM Wishlist WHERE user_id = ? AND product_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return false;
    }

    /**
     * Lấy tất cả ID sản phẩm (Dùng để tô màu trái tim ở trang chủ/chi tiết)
     */
    public Set<Integer> getWishlistProductIds(int userId) {
        Set<Integer> productIds = new HashSet<>();
        String query = "SELECT product_id FROM Wishlist WHERE user_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                productIds.add(rs.getInt("product_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return productIds;
    }

    /**
     * --- HÀM MỚI QUAN TRỌNG ---
     * Lấy danh sách ĐỐI TƯỢNG Sản phẩm đầy đủ (Dùng cho trang User Dashboard)
     */
    public List<Product> getWishlistItems(int userId) {
        List<Product> list = new ArrayList<>();
        // JOIN bảng Wishlist với bảng Product để lấy thông tin chi tiết
        // Lấy 4 sản phẩm mới nhất để hiển thị ở Dashboard
        String query = "SELECT p.id, p.name, p.thumbnail_url, p.price, p.sale_price " +
                "FROM Product p " +
                "JOIN Wishlist w ON p.id = w.product_id " +
                "WHERE w.user_id = ? " +
                "ORDER BY w.created_at DESC LIMIT 4";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setThumbnailUrl(rs.getString("thumbnail_url"));
                p.setPrice(rs.getDouble("price"));
                p.setSalePrice(rs.getDouble("sale_price"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }
}