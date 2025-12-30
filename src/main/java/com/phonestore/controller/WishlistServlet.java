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

// [QUAN TRỌNG] Thêm "/wishlist/remove" vào đây để khớp với JSP
@WebServlet(urlPatterns = {"/wishlist", "/wishlist/remove"})
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
            // Nên trả về 200 kèm success:false để JS xử lý hiển thị thông báo thay vì browser chặn lỗi 401
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"success\": false, \"message\": \"Vui lòng đăng nhập để thực hiện.\"}");
            return;
        }

        try {
            User user = (User) session.getAttribute("user");

            // Lấy tham số và xử lý ngoại lệ nếu null
            String productIdStr = request.getParameter("productId");
            String action = request.getParameter("action");

            if (productIdStr == null || action == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Thiếu tham số.\"}");
                return;
            }

            int productId = Integer.parseInt(productIdStr);
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

            // 3. Trả về JSON
            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                // Trả về JSON thủ công (nếu không dùng thư viện Gson/Jackson)
                response.getWriter().write("{\"success\": true, \"isFavorited\": " + isFavorited + "}");
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"success\": false, \"message\": \"Thao tác thất bại.\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"ID sản phẩm không hợp lệ.\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi Server: " + e.getMessage() + "\"}");
        }
    }
}