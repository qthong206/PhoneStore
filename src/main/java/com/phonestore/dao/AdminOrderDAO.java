package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.ProductOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminOrderDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    private void close() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception ignored) {}
    }

    private static final String BASE_QUERY =
            "SELECT o.*, u.username " +
                    "FROM ProductOrder o " +
                    "JOIN User u ON o.user_id = u.id ";

    private ProductOrder map(ResultSet rs) throws SQLException {
        ProductOrder o = new ProductOrder();
        o.setId(rs.getInt("id"));
        o.setUserId(rs.getInt("user_id"));
        o.setRecipientName(rs.getString("recipient_name"));
        o.setRecipientPhone(rs.getString("recipient_phone"));
        o.setShippingAddress(rs.getString("shipping_address"));
        o.setPaymentMethod(rs.getString("payment_method"));
        o.setTotalAmount(rs.getDouble("total_amount"));
        o.setStatus(rs.getString("status"));
        o.setCreatedAt(rs.getTimestamp("created_at"));
        o.setUsername(rs.getString("username")); // field phụ cho admin
        return o;
    }

    // 📌 Lấy toàn bộ đơn (admin)
    public List<ProductOrder> getAllOrders(String status) {
        List<ProductOrder> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_QUERY);

        if (status != null && !status.equals("all")) {
            sql.append(" WHERE o.status = ?");
        }
        sql.append(" ORDER BY o.created_at DESC");

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql.toString());
            if (status != null && !status.equals("all")) {
                ps.setString(1, status);
            }
            rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }

    // 📌 Cập nhật trạng thái đơn
    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE ProductOrder SET status=? WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return false;
    }
}
