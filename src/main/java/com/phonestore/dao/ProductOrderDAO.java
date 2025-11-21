package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.ProductOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductOrderDAO {

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

    public int countOrdersByUserId(int userId) {
        // SỬA TÊN BẢNG THÀNH ProductOrder
        String query = "SELECT COUNT(*) FROM ProductOrder WHERE user_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return 0;
    }

    public double sumTotalSpentByUserId(int userId) {
        // SỬA TÊN BẢNG THÀNH ProductOrder
        String query = "SELECT SUM(total_amount) FROM ProductOrder WHERE user_id = ? AND status = 'delivered'";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return 0;
    }

    /**
     * QUERY CHUNG: Lấy đơn hàng + Tên/Ảnh sản phẩm đầu tiên
     * SỬA LỖI: Đã thống nhất tên bảng là 'OrderDetail' cho cả 2 dòng sub-query
     */
    private String BASE_QUERY =
            "SELECT o.*, " +
                    " (SELECT p.name FROM Product p JOIN OrderDetail od ON p.id = od.product_id WHERE od.order_id = o.id LIMIT 1) as first_p_name, " +
                    " (SELECT p.thumbnail_url FROM Product p JOIN OrderDetail od ON p.id = od.product_id WHERE od.order_id = o.id LIMIT 1) as first_p_img " +
                    "FROM ProductOrder o ";

    private ProductOrder mapRowToOrder(ResultSet rs) throws java.sql.SQLException {
        ProductOrder o = new ProductOrder();
        o.setId(rs.getInt("id"));
        o.setUserId(rs.getInt("user_id"));
        o.setRecipientName(rs.getString("recipient_name"));
        o.setCreatedAt(rs.getTimestamp("created_at"));
        o.setStatus(rs.getString("status"));
        o.setTotalAmount(rs.getDouble("total_amount"));
        o.setFirstProductName(rs.getString("first_p_name"));
        o.setFirstProductImage(rs.getString("first_p_img"));
        return o;
    }

    public List<ProductOrder> getRecentOrders(int userId) {
        List<ProductOrder> list = new ArrayList<>();
        String query = BASE_QUERY + "WHERE o.user_id = ? ORDER BY o.created_at DESC LIMIT 5";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToOrder(rs));
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return list;
    }

    public List<ProductOrder> getOrdersByUserId(int userId, String status) {
        List<ProductOrder> list = new ArrayList<>();
        StringBuilder query = new StringBuilder(BASE_QUERY + "WHERE o.user_id = ?");

        if (status != null && !status.isEmpty() && !status.equals("all")) {
            query.append(" AND o.status = ?");
        }
        query.append(" ORDER BY o.created_at DESC");

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query.toString());
            ps.setInt(1, userId);
            if (status != null && !status.isEmpty() && !status.equals("all")) {
                ps.setString(2, status);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToOrder(rs));
            }
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return list;
    }
}