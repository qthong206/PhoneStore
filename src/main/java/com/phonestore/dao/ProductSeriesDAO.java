package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.ProductSeries;

import java.sql.*;
import java.util.*;

public class ProductSeriesDAO {

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

    public List<ProductSeries> getAllSeries() {
        List<ProductSeries> list = new ArrayList<>();
        String sql = "SELECT * FROM ProductSeries ORDER BY id";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductSeries s = new ProductSeries();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                list.add(s);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
        return list;
    }

    public ProductSeries getById(int id) {
        String sql = "SELECT * FROM ProductSeries WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                ProductSeries s = new ProductSeries();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                return s;
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
        return null;
    }

    public void insert(ProductSeries s) {
        String sql = "INSERT INTO ProductSeries(name) VALUES(?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }

    public void update(ProductSeries s) {
        String sql = "UPDATE ProductSeries SET name=? WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setInt(2, s.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM ProductSeries WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }
}
