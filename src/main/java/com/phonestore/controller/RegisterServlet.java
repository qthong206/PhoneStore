package com.phonestore.controller;

import com.phonestore.dao.UserDAO;
import com.phonestore.model.User;
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

        // 1. Lấy dữ liệu và TRIM
        String fullNameRaw = (request.getParameter("fullName") != null) ? request.getParameter("fullName").trim() : "";
        String phone = (request.getParameter("phone") != null) ? request.getParameter("phone").trim() : "";
        String email = (request.getParameter("email") != null) ? request.getParameter("email").trim() : "";
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // 2. Kiểm tra Backend
        if (fullNameRaw.length() < 2 || !fullNameRaw.matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            sendError(request, response, "Họ tên không hợp lệ (vui lòng chỉ nhập chữ cái).", fullNameRaw, phone, email);
            return;
        }

        if (!password.equals(confirmPassword)) {
            sendError(request, response, "Mật khẩu nhập lại không khớp.", fullNameRaw, phone, email);
            return;
        }

        if (userDAO.checkPhoneExists(phone)) {
            sendError(request, response, "Số điện thoại này đã được sử dụng.", fullNameRaw, phone, email);
            return;
        }

        if (!email.isEmpty() && userDAO.checkEmailExists(email)) {
            sendError(request, response, "Email này đã được sử dụng.", fullNameRaw, phone, email);
            return;
        }

        // 3. TỰ ĐỘNG VIẾT HOA TÊN TRƯỚC KHI LƯU
        String formattedName = capitalizeName(fullNameRaw);

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setFullName(formattedName); // Lưu tên đã đẹp vào DB
        user.setPhoneNumber(phone);
        user.setUsername(phone);
        user.setEmail(email.isEmpty() ? null : email);
        user.setPasswordHash(hashedPassword);
        user.setRole("customer");

        boolean success = userDAO.createUser(user);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/login?register=success");
        } else {
            sendError(request, response, "Đã có lỗi xảy ra.", fullNameRaw, phone, email);
        }
    }

    // Hàm hỗ trợ viết hoa chữ cái đầu mỗi từ
    private String capitalizeName(String name) {
        if (name == null || name.isEmpty()) return name;
        String[] words = name.toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response,
                           String message, String fullName, String phone, String email)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.setAttribute("oldFullName", fullName);
        request.setAttribute("oldPhone", phone);
        request.setAttribute("oldEmail", email);
        doGet(request, response);
    }
}