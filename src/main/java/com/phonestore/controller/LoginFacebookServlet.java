package com.phonestore.controller;

import com.phonestore.dao.UserDAO;
import com.phonestore.model.FacebookPojo;
import com.phonestore.model.User;
import com.phonestore.utils.FacebookUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login-facebook")
public class LoginFacebookServlet extends HttpServlet {
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
            response.sendRedirect("login?errorMessage=Facebook_Login_Failed");
            return;
        }

        try {
            String accessToken = FacebookUtils.getToken(code);
            FacebookPojo fbUser = FacebookUtils.getUserInfo(accessToken);

            String fbID = fbUser.getId();
            User user = userDAO.getUserByUsername(fbID);

            if (user == null) {
                user = new User();
                user.setUsername(fbID); // FB ID làm Username
                user.setFullName(fbUser.getName());
                user.setEmail(fbUser.getEmail()); // Có thể null
                user.setAuthProvider("facebook"); // Set Provider

                userDAO.createGoogleUser(user);
                user = userDAO.getUserByUsername(fbID);
            }

            HttpSession session = request.getSession();
            user.setPasswordHash(null);
            session.setAttribute("user", user);
            response.sendRedirect("home");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi đăng nhập Facebook: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}