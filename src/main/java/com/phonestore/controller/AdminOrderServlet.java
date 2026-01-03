package com.phonestore.controller;

import com.phonestore.dao.AdminOrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {

    AdminOrderDAO orderDAO = new AdminOrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String status = req.getParameter("status");
        if (status == null) status = "all";

        req.setAttribute("orders", orderDAO.getAllOrders(status));
        req.setAttribute("status", status);

        req.setAttribute("contentPage", "/WEB-INF/views/admin/order-list.jsp");
        req.setAttribute("pageCss", "admin-order.css");

        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }
}

