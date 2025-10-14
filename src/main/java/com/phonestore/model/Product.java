package com.phonestore.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private double salePrice; // Thêm giá khuyến mãi
    private String thumbnailUrl; // Sửa tên trường ảnh cho khớp DB
    private String description;
    private Brand brand; // Chứa thông tin thương hiệu

    // Constructors...
    public Product() {}

    // Getters and Setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getSalePrice() { return salePrice; }
    public void setSalePrice(double salePrice) { this.salePrice = salePrice; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }
}