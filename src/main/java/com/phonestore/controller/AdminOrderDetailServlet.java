package com.phonestore.controller;

import com.phonestore.dao.AdminOrderDAO;
import com.phonestore.dao.ProductOrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/order-detail")
public class AdminOrderDetailServlet extends HttpServlet {

    ProductOrderDAO orderDAO = new ProductOrderDAO();
    AdminOrderDAO adminDAO = new AdminOrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        req.setAttribute("order", orderDAO.getOrderById(id));
        req.setAttribute("details", orderDAO.getOrderDetails(id));
        req.setAttribute("contentPage",
                "/WEB-INF/views/admin/order-detail.jsp");

        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String status = req.getParameter("status");

        adminDAO.updateStatus(orderId, status);
        resp.sendRedirect("order-detail?id=" + orderId);
    }
}
