package com.phonestore.controller;

import com.phonestore.dao.ProductOrderDAO;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"/warranty"})
public class WarrantyServlet extends HttpServlet {

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

        // 2. Lấy trạng thái tab (status)
        String status = request.getParameter("status");
        if (status == null || status.isEmpty()) {
            status = "all";
        }

        // 3. Lấy thống kê (Để thanh User Info Bar không bị về 0)
        int totalOrders = productOrderDAO.countOrdersByUserId(user.getId());
        double totalSpent = productOrderDAO.sumTotalSpentByUserId(user.getId());

        // 4. (TODO) Sau này gọi WarrantyDAO lấy danh sách bảo hành ở đây
        // List<Warranty> warrantyList = ...

        // 5. Gửi dữ liệu sang JSP
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalSpent", totalSpent);

        request.setAttribute("currentView", "warranty");
        request.setAttribute("currentTab", status);

        // 6. Forward về đúng thư mục /user/
        request.getRequestDispatcher("/WEB-INF/views/user/warranty.jsp").forward(request, response);
    }
}