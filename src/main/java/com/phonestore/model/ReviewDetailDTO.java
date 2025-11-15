package com.phonestore.model;

import java.sql.Timestamp;

public class ReviewDetailDTO {
    private int rating;
    private String commentBody;
    private Timestamp createdAt;
    private String userFullName; // Dữ liệu JOIN từ bảng User

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getCommentBody() { return commentBody; }
    public void setCommentBody(String commentBody) { this.commentBody = commentBody; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }
}