package com.phonestore.controller;

import com.phonestore.dao.UserDAO;
import com.phonestore.model.User;
import com.phonestore.service.EmailService;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Quên mật khẩu");
        request.setAttribute("pageCss", "register.css");
        request.getRequestDispatcher("/WEB-INF/views/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String email = (request.getParameter("email") != null) ? request.getParameter("email").trim() : "";

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);

        // 1. Kiểm tra Email có tồn tại và không phải login bằng Google
        if (user == null) {
            request.setAttribute("errorMessage", "Email này chưa được đăng ký!");
        } else if ("google".equals(user.getAuthProvider())) {
            request.setAttribute("errorMessage", "Tài khoản này đăng nhập bằng Google, không thể đổi mật khẩu tại đây.");
        } else {
            // 2. Tạo mật khẩu mới ngẫu nhiên
            String newPassword = "Phone" + (int)(Math.random() * 90000 + 10000); // VD: Phone54321
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // 3. Cập nhật vào DB
            boolean isUpdated = userDAO.updatePassword(email, hashedPassword);

            if (isUpdated) {
                // 4. Gửi Mail (Chạy ngầm để không treo giao diện)
                new Thread(() -> {
                    EmailService emailService = new EmailService();
                    emailService.sendPasswordResetEmail(email, user.getFullName(), newPassword);
                }).start();

                request.setAttribute("successMessage", "Mật khẩu mới đã được gửi vào Email của bạn.");
            } else {
                request.setAttribute("errorMessage", "Lỗi hệ thống, vui lòng thử lại sau.");
            }
        }

        doGet(request, response);
    }
}