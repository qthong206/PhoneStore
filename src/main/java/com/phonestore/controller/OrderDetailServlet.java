package com.phonestore.controller;

import com.phonestore.dao.ProductOrderDAO;
import com.phonestore.model.ProductOrder;
import com.phonestore.model.OrderDetail;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/order-detail")
public class OrderDetailServlet extends HttpServlet {

    private ProductOrderDAO orderDAO = new ProductOrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idRaw = req.getParameter("id");
        if (idRaw == null) {
            resp.sendRedirect(req.getContextPath() + "/order");
            return;
        }

        try {
            int orderId = Integer.parseInt(idRaw);

            // Hàm này giờ đã có trong DAO (do code ở trên đã thêm vào)
            ProductOrder order = orderDAO.getOrderById(orderId);

            if (order == null || order.getUserId() == null || order.getUserId() != user.getId()) {
                resp.sendRedirect(req.getContextPath() + "/order");
                return;
            }

            List<OrderDetail> details = orderDAO.getOrderDetails(orderId);

            req.setAttribute("order", order);
            req.setAttribute("details", details);
            req.setAttribute("currentView", "order");

            req.getRequestDispatcher("/WEB-INF/views/user/order-detail.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/order");
        }
    }
}