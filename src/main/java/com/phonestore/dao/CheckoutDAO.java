package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.Cart;
import com.phonestore.model.CartItem;
import com.phonestore.model.ProductOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckoutDAO {

    public int createOrder(ProductOrder order, Cart cart) {
        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        ResultSet rs = null;
        int orderId = -1;

        // 1. SỬA LỖI CHÍNH TẢ: 'payment_meth' -> 'payment_method'
        String queryOrder = "INSERT INTO ProductOrder " +
                "(user_id, recipient_name, recipient_email, recipient_phone, shipping_address, total_amount, status, created_at, payment_method) " +
                "VALUES (?, ?, ?, ?, ?, ?, 'pending', NOW(), ?)";

        String queryDetail = "INSERT INTO OrderDetail " +
                "(order_id, product_id, quantity, price_at_purchase) " +
                "VALUES (?, ?, ?, ?)";

        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false);

            // 2. CÁC THAM SỐ (Vẫn đúng)
            psOrder = conn.prepareStatement(queryOrder, Statement.RETURN_GENERATED_KEYS);

            if (order.getUserId() != null) {
                psOrder.setInt(1, order.getUserId());
            } else {
                psOrder.setNull(1, java.sql.Types.INTEGER);
            }
            psOrder.setString(2, order.getRecipientName());
            psOrder.setString(3, order.getRecipientEmail());
            psOrder.setString(4, order.getRecipientPhone());
            psOrder.setString(5, order.getShippingAddress());
            psOrder.setDouble(6, order.getTotalAmount());
            psOrder.setString(7, order.getPaymentMethod());

            int affectedRows = psOrder.executeUpdate();

            // 3. LẤY ID (Giữ nguyên)
            if (affectedRows > 0) {
                rs = psOrder.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
            if (orderId == -1) {
                throw new Exception("Không thể tạo đơn hàng, ID không được trả về.");
            }

            // 4. THÊM OrderDetail (Giữ nguyên)
            psDetail = conn.prepareStatement(queryDetail);
            for (CartItem item : cart.getItems()) {
                psDetail.setInt(1, orderId);
                psDetail.setInt(2, item.getProduct().getId());
                psDetail.setInt(3, item.getQuantity());

                double price = (item.getProduct().getSalePrice() > 0) ?
                        item.getProduct().getSalePrice() :
                        item.getProduct().getPrice();

                psDetail.setDouble(4, price);
                psDetail.addBatch();
            }
            psDetail.executeBatch();

            // 5. COMMIT (Giữ nguyên)
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            orderId = -1;

        } finally {
            // 6. DỌN DẸP (Giữ nguyên)
            try {
                if (rs != null) rs.close();
                if (psOrder != null) psOrder.close();
                if (psDetail != null) psDetail.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return orderId;
    }
}