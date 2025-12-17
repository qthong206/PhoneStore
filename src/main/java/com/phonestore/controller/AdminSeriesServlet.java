package com.phonestore.controller;

import com.phonestore.dao.ProductSeriesDAO;
import com.phonestore.model.ProductSeries;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
        "/admin/series",
        "/admin/series/add",
        "/admin/series/insert",
        "/admin/series/edit",
        "/admin/series/update",
        "/admin/series/delete"
})
public class AdminSeriesServlet extends HttpServlet {

    private ProductSeriesDAO dao = new ProductSeriesDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        switch (path) {
            case "/admin/series":
                list(req, resp);
                break;

            case "/admin/series/add":
                showForm(req, resp, null);
                break;

            case "/admin/series/edit":
                int id = parseInt(req.getParameter("id"));
                ProductSeries s = dao.getById(id);
                showForm(req, resp, s);
                break;

            case "/admin/series/delete":
                delete(req, resp);
                break;

            default:
                resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        switch (path) {
            case "/admin/series/insert":
                insert(req, resp);
                break;

            case "/admin/series/update":
                update(req, resp);
                break;

            default:
                resp.sendError(404);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<ProductSeries> list = dao.getAllSeries();
        req.setAttribute("series", list);

        req.setAttribute("pageCss","series-list.css");
        req.setAttribute("contentPage","/WEB-INF/views/admin/series-list.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, ProductSeries s)
            throws ServletException, IOException {

        req.setAttribute("item", s);
        req.setAttribute("pageCss","series-form.css");
        req.setAttribute("contentPage","/WEB-INF/views/admin/series-form.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        ProductSeries s = new ProductSeries();
        s.setName(req.getParameter("name"));
        dao.insert(s);

        resp.sendRedirect(req.getContextPath() + "/admin/series");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        ProductSeries s = new ProductSeries();
        s.setId(parseInt(req.getParameter("id")));
        s.setName(req.getParameter("name"));
        dao.update(s);

        resp.sendRedirect(req.getContextPath() + "/admin/series");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = parseInt(req.getParameter("id"));
        dao.delete(id);

        resp.sendRedirect(req.getContextPath() + "/admin/series");
    }

    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}
