package com.phonestore.dao;

import com.phonestore.context.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductGalleryDAO {
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

    public List<String> getImagesByProductId(int productId) {
        List<String> list = new ArrayList<>();
        // Cập nhật SQL để lấy theo thứ tự sắp xếp
        String sql = "SELECT image_url FROM ProductGallery WHERE product_id=? ORDER BY sort_order ASC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("image_url"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
        return list;
    }

    // METHOD CŨ: Giữ lại để không bị lỗi code ở các lớp khác
    public void insertImage(int productId, String url) {
        insertImage(productId, url, 0); // Mặc định thứ tự là 0
    }

    // METHOD MỚI: Hỗ trợ lưu thứ tự (sort_order)
    public void insertImage(int productId, String url, int sortOrder) {
        String sql = "INSERT INTO ProductGallery(product_id, image_url, sort_order) VALUES(?, ?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setString(2, url);
            ps.setInt(3, sortOrder);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }

    public void deleteImage(int productId, String url) {
        String sql = "DELETE FROM ProductGallery WHERE product_id=? AND image_url=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setString(2, url);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }

    // Thêm hàm xóa tất cả để phục vụ việc cập nhật lại danh sách ảnh
    public void deleteAllByProductId(int productId) {
        String sql = "DELETE FROM ProductGallery WHERE product_id=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { closeConnections(); }
    }
}