package com.phonestore.model;

import java.util.Objects;

public class Brand {
    private int id;
    private String name;

    public Brand() {}

    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // --- THÊM 2 PHƯƠNG THỨC QUAN TRỌNG NÀY VÀO ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return id == brand.id; // Coi hai Brand là bằng nhau nếu ID của chúng giống nhau
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Tạo mã hash dựa trên ID
    }
}