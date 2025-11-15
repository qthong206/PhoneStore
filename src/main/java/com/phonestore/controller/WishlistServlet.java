package com.phonestore.controller;

import com.phonestore.dao.WishlistDAO;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"/wishlist"})
public class WishlistServlet extends HttpServlet {

    private WishlistDAO wishlistDAO;

    @Override
    public void init() {
        wishlistDAO = new WishlistDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 1. Kiểm tra đăng nhập
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write("{\"success\": false, \"message\": \"Cần đăng nhập\"}");
            return;
        }

        try {
            User user = (User) session.getAttribute("user");
            int productId = Integer.parseInt(request.getParameter("productId"));
            String action = request.getParameter("action"); // "add" hoặc "remove"

            boolean success = false;
            boolean isFavorited = false;

            // 2. Xử lý logic
            if ("add".equals(action)) {
                success = wishlistDAO.addToWishlist(user.getId(), productId);
                isFavorited = true;
            } else if ("remove".equals(action)) {
                success = wishlistDAO.removeFromWishlist(user.getId(), productId);
                isFavorited = false;
            }

            // 3. Trả về JSON cho JavaScript
            if (success) {
                response.setStatus(HttpServletResponse.SC_OK); // 200
                response.getWriter().write("{\"success\": true, \"isFavorited\": " + isFavorited + "}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                response.getWriter().write("{\"success\": false, \"message\": \"Thao tác thất bại\"}");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            response.getWriter().write("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ\"}");
        }
    }
}