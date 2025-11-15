package com.phonestore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"/order-success"})
public class OrderSuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Integer orderId = (Integer) session.getAttribute("latestOrderId");

        if (orderId == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        request.setAttribute("orderId", orderId);
        session.removeAttribute("latestOrderId");

        // 4. Forward đến trang "Thành công"
        request.setAttribute("pageTitle", "Đặt hàng thành công");
        request.setAttribute("pageCss", "orderSuccess.css");
        request.getRequestDispatcher("/WEB-INF/views/orderSuccess.jsp").forward(request, response);
    }
}