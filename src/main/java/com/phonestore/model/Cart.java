package com.phonestore.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer, CartItem> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    /**
     * Thêm sản phẩm (Dùng cho nút "Thêm vào giỏ" - Cộng dồn số lượng)
     */
    public void addItem(Product product, int quantity) {
        int productId = product.getId();
        if (items.containsKey(productId)) {
            CartItem existingItem = items.get(productId);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(product, quantity);
            items.put(productId, newItem);
        }
    }

    /**
     * Cập nhật số lượng (Dùng cho nút +/- trong giỏ hàng - Gán số lượng mới)
     * (HÀM MỚI BỔ SUNG)
     */
    public void updateItem(int productId, int quantity) {
        if (items.containsKey(productId)) {
            if (quantity <= 0) {
                items.remove(productId); // Nếu số lượng <= 0 thì xóa luôn
            } else {
                CartItem item = items.get(productId);
                item.setQuantity(quantity); // Gán đè số lượng mới
            }
        }
    }

    /**
     * Xóa một sản phẩm khỏi giỏ hàng.
     */
    public void removeItem(int productId) {
        items.remove(productId);
    }

    /**
     * Xóa sạch giỏ hàng (Dùng sau khi thanh toán thành công)
     * (HÀM MỚI BỔ SUNG)
     */
    public void clear() {
        items.clear();
    }

    /**
     * Lấy danh sách item
     */
    public Collection<CartItem> getItems() {
        return items.values();
    }

    /**
     * Tính tổng tiền
     */
    public double getTotal() {
        double total = 0;
        for (CartItem item : items.values()) {
            double price;
            // Ưu tiên lấy giá khuyến mãi nếu có
            if (item.getProduct().getSalePrice() > 0) {
                price = item.getProduct().getSalePrice();
            } else {
                price = item.getProduct().getPrice();
            }
            total += price * item.getQuantity();
        }
        return total;
    }

    /**
     * Lấy tổng số lượng sản phẩm (để hiển thị icon giỏ hàng)
     */
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (CartItem item : items.values()) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }
}