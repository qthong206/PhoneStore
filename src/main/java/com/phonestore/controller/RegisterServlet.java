package com.phonestore.controller;

import com.phonestore.dao.UserDAO;
import com.phonestore.model.User;
// 1. IMPORT THƯ VIỆN BCRYPT
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("pageTitle", "Đăng ký tài khoản");
        request.setAttribute("pageCss", "register.css");
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            sendError(request, response, "Mật khẩu nhập lại không khớp.", fullName, phone, email);
            return;
        }
        if (userDAO.checkPhoneExists(phone)) {
            sendError(request, response, "Số điện thoại này đã được sử dụng.", fullName, phone, email);
            return;
        }
        if (email != null && !email.isEmpty() && userDAO.checkEmailExists(email)) {
            sendError(request, response, "Email này đã được sử dụng.", fullName, phone, email);
            return;
        }

        // 2. HASH MẬT KHẨU
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setFullName(fullName);
        user.setPhoneNumber(phone);
        user.setUsername(phone);
        user.setEmail(email);
        user.setPasswordHash(hashedPassword); // <-- Lưu hash
        user.setRole("user");

        boolean success = userDAO.createUser(user);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/login?register=success");
        } else {
            sendError(request, response, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", fullName, phone, email);
        }
    }

    // Hàm gửi lỗi (đã được thêm vào bản 08:44)
    private void sendError(HttpServletRequest request, HttpServletResponse response,
                           String message, String fullName, String phone, String email)
            throws ServletException, IOException {

        request.setAttribute("errorMessage", message);
        request.setAttribute("oldFullName", fullName);
        request.setAttribute("oldPhone", phone);
        request.setAttribute("oldEmail", email);

        // Gọi doGet để tải lại trang với CSS (bản 08:44 đã sửa)
        doGet(request, response);
    }
}