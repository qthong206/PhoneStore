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

    /* * HÀM CŨ BỊ TRÙNG (getReviewSummary) ĐÃ BỊ XÓA KHỎI ĐÂY
     */

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

    /**
     * Lấy tóm tắt đánh giá (ĐÃ CẬP NHẬT: đếm từng loại sao)
     * (Đây là hàm getReviewSummary duy nhất còn lại)
     */
    public ReviewSummaryDTO getReviewSummary(int productId) {
        ReviewSummaryDTO summary = new ReviewSummaryDTO();

        String query = "SELECT " +
                "    AVG(rating) as avgRating, " +
                "    COUNT(*) as totalReviews, " +
                "    SUM(CASE WHEN rating = 5 THEN 1 ELSE 0 END) as count5, " +
                "    SUM(CASE WHEN rating = 4 THEN 1 ELSE 0 END) as count4, " +
                "    SUM(CASE WHEN rating = 3 THEN 1 ELSE 0 END) as count3, " +
                "    SUM(CASE WHEN rating = 2 THEN 1 ELSE 0 END) as count2, " +
                "    SUM(CASE WHEN rating = 1 THEN 1 ELSE 0 END) as count1 " +
                "FROM ProductReview " +
                "WHERE product_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    summary.setTotalReviews(rs.getLong("totalReviews"));

                    // Chỉ set các giá trị nếu có review
                    if (summary.getTotalReviews() > 0) {
                        summary.setAvgRating(rs.getDouble("avgRating"));
                        summary.setCount5(rs.getLong("count5"));
                        summary.setCount4(rs.getLong("count4"));
                        summary.setCount3(rs.getLong("count3"));
                        summary.setCount2(rs.getLong("count2"));
                        summary.setCount1(rs.getLong("count1"));
                    } else {
                        // Mặc định là 0 nếu không có review
                        summary.setAvgRating(0);
                        summary.setCount5(0);
                        summary.setCount4(0);
                        summary.setCount3(0);
                        summary.setCount2(0);
                        summary.setCount1(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summary;
    }
}