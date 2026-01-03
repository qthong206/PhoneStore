

package com.phonestore.controller;

import com.phonestore.dao.AddressDAO;
import com.phonestore.dao.AdminUserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/user/detail")
public class AdminUserDetailServlet extends HttpServlet {

    private final AdminUserDAO userDAO = new AdminUserDAO();
    private final AddressDAO addressDAO = new AddressDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int userId = Integer.parseInt(req.getParameter("id"));

        req.setAttribute("user", userDAO.getById(userId));
        req.setAttribute("addresses", addressDAO.getAllByUserId(userId));
        req.setAttribute("contentPage", "/WEB-INF/views/admin/user-detail.jsp");

        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }
}
