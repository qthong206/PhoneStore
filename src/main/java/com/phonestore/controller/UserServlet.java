package com.phonestore.controller;

import com.phonestore.dao.ProductOrderDAO; // <-- Dùng DAO mới
import com.phonestore.dao.WishlistDAO;
import com.phonestore.model.ProductOrder;  // <-- Dùng Model của bạn
import com.phonestore.model.Product;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {

    private ProductOrderDAO productOrderDAO; // Sửa tên biến
    private WishlistDAO wishlistDAO;

    @Override
    public void init() {
        productOrderDAO = new ProductOrderDAO(); // Khởi tạo DAO mới
        wishlistDAO = new WishlistDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        // 1. Lấy thống kê
        int totalOrders = productOrderDAO.countOrdersByUserId(user.getId());
        double totalSpent = productOrderDAO.sumTotalSpentByUserId(user.getId());

        // 2. Lấy danh sách đơn hàng gần đây (ProductOrder)
        List<ProductOrder> recentOrders = productOrderDAO.getRecentOrders(user.getId());

        // 3. Lấy danh sách sản phẩm yêu thích (Giữ nguyên logic cũ nếu WishlistDAO đã đúng)
        List<Product> wishlistItems = wishlistDAO.getWishlistItems(user.getId());

        // 4. Gửi sang JSP
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalSpent", totalSpent);
        request.setAttribute("recentOrders", recentOrders);
        request.setAttribute("wishlistItems", wishlistItems);

        request.setAttribute("currentView", "overview");

        request.getRequestDispatcher("/WEB-INF/views/user/user.jsp").forward(request, response);
    }
}