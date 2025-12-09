package com.phonestore.controller;

import com.phonestore.dao.BrandDAO;
import com.phonestore.dao.CategoryDAO;
import com.phonestore.model.Brand;
import com.phonestore.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
        "/admin/brand",
        "/admin/brand/add",
        "/admin/brand/insert",
        "/admin/brand/edit",
        "/admin/brand/update",
        "/admin/brand/delete"
})
public class AdminBrandServlet extends HttpServlet {

    private BrandDAO brandDAO = new BrandDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        switch (path) {
            case "/admin/brand":
                listBrands(req, resp);
                break;
            case "/admin/brand/add":
                showAddForm(req, resp);
                break;
            case "/admin/brand/edit":
                showEditForm(req, resp);
                break;
            case "/admin/brand/delete":
                deleteBrand(req, resp);
                break;
            default:
                resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        switch (path) {
            case "/admin/brand/insert":
                insertBrand(req, resp);
                break;
            case "/admin/brand/update":
                updateBrand(req, resp);
                break;
            default:
                resp.sendError(404);
        }
    }

    private void listBrands(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Brand> brands = brandDAO.getAllBrands();
        req.setAttribute("brands", brands);
        req.getRequestDispatcher("/WEB-INF/views/admin/brand-list.jsp").forward(req, resp);
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category> categories = categoryDAO.getAllCategories();
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/WEB-INF/views/admin/brand-form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = parseInt(req.getParameter("id"));
        Brand brand = brandDAO.getBrandById(id);
        if (brand == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/brand");
            return;
        }
        List<Category> categories = categoryDAO.getAllCategories();
        req.setAttribute("categories", categories);
        req.setAttribute("brand", brand);
        req.getRequestDispatcher("/WEB-INF/views/admin/brand-form.jsp").forward(req, resp);
    }

    private void insertBrand(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Brand b = new Brand();
        b.setName(req.getParameter("name"));
        b.setSlug(req.getParameter("slug"));
        b.setLogoUrl(req.getParameter("logoUrl"));
        b.setCategoryId(parseInt(req.getParameter("categoryId")));
        brandDAO.insertBrand(b);
        resp.sendRedirect(req.getContextPath() + "/admin/brand");
    }

    private void updateBrand(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Brand b = new Brand();
        b.setId(parseInt(req.getParameter("id")));
        b.setName(req.getParameter("name"));
        b.setSlug(req.getParameter("slug"));
        b.setLogoUrl(req.getParameter("logoUrl"));
        b.setCategoryId(parseInt(req.getParameter("categoryId")));
        brandDAO.updateBrand(b);
        resp.sendRedirect(req.getContextPath() + "/admin/brand");
    }

    private void deleteBrand(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = parseInt(req.getParameter("id"));
        brandDAO.deleteBrand(id);
        resp.sendRedirect(req.getContextPath() + "/admin/brand");
    }

    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}
