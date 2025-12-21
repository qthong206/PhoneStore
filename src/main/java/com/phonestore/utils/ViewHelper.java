package com.phonestore.utils;

import com.phonestore.dao.WishlistDAO;
import com.phonestore.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class ViewHelper {

    /**
     * Hàm này giúp lấy danh sách ID sản phẩm yêu thích của user
     * và tự động gắn vào request attribute "wishlistIds".
     */
    public static void loadWishlistData(HttpServletRequest request, WishlistDAO wishlistDAO) {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        Set<Integer> wishlistIds = new HashSet<>();

        if (user != null) {
            // Chỉ gọi DB khi user đã đăng nhập
            wishlistIds = wishlistDAO.getWishlistProductIds(user.getId());
        }

        // Gán kết quả vào request để JSP sử dụng
        request.setAttribute("wishlistIds", wishlistIds);
    }
}