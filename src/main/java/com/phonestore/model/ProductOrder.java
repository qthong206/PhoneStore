package com.phonestore.model;

import java.sql.Timestamp;

public class ProductOrder {
    private int id;
    private Integer userId; // Dùng Integer để chấp nhận NULL
    private String recipientName;
    private String recipientEmail;
    private String recipientPhone;
    private String shippingAddress;
    private double totalAmount;
    private String status;
    private Timestamp createdAt;
    private String paymentMethod;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setPaymentMethod(String paymentMethod) {this.paymentMethod = paymentMethod;}
    public String getPaymentMethod() {return this.paymentMethod;}
}