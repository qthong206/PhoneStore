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
        // JOIN để lấy tên Brand từ ID
        String sql = "SELECT s.*, b.name AS brandName FROM ProductSeries s " +
                "LEFT JOIN Brand b ON s.brand_id = b.id ORDER BY s.id DESC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductSeries s = new ProductSeries();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setBrandId(rs.getInt("brand_id"));
                s.setReleaseYear(rs.getInt("release_year"));
                s.setBrandName(rs.getString("brandName"));
                list.add(s);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
        return list;
    }

    // SỬA: Lấy đầy đủ thông tin để trang Edit có dữ liệu cũ
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
                s.setBrandId(rs.getInt("brand_id"));
                s.setReleaseYear(rs.getInt("release_year"));
                return s;
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
        return null;
    }

    // SỬA: Thêm brand_id và release_year khi lưu mới
    public void insert(ProductSeries s) {
        String sql = "INSERT INTO ProductSeries(name, brand_id, release_year) VALUES(?, ?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setInt(2, s.getBrandId());
            ps.setInt(3, s.getReleaseYear());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }

    // SỬA: Cập nhật đầy đủ các cột khi chỉnh sửa
    public void update(ProductSeries s) {
        String sql = "UPDATE ProductSeries SET name=?, brand_id=?, release_year=? WHERE id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setInt(2, s.getBrandId());
            ps.setInt(3, s.getReleaseYear());
            ps.setInt(4, s.getId());
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