package com.phonestore.controller;

import com.phonestore.dao.ColorDAO;
import com.phonestore.model.Color;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
        "/admin/color",
        "/admin/color/add",
        "/admin/color/insert",
        "/admin/color/edit",
        "/admin/color/update",
        "/admin/color/delete"
})
public class AdminColorServlet extends HttpServlet {

    private ColorDAO dao = new ColorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        switch (path) {
            case "/admin/color":
                list(req, resp);
                break;

            case "/admin/color/add":
                showForm(req, resp, null);
                break;

            case "/admin/color/edit":
                int id = parseInt(req.getParameter("id"));
                Color c = dao.getById(id);
                showForm(req, resp, c);
                break;

            case "/admin/color/delete":
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
            case "/admin/color/insert":
                insert(req, resp);
                break;

            case "/admin/color/update":
                update(req, resp);
                break;

            default:
                resp.sendError(404);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Color> list = dao.getAll();
        req.setAttribute("colors", list);

        req.setAttribute("pageCss","color-list.css");
        req.setAttribute("contentPage","/WEB-INF/views/admin/color-list.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Color c)
            throws ServletException, IOException {

        req.setAttribute("item", c);
        req.setAttribute("pageCss","color-form.css");
        req.setAttribute("contentPage","/WEB-INF/views/admin/color-form.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Color c = new Color();
        c.setName(req.getParameter("name"));
        c.setHexCode(req.getParameter("hexCode"));
        dao.insert(c);

        resp.sendRedirect(req.getContextPath() + "/admin/color");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Color c = new Color();
        c.setId(parseInt(req.getParameter("id")));
        c.setName(req.getParameter("name"));
        c.setHexCode(req.getParameter("hexCode"));
        dao.update(c);

        resp.sendRedirect(req.getContextPath() + "/admin/color");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = parseInt(req.getParameter("id"));
        dao.delete(id);

        resp.sendRedirect(req.getContextPath() + "/admin/color");
    }

    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}
