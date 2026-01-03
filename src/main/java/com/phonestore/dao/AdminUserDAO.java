package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminUserDAO {

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPhoneNumber(rs.getString("phone_number"));
        u.setRole(rs.getString("role"));
        u.setAuthProvider(rs.getString("auth_provider"));
        u.setActive(rs.getBoolean("active"));
        return u;
    }

    // 📌 Danh sách + tìm kiếm
    public List<User> getUsers(String keyword) {
        List<User> list = new ArrayList<>();
        String sql =
                "SELECT * FROM User " +
                        "WHERE role <> 'admin' " +
                        "AND (username LIKE ? OR full_name LIKE ? OR email LIKE ?) " +
                        "ORDER BY id DESC";

        try (Connection c = DBContext.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String k = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 📌 Chi tiết user
    public User getById(int id) {
        String sql = "SELECT * FROM User WHERE id=?";
        try (Connection c = DBContext.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 📌 Khóa / mở user
    public void updateStatus(int id, boolean active) {
        String sql = "UPDATE User SET active=? WHERE id=?";
        try (Connection c = DBContext.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setBoolean(1, active);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
