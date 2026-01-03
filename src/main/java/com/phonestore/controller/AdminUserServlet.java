package com.phonestore.controller;

import com.phonestore.dao.AdminUserDAO;
import com.phonestore.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user")
public class AdminUserServlet extends HttpServlet {

    private final AdminUserDAO adminUserDAO = new AdminUserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = req.getParameter("keyword");
        List<User> users = adminUserDAO.getUsers(keyword);

        req.setAttribute("users", users);
        req.setAttribute("keyword", keyword);
        req.setAttribute("contentPage", "/WEB-INF/views/admin/user-list.jsp");

        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        boolean active = Boolean.parseBoolean(req.getParameter("active"));

        adminUserDAO.updateStatus(id, active);
        resp.sendRedirect(req.getContextPath() + "/admin/user");
    }
}
