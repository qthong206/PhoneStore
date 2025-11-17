package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SpecificationDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy tất cả thông số kỹ thuật của 1 sản phẩm
     * Sắp xếp theo nhóm (group_name) để hiển thị trên JSP
     */
    public List<Specification> getSpecificationsByProductId(int productId) {
        List<Specification> specsList = new ArrayList<>();

        // Sắp xếp theo group_name để JSP có thể gom nhóm
        String query = "SELECT * FROM Specification " +
                "WHERE product_id = ? " +
                "ORDER BY group_name, id";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Specification spec = new Specification();
                spec.setId(rs.getInt("id"));
                spec.setProductId(rs.getInt("product_id"));
                spec.setGroupName(rs.getString("group_name"));
                spec.setSpecKey(rs.getString("spec_key"));
                spec.setSpecValue(rs.getString("spec_value"));
                specsList.add(spec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return specsList;
    }
}