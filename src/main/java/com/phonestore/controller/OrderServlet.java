package com.phonestore.controller;

import com.phonestore.dao.ProductOrderDAO;
import com.phonestore.model.ProductOrder;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

    private ProductOrderDAO productOrderDAO;

    @Override
    public void init() {
        productOrderDAO = new ProductOrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        // 2. Lấy trạng thái tab từ URL
        String status = request.getParameter("status");
        if (status == null || status.isEmpty()) {
            status = "all";
        }

        // 3. Lấy dữ liệu thống kê
        int totalOrders = productOrderDAO.countOrdersByUserId(user.getId());
        double totalSpent = productOrderDAO.sumTotalSpentByUserId(user.getId());

        // 4. Gọi DAO lấy danh sách đơn hàng theo trạng thái (status)
        List<ProductOrder> orderList = productOrderDAO.getOrdersByUserId(user.getId(), status);

        // 5. Gửi dữ liệu sang JSP
        request.setAttribute("orders", orderList);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalSpent", totalSpent);

        // 6. Gửi "tín hiệu" để Highlight menu và tab
        request.setAttribute("currentView", "order");
        request.setAttribute("currentTab", status);

        request.getRequestDispatcher("/WEB-INF/views/user/order.jsp").forward(request, response);
    }
}