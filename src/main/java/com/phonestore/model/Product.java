package com.phonestore.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private String image; // Đường dẫn đến file ảnh

    // 1. Constructor mặc định (cần thiết cho một số frameworks)
    public Product() {
    }

    // 2. Constructor với tất cả các tham số (dùng để khởi tạo dữ liệu trong HomeServlet)
    public Product(int id, String name, double price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    // 3. Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    // 4. Setters (để thay đổi giá trị nếu cần)
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }
}