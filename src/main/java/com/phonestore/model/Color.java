package com.phonestore.model;

public class Color {
    private int id;
    private String name;
    private String hexCode; // Mã màu (ví dụ: #FFFFFF)

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHexCode() { return hexCode; }
    public void setHexCode(String hexCode) { this.hexCode = hexCode; }
}