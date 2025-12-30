package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.OrderDetail;
import com.phonestore.model.ProductOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        } catch (Exception e) { e.printStackTrace(); }
    }

    // [Image of SQL count aggregation]
    // --- Đếm tổng đơn hàng của User ---
    public int countOrdersByUserId(int userId) {
        String query = "SELECT COUNT(*) FROM ProductOrder WHERE user_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return 0;
    }

    // --- Tính tổng tiền đã chi tiêu (Chỉ tính đơn thành công) ---
    public double sumTotalSpentByUserId(int userId) {
        String query = "SELECT SUM(total_amount) FROM ProductOrder WHERE user_id = ? AND status = 'delivered'";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return 0;
    }

    // --- Query cơ bản lấy thông tin Order + 1 sản phẩm đại diện ---
    private String BASE_QUERY =
            "SELECT o.*, " +
                    " (SELECT p.name FROM Product p JOIN OrderDetail od ON p.id = od.product_id WHERE od.order_id = o.id LIMIT 1) as first_p_name, " +
                    " (SELECT p.thumbnail_url FROM Product p JOIN OrderDetail od ON p.id = od.product_id WHERE od.order_id = o.id LIMIT 1) as first_p_img " +
                    "FROM ProductOrder o ";

    private ProductOrder mapRowToOrder(ResultSet rs) throws SQLException {
        ProductOrder o = new ProductOrder();
        o.setId(rs.getInt("id"));
        o.setUserId(rs.getInt("user_id"));
        o.setRecipientName(rs.getString("recipient_name"));
        o.setRecipientPhone(rs.getString("recipient_phone"));
        o.setRecipientEmail(rs.getString("recipient_email"));
        o.setShippingAddress(rs.getString("shipping_address"));
        o.setPaymentMethod(rs.getString("payment_method"));
        o.setTotalAmount(rs.getDouble("total_amount"));
        o.setStatus(rs.getString("status"));
        o.setCreatedAt(rs.getTimestamp("created_at"));

        o.setFirstProductName(rs.getString("first_p_name"));
        o.setFirstProductImage(rs.getString("first_p_img"));
        return o;
    }

    // --- Lấy 5 đơn hàng gần nhất ---
    public List<ProductOrder> getRecentOrders(int userId) {
        List<ProductOrder> list = new ArrayList<>();
        String query = BASE_QUERY + "WHERE o.user_id = ? ORDER BY o.created_at DESC LIMIT 5";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapRowToOrder(rs));
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return list;
    }

    // --- Lấy danh sách đơn hàng theo trạng thái ---
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
            if (status != null && !status.isEmpty() && !status.equals("all")) ps.setString(2, status);
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapRowToOrder(rs));
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return list;
    }

    // --- Lấy chi tiết 1 đơn hàng theo ID ---
    public ProductOrder getOrderById(int orderId) {
        String query = BASE_QUERY + "WHERE o.id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) return mapRowToOrder(rs);
        } catch (Exception e) { e.printStackTrace(); } finally { closeConnections(); }
        return null;
    }

    // --- Lấy danh sách sản phẩm trong đơn hàng ---
    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String query = "SELECT od.*, p.name, p.thumbnail_url " +
                "FROM OrderDetail od " +
                "JOIN Product p ON od.product_id = p.id " +
                "WHERE od.order_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetail od = new OrderDetail();
                od.setId(rs.getInt("id"));
                od.setOrderId(rs.getInt("order_id"));
                od.setProductId(rs.getInt("product_id"));
                od.setQuantity(rs.getInt("quantity"));
                od.setPriceAtPurchase(rs.getDouble("price_at_purchase"));

                od.setProductName(rs.getString("name"));
                od.setThumbnailUrl(rs.getString("thumbnail_url"));

                double total = od.getQuantity() * od.getPriceAtPurchase();
                od.setTotalMoney(total);

                list.add(od);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }

    // --- Hủy đơn hàng (Chỉ khi status = 'pending') ---
    public boolean cancelOrder(int orderId) {
        String sql = "UPDATE ProductOrder SET status = 'cancelled' WHERE id = ? AND status = 'pending'";

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, orderId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return false;
    }
}