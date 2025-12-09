package com.phonestore.controller;

import com.phonestore.dao.UserDAO;
import com.phonestore.model.GooglePojo;
import com.phonestore.model.User;
import com.phonestore.utils.GoogleUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            response.sendRedirect("login?errorMessage=Google_Login_Failed");
            return;
        }

        try {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googleUser = GoogleUtils.getUserInfo(accessToken);
            User user = userDAO.getUserByEmail(googleUser.getEmail());

            if (user == null) {
                user = new User();
                user.setEmail(googleUser.getEmail());
                user.setFullName(googleUser.getName());
                user.setUsername(googleUser.getEmail());
                user.setAuthProvider("google"); // Set Provider

                userDAO.createGoogleUser(user);
                user = userDAO.getUserByEmail(googleUser.getEmail());
            }

            HttpSession session = request.getSession();
            user.setPasswordHash(null);
            session.setAttribute("user", user);
            response.sendRedirect("home");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi đăng nhập Google: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}