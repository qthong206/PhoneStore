package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColorDAO {

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

    public List<Color> getAll() {
        List<Color> list = new ArrayList<>();
        String sql = "SELECT * FROM Color ORDER BY id";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Color c = new Color();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setHexCode(rs.getString("hex_code"));
                list.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
        return list;
    }

    public Color getById(int id) {
        String sql = "SELECT * FROM Color WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Color c = new Color();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setHexCode(rs.getString("hex_code"));
                return c;
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
        return null;
    }

    public void insert(Color c) {
        String sql = "INSERT INTO Color(name, hex_code) VALUES(?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getHexCode());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }

    public void update(Color c) {
        String sql = "UPDATE Color SET name=?, hex_code=? WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getHexCode());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Color WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }
}
