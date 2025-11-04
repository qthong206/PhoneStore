package com.phonestore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
// Trong tương lai bạn sẽ cần:
// import com.phonestore.model.Order;
// import com.phonestore.dao.OrderDAO;
// import java.util.List;

@WebServlet(urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

    // private OrderDAO orderDAO;
    // public void init() { orderDAO = new OrderDAO(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        // 1. Lấy trạng thái tab từ URL (ví dụ: /order?status=pending)
        String status = request.getParameter("status");
        if (status == null || status.isEmpty()) {
            status = "all"; // Mặc định là tab "Tất cả"
        }

        // 2. (Giai đoạn sau) Gọi DAO để lấy danh sách đơn hàng đã lọc
        // User user = (User) request.getSession().getAttribute("user");
        // List<Order> orderList = orderDAO.getOrdersByStatus(user.getId(), status);
        // request.setAttribute("orderList", orderList);

        // 3. Gửi "tín hiệu" cho menu bên trái (user-nav)
        request.setAttribute("currentView", "order"); // Tín hiệu cho menu chính

        // 4. Gửi "tín hiệu" cho thanh tab (tab-nav)
        request.setAttribute("currentTab", status); // Tín hiệu cho tab (all, pending, ...)

        // 5. Chuyển đến JSP
        request.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(request, response);
    }
}