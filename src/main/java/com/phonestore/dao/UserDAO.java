package com.phonestore.dao;

import com.phonestore.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Lấy thông tin người dùng dựa trên username.
     * @param username Tên đăng nhập của người dùng
     * @return Đối tượng User nếu tìm thấy, ngược lại trả về null.
     */
    public User getUserByUsername(String username) {
        User user = null;
        // Sửa lại câu lệnh SQL để tìm bằng username
        String sql = "SELECT * FROM User WHERE username = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username")); // Lấy username
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setAddress(rs.getString("address"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}