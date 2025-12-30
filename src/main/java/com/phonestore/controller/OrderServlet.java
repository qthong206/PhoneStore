package com.phonestore.controller;

import com.phonestore.dao.ProductOrderDAO;
import com.phonestore.model.ProductOrder;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

    private ProductOrderDAO productOrderDAO;

    @Override
    public void init() {
        productOrderDAO = new ProductOrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check login
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");

        // --- Xử lý Hủy đơn hàng ---
        if ("cancel".equals(action)) {
            handleCancelOrder(request, response, user);
            return;
        }

        // --- Mặc định: Hiển thị danh sách đơn hàng ---
        String status = request.getParameter("status");
        if (status == null || status.isEmpty()) {
            status = "all";
        }

        // Lấy thống kê
        int totalOrders = productOrderDAO.countOrdersByUserId(user.getId());
        double totalSpent = productOrderDAO.sumTotalSpentByUserId(user.getId());

        // Lấy danh sách đơn
        List<ProductOrder> orderList = productOrderDAO.getOrdersByUserId(user.getId(), status);

        request.setAttribute("orders", orderList);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalSpent", totalSpent);

        request.setAttribute("currentView", "order");
        request.setAttribute("currentTab", status);

        request.getRequestDispatcher("/WEB-INF/views/user/order.jsp").forward(request, response);
    }

    // --- Logic hủy đơn ---
    private void handleCancelOrder(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {

        String idRaw = request.getParameter("id");

        if (idRaw != null) {
            try {
                int orderId = Integer.parseInt(idRaw);

                ProductOrder order = productOrderDAO.getOrderById(orderId);

                // Kiểm tra quyền sở hữu đơn hàng
                if (order != null && order.getUserId() == user.getId()) {
                    boolean success = productOrderDAO.cancelOrder(orderId);

                    if (success) {
                        // Hủy thành công -> Quay lại trang chi tiết để thấy trạng thái mới
                        response.sendRedirect(request.getContextPath() + "/order-detail?id=" + orderId);
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Lỗi -> Quay về danh sách
        response.sendRedirect(request.getContextPath() + "/order");
    }
}