package com.phonestore.dao;

import com.phonestore.context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            return rs.next(); // Trả về true nếu tìm thấy (đã thích)
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
            // Lỗi có thể xảy ra nếu "thích" trùng (unique_user_product)
            System.err.println("Lỗi khi thêm vào Wishlist: " + e.getMessage());
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
}