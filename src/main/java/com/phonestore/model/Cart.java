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
     * Thêm một sản phẩm vào giỏ hàng hoặc cập nhật số lượng nếu đã tồn tại.
     * @param product Sản phẩm cần thêm.
     * @param quantity Số lượng.
     */
    public void addItem(Product product, int quantity) {
        int productId = product.getId();

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        if (items.containsKey(productId)) {
            // Nếu có, cập nhật lại số lượng
            CartItem existingItem = items.get(productId);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Nếu chưa, tạo một CartItem mới và thêm vào giỏ
            CartItem newItem = new CartItem(product, quantity);
            items.put(productId, newItem);
        }
    }

    /**
     * Xóa một sản phẩm khỏi giỏ hàng.
     * @param productId ID của sản phẩm cần xóa.
     */
    public void removeItem(int productId) {
        items.remove(productId);
    }

    /**
     * Lấy danh sách tất cả các món hàng trong giỏ.
     * @return Một Collection các CartItem.
     */
    public Collection<CartItem> getItems() {
        return items.values();
    }

    /**
     * Tính tổng số tiền của tất cả các sản phẩm trong giỏ hàng.
     * @return Tổng số tiền.
     */
    public double getTotal() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
}