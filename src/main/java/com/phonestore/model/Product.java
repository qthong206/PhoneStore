package com.phonestore.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private double salePrice;
    private String thumbnailUrl;
    private String description;
    private Brand brand;
    private int seriesId;

    private String model; // <-- THÊM DÒNG NÀY
    private String storage; // <-- THÊM DÒNG NÀY

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
    public int getSeriesId() { return seriesId; }
    public void setSeriesId(int seriesId) { this.seriesId = seriesId; }

    // THÊM GETTER/SETTER CHO 2 TRƯỜNG MỚI
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getStorage() { return storage; }
    public void setStorage(String storage) { this.storage = storage; }
}