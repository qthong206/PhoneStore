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
        // Thêm dòng này để nhận keyword tiếng Việt đúng
        req.setCharacterEncoding("UTF-8");

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
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean active = Boolean.parseBoolean(req.getParameter("active"));

            adminUserDAO.updateStatus(id, active);

            // Có thể thêm session message ở đây để hiển thị thông báo "Cập nhật thành công"
            req.getSession().setAttribute("message", "Đã cập nhật trạng thái tài khoản!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/admin/user");
    }
}