package com.phonestore.controller;

import com.phonestore.dao.AddressDAO;
import com.phonestore.dao.AdminUserDAO;
import com.phonestore.dao.ProductOrderDAO; // Đảm bảo đã có DAO này
import com.phonestore.model.User;
import com.phonestore.model.ProductOrder;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user/detail")
public class AdminUserDetailServlet extends HttpServlet {

    private final AdminUserDAO userDAO = new AdminUserDAO();
    private final AddressDAO addressDAO = new AddressDAO();
    private final ProductOrderDAO orderDAO = new ProductOrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("id"));

            // 1. Lấy thông tin User cơ bản
            req.setAttribute("user", userDAO.getById(userId));

            // 2. Lấy danh sách địa chỉ
            req.setAttribute("addresses", addressDAO.getAllByUserId(userId));

            // 3. Lấy danh sách đơn hàng (Sử dụng hàm getByUserId đã chỉnh trong DAO)
            req.setAttribute("userOrders", orderDAO.getByUserId(userId));

            // 4. Thêm các thông số thống kê để hiển thị ở Sidebar Profile
            req.setAttribute("orderCount", orderDAO.countOrdersByUserId(userId));
            req.setAttribute("totalSpent", orderDAO.sumTotalSpentByUserId(userId));

            req.setAttribute("contentPage", "/WEB-INF/views/admin/user-detail.jsp");
            req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                    .forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin/user");
        }
    }
}