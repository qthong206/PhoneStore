package com.phonestore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/warranty"}) // 1. Đặt URL là /warranty
public class WarrantyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 2. Kiểm tra đăng nhập
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        // 3. Lấy trạng thái tab từ URL (ví dụ: /warranty?status=received)
        String status = request.getParameter("status");
        if (status == null || status.isEmpty()) {
            status = "all"; // Mặc định là tab "Tất cả"
        }

        // 4. (Giai đoạn sau) Gọi DAO để lấy danh sách bảo hành
        // List<Warranty> warrantyList = warrantyDAO.getWarrantiesByStatus(user.getId(), status);
        // request.setAttribute("warrantyList", warrantyList);

        // 5. Gửi "tín hiệu" cho menu bên trái (user-nav)
        request.setAttribute("currentView", "warranty"); // Tín hiệu cho menu chính

        // 6. Gửi "tín hiệu" cho thanh tab (tab-nav)
        request.setAttribute("currentTab", status); // Tín hiệu cho tab (all, received, ...)

        // 7. Chuyển đến file warranty.jsp
        request.getRequestDispatcher("/WEB-INF/views/warranty.jsp").forward(request, response);
    }
}