package com.phonestore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/account"}) // 1. Đặt URL là /account
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 2. Kiểm tra đăng nhập
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        // 3. (Giai đoạn sau) Gọi DAO để lấy thông tin chi tiết tài khoản,
        //    danh sách địa chỉ, tài khoản liên kết...
        // AddressBook addressList = addressDAO.getAddresses(user.getId());
        // request.setAttribute("addressList", addressList);

        // 4. Gửi "tín hiệu" cho menu bên trái
        request.setAttribute("currentView", "account"); // Tín hiệu cho menu chính

        // 5. Chuyển đến file account.jsp
        request.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(request, response);
    }
}