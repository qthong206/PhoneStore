package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.UserAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    // 1. Lấy danh sách địa chỉ
    public List<UserAddress> getAllByUserId(int userId) {
        List<UserAddress> list = new ArrayList<>();
        String sql = "SELECT * FROM UserAddress WHERE user_id = ? ORDER BY is_default DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserAddress addr = new UserAddress();
                addr.setId(rs.getInt("id"));
                addr.setUserId(rs.getInt("user_id"));
                addr.setReceiverName(rs.getString("receiver_name"));
                addr.setPhoneNumber(rs.getString("phone_number"));
                addr.setStreetAddress(rs.getString("street_address"));
                addr.setAddressType(rs.getString("address_type"));
                addr.setDefaultAddress(rs.getBoolean("is_default"));
                list.add(addr);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm địa chỉ mới
    public boolean addAddress(UserAddress addr) {
        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false); // Dùng transaction

            // Nếu user chọn địa chỉ này là mặc định, bỏ mặc định các cái cũ
            if (addr.isDefaultAddress()) {
                String sqlUnset = "UPDATE UserAddress SET is_default = 0 WHERE user_id = ?";
                try (PreparedStatement psUnset = conn.prepareStatement(sqlUnset)) {
                    psUnset.setInt(1, addr.getUserId());
                    psUnset.executeUpdate();
                }
            } else {
                // Kiểm tra xem user đã có địa chỉ nào chưa, nếu chưa thì cái đầu tiên bắt buộc là mặc định
                String sqlCount = "SELECT COUNT(*) FROM UserAddress WHERE user_id = ?";
                try (PreparedStatement psCount = conn.prepareStatement(sqlCount)) {
                    psCount.setInt(1, addr.getUserId());
                    ResultSet rs = psCount.executeQuery();
                    if (rs.next() && rs.getInt(1) == 0) {
                        addr.setDefaultAddress(true); // Tự động set default nếu là cái đầu tiên
                    }
                }
            }

            String sql = "INSERT INTO UserAddress (user_id, receiver_name, phone_number, street_address, address_type, is_default) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, addr.getUserId());
                ps.setString(2, addr.getReceiverName());
                ps.setString(3, addr.getPhoneNumber());
                ps.setString(4, addr.getStreetAddress());
                ps.setString(5, addr.getAddressType());
                ps.setBoolean(6, addr.isDefaultAddress());
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    // 3. Cập nhật địa chỉ (MỚI THÊM)
    public boolean updateAddress(UserAddress addr) {
        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false);

            // Nếu user tick chọn mặc định khi update -> Bỏ mặc định các cái cũ
            if (addr.isDefaultAddress()) {
                String sqlUnset = "UPDATE UserAddress SET is_default = 0 WHERE user_id = ?";
                try (PreparedStatement psUnset = conn.prepareStatement(sqlUnset)) {
                    psUnset.setInt(1, addr.getUserId());
                    psUnset.executeUpdate();
                }
            }

            String sql = "UPDATE UserAddress SET receiver_name=?, phone_number=?, street_address=?, address_type=?, is_default=? WHERE id=? AND user_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, addr.getReceiverName());
                ps.setString(2, addr.getPhoneNumber());
                ps.setString(3, addr.getStreetAddress());
                ps.setString(4, addr.getAddressType());
                ps.setBoolean(5, addr.isDefaultAddress());
                ps.setInt(6, addr.getId());
                ps.setInt(7, addr.getUserId());
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    // 4. Set địa chỉ mặc định
    public boolean setDefaultAddress(int userId, int addressId) {
        Connection conn = null;
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false);

            String sql1 = "UPDATE UserAddress SET is_default = 0 WHERE user_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, userId);
            ps1.executeUpdate();

            String sql2 = "UPDATE UserAddress SET is_default = 1 WHERE id = ? AND user_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, addressId);
            ps2.setInt(2, userId);
            ps2.executeUpdate();

            conn.commit();
            return true;
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    // 5. Xóa địa chỉ (ĐÃ HOÀN THIỆN)
    public boolean deleteAddress(int addressId, int userId) {
        String sql = "DELETE FROM UserAddress WHERE id = ? AND user_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addressId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}