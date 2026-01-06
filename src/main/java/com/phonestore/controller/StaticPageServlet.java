package com.phonestore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Đăng ký tất cả các đường dẫn cần thiết
@WebServlet(urlPatterns = {"/about", "/policy", "/terms", "/support"})
public class StaticPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        String view;

        switch (path) {
            case "/policy":
                view = "/WEB-INF/views/policy.jsp";
                break;
            case "/terms":
                view = "/WEB-INF/views/terms.jsp";
                break;
            case "/support":
                view = "/WEB-INF/views/support.jsp";
                break;
            default:
                String tab = request.getParameter("tab");
                if (tab == null || tab.isEmpty()) tab = "gioi-thieu";
                request.setAttribute("activeTab", tab);
                view = "/WEB-INF/views/info-center.jsp";
                break;
        }

        request.getRequestDispatcher(view).forward(request, response);
    }
}