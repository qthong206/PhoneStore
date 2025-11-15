package com.phonestore.dao;

import com.phonestore.context.DBContext;
import com.phonestore.model.ProductReview;
import com.phonestore.model.ReviewDetailDTO;
import com.phonestore.model.ReviewSummaryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

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
     * Lấy Tóm tắt đánh giá (Sao trung bình và Tổng số)
     */
    public ReviewSummaryDTO getReviewSummary(int productId) {
        ReviewSummaryDTO summary = new ReviewSummaryDTO();
        summary.setAvgRating(0);
        summary.setTotalReviews(0);

        String query = "SELECT COUNT(*) as total_reviews, AVG(rating) as avg_rating " +
                "FROM ProductReview WHERE product_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                summary.setTotalReviews(rs.getInt("total_reviews"));
                summary.setAvgRating(rs.getDouble("avg_rating"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return summary;
    }

    /**
     * Lấy danh sách đánh giá chi tiết (JOIN với User để lấy Tên)
     */
    public List<ReviewDetailDTO> getReviewsByProductId(int productId) {
        List<ReviewDetailDTO> reviews = new ArrayList<>();
        String query = "SELECT pr.rating, pr.comment_body, pr.created_at, u.full_name " +
                "FROM ProductReview pr " +
                "JOIN User u ON pr.user_id = u.id " +
                "WHERE pr.product_id = ? " +
                "ORDER BY pr.created_at DESC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ReviewDetailDTO dto = new ReviewDetailDTO();
                dto.setRating(rs.getInt("rating"));
                dto.setCommentBody(rs.getString("comment_body"));
                dto.setCreatedAt(rs.getTimestamp("created_at"));
                dto.setUserFullName(rs.getString("full_name"));
                reviews.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return reviews;
    }

    /**
     * Thêm một đánh giá mới (dùng cho Form POST)
     */
    public boolean addReview(ProductReview review) {
        String query = "INSERT INTO ProductReview (product_id, user_id, rating, comment_body, created_at) " +
                "VALUES (?, ?, ?, ?, NOW())";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, review.getProductId());
            ps.setInt(2, review.getUserId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getCommentBody());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return false;
    }
}