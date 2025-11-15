package com.phonestore.controller;

import com.phonestore.dao.ReviewDAO;
import com.phonestore.model.ProductReview;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"/review"})
public class ReviewServlet extends HttpServlet {

    private ReviewDAO reviewDAO;

    @Override
    public void init() {
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action != null && action.equals("add")) {
            handleAddReview(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ");
        }
    }

    private void handleAddReview(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        // 1. Kiểm tra đăng nhập
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn phải đăng nhập để đánh giá");
            return;
        }

        try {
            // 2. Lấy thông tin
            User user = (User) session.getAttribute("user");
            int productId = Integer.parseInt(request.getParameter("productId"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String commentBody = request.getParameter("comment_body");

            // 3. Tạo Model
            ProductReview review = new ProductReview();
            review.setProductId(productId);
            review.setUserId(user.getId());
            review.setRating(rating);
            review.setCommentBody(commentBody);

            // 4. Lưu vào DB
            reviewDAO.addReview(review);

            // 5. Quay lại trang sản phẩm
            response.sendRedirect(request.getContextPath() + "/product-detail?id=" + productId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi máy chủ");
        }
    }
}