package com.phonestore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"/logout"}) // 1. Đặt URL là /logout
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 2. Lấy session hiện tại (không tạo mới nếu không có)
        HttpSession session = request.getSession(false);

        // 3. Kiểm tra xem session có tồn tại không
        if (session != null) {
            session.invalidate(); // Huỷ session, xoá tất cả data (như "user")
        }

        // 4. Đưa người dùng về trang đăng nhập
        response.sendRedirect("login");
    }
}