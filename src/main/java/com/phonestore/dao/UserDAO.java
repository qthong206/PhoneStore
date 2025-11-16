package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.User;
// 1. IMPORT THƯ VIỆN BCRYPT
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * (Hàm cũ của bạn) Lấy thông tin người dùng (KHÔNG BAO GỒM KIỂM TRA MẬT KHẨU)
     */
    public User getUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM User WHERE username = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setAddress(rs.getString("address"));
                    user.setPasswordHash(rs.getString("password_hash")); // Lấy hash
                    user.setRole(rs.getString("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * HÀM MỚI QUAN TRỌNG: Kiểm tra Đăng nhập
     * So sánh mật khẩu plain-text với mật khẩu đã băm trong DB
     */
    public User checkLogin(String username, String plainTextPassword) {
        // 1. Lấy user bằng username
        User user = getUserByUsername(username);

        if (user != null) {
            // 2. Lấy mật khẩu đã băm (hash) từ DB
            String hashedPasswordFromDB = user.getPasswordHash();

            // 3. Dùng BCrypt để so sánh
            if (BCrypt.checkpw(plainTextPassword, hashedPasswordFromDB)) {
                // Nếu mật khẩu khớp -> trả về User
                return user;
            }
        }

        // Nếu user không tồn tại, hoặc mật khẩu sai -> trả về null
        return null;
    }


    // --- (Các hàm đăng ký (08:44) giữ nguyên) ---

    public boolean checkPhoneExists(String phone) {
        String sql = "SELECT 1 FROM User WHERE phone_number = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // True nếu tìm thấy
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkEmailExists(String email) {
        String sql = "SELECT 1 FROM User WHERE email = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // True nếu tìm thấy
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createUser(User user) {
        String sql = "INSERT INTO User (full_name, username, phone_number, email, password_hash, role) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());

            // Dòng này giờ sẽ lưu MẬT KHẨU ĐÃ BĂM (hash)
            ps.setString(5, user.getPasswordHash());

            ps.setString(6, user.getRole());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}