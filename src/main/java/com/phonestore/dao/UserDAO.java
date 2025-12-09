package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAO {

    // Helper: Map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAddress(rs.getString("address"));
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

    public User checkLogin(String username, String plainTextPassword) {
        User user = getUserByUsername(username);
        if (user != null && BCrypt.checkpw(plainTextPassword, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    // --- CÁC HÀM CHECK TỒN TẠI (ĐỂ SỬA LỖI RegisterServlet) ---

    public boolean checkPhoneExists(String phone) {
        return checkExists("phone_number", phone);
    }

    public boolean checkEmailExists(String email) {
        return checkExists("email", email);
    }

    // Hàm phụ trợ dùng chung để check tồn tại
    private boolean checkExists(String column, String value) {
        String sql = "SELECT 1 FROM User WHERE " + column + " = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Trả về true nếu tìm thấy
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- CÁC HÀM TẠO USER ---

    // 1. Đăng ký thường (RegisterServlet dùng hàm này)
    public boolean createUser(User user) {
        String sql = "INSERT INTO User (full_name, username, phone_number, email, password_hash, role, auth_provider) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPasswordHash());
            ps.setString(6, user.getRole());
            ps.setString(7, "local"); // Mặc định là local

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Đăng ký Social (Google/Facebook dùng hàm này)
    public void createGoogleUser(User user) {
        String sql = "INSERT INTO User (username, full_name, email, password_hash, role, phone_number, auth_provider) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());

            if (user.getEmail() != null) {
                ps.setString(3, user.getEmail());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }

            ps.setString(4, BCrypt.hashpw("SOCIAL_LOGIN_" + System.currentTimeMillis(), BCrypt.gensalt()));
            ps.setString(5, "customer");
            ps.setNull(6, Types.VARCHAR);

            ps.setString(7, user.getAuthProvider()); // Lấy từ object

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}