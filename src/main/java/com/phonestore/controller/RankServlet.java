package com.phonestore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/rank"}) // 1. Đặt URL là /rank
public class RankServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 2. Kiểm tra đăng nhập
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        // 3. (Giai đoạn sau) Gọi DAO để lấy thông tin hạng thành viên,
        //    số tiền đã chi, v.v.
        // RankInfo info = rankDAO.getRankInfo(user.getId());
        // request.setAttribute("rankInfo", info);

        // 4. Gửi "tín hiệu" cho menu bên trái
        request.setAttribute("currentView", "rank"); // Tín hiệu cho menu chính

        // 5. Chuyển đến file rank.jsp
        request.getRequestDispatcher("/WEB-INF/views/rank.jsp").forward(request, response);
    }
}