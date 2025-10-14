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
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username"); // Lấy "username" từ form
        String password = request.getParameter("password");

        // Gọi DAO để lấy thông tin user bằng username
        User userFromDB = userDAO.getUserByUsername(username);

        // Kiểm tra user có tồn tại VÀ mật khẩu có khớp không
        if (userFromDB != null && BCrypt.checkpw(password, userFromDB.getPasswordHash())) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            userFromDB.setPasswordHash(null);
            session.setAttribute("user", userFromDB);
            response.sendRedirect("home");
        } else {
            // Đăng nhập thất bại
            request.setAttribute("errorMessage", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}