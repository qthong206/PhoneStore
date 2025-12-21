package com.phonestore.controller;

import com.phonestore.dao.UserDAO;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Nếu user đã đăng nhập rồi mà cố vào trang login -> đẩy về home
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy dữ liệu
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2. Validate cơ bản (Tránh gọi DB nếu dữ liệu rỗng)
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        // 3. Gọi DB kiểm tra
        User userFromDB = userDAO.getUserByUsername(username);

        // 4. Kiểm tra Password
        if (userFromDB != null && BCrypt.checkpw(password, userFromDB.getPasswordHash())) {
            HttpSession session = request.getSession();
            userFromDB.setPasswordHash(null);
            session.setAttribute("user", userFromDB);

            String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                session.removeAttribute("redirectAfterLogin");
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }

        } else {
            request.setAttribute("errorMessage", "Tài khoản hoặc mật khẩu không chính xác!");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}