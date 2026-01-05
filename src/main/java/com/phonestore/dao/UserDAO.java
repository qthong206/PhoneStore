package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Helper: Map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        user.setAuthProvider(rs.getString("auth_provider"));
        return user;
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Các hàm check tồn tại
    public boolean checkPhoneExists(String phone) {
        return checkExists("phone_number", phone);
    }
    public boolean checkEmailExists(String email) {
        return checkExists("email", email);
    }
    private boolean checkExists(String column, String value) {
        String sql = "SELECT 1 FROM User WHERE " + column + " = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đăng ký thường
    public boolean createUser(User user) {
        String sql = "INSERT INTO User (full_name, username, phone_number, email, password_hash, role, auth_provider) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPasswordHash());
            ps.setString(6, user.getRole());
            ps.setString(7, "local");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đăng ký Social
    public void createGoogleUser(User user) {
        String sql = "INSERT INTO User (username, full_name, email, password_hash, role, phone_number, auth_provider) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());
            if (user.getEmail() != null) ps.setString(3, user.getEmail());
            else ps.setNull(3, Types.VARCHAR);
            ps.setString(4, BCrypt.hashpw("SOCIAL_" + System.currentTimeMillis(), BCrypt.gensalt()));
            ps.setString(5, "customer");
            ps.setNull(6, Types.VARCHAR);
            ps.setString(7, user.getAuthProvider());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hàm cập nhật thông tin cá nhân (Chỉ update tên, không update address nữa)
    public boolean updateUserInfo(User user) {
        String sql = "UPDATE User SET full_name = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setInt(2, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm đổi mật khẩu
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBContext.getConnection();

            // 1. Lấy mật khẩu hiện tại trong DB để so sánh
            String getPassSql = "SELECT password_hash FROM User WHERE id = ?";
            ps = conn.prepareStatement(getPassSql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String currentHash = rs.getString("password_hash");

                // 2. Kiểm tra mật khẩu cũ người dùng nhập vào
                // Nếu dùng BCrypt (như code bạn gửi)
                if (BCrypt.checkpw(oldPassword, currentHash)) {

                    // 3. Nếu đúng, mã hóa mật khẩu mới và update
                    String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                    String updateSql = "UPDATE User SET password_hash = ? WHERE id = ?";
                    // Đóng ps cũ để dùng ps mới
                    ps.close();

                    ps = conn.prepareStatement(updateSql);
                    ps.setString(1, newHash);
                    ps.setInt(2, userId);

                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối (bạn có thể dùng hàm closeConnections có sẵn)
            try { if(rs!=null) rs.close(); if(ps!=null) ps.close(); if(conn!=null) conn.close(); } catch(Exception e){}
        }
        return false;
    }

}