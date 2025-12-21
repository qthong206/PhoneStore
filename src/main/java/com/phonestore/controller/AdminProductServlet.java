package com.phonestore.controller;

import com.phonestore.dao.BrandDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.ProductSeriesDAO;
import com.phonestore.model.Brand;
import com.phonestore.model.Product;
import com.phonestore.model.ProductSeries;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {
        "/admin/product",
        "/admin/product/add",
        "/admin/product/insert",
        "/admin/product/edit",
        "/admin/product/update",
        "/admin/product/delete",
        "/admin/product/toggle"
})
public class AdminProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    // --- KHAI BÁO THÊM DAO ĐỂ LOAD DROPDOWN ---
    private BrandDAO brandDAO = new BrandDAO();
    private ProductSeriesDAO seriesDAO = new ProductSeriesDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        switch (path) {
            case "/admin/product":
                listProducts(req, resp);
                break;

            case "/admin/product/add":
                showAddForm(req, resp);
                break;

            case "/admin/product/edit":
                showEditForm(req, resp);
                break;

            case "/admin/product/delete":
                deleteProduct(req, resp);
                break;

            case "/admin/product/toggle":
                toggleStatus(req, resp);
                break;

            default:
                resp.sendError(404, "Not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        switch (path) {
            case "/admin/product/insert":
                insertProduct(req, resp);
                break;

            case "/admin/product/update":
                updateProduct(req, resp);
                break;

            default:
                resp.sendError(404);
        }
    }

    /* ================================
                LIST PRODUCTS
       ================================ */
    private void listProducts(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<Brand, List<Product>> productMap = productDAO.getProductsGroupedByBrand();
        req.setAttribute("productMap", productMap);

        req.setAttribute("pageCss","product.css");
        req.setAttribute("contentPage","/WEB-INF/views/admin/product-list.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }

    /* ================================
                SHOW ADD FORM
       ================================ */
    private void showAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // --- LOAD DATA CHO DROPDOWN (QUAN TRỌNG) ---
        List<Brand> brands = brandDAO.getAllBrands();
        List<ProductSeries> series = seriesDAO.getAllSeries();

        req.setAttribute("brands", brands);
        req.setAttribute("series", series);
        // -------------------------------------------

        req.setAttribute("pageCss","product-from.css");
        req.setAttribute("contentPage","/WEB-INF/views/admin/product-form.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }

    /* ================================
                INSERT PRODUCT
       ================================ */
    private void insertProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Product p = new Product();
        p.setName(req.getParameter("name"));
        p.setDescription(req.getParameter("description"));
        p.setPrice(parseDouble(req.getParameter("price")));
        p.setSalePrice(parseDouble(req.getParameter("salePrice")));
        p.setThumbnailUrl(req.getParameter("thumbnailUrl"));
        p.setSeriesId(parseInt(req.getParameter("seriesId")));
        p.setModel(req.getParameter("model"));
        p.setStorage(req.getParameter("storage"));
        p.setStatus(parseInt(req.getParameter("status")));

        Brand b = new Brand();
        b.setId(parseInt(req.getParameter("brandId")));
        p.setBrand(b);

        productDAO.insertProduct(p);

        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }

    /* ================================
                SHOW EDIT FORM
       ================================ */
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = parseInt(req.getParameter("id"));
        Product product = productDAO.getProductById(id);

        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/product");
            return;
        }

        // --- LOAD DATA CHO DROPDOWN (QUAN TRỌNG) ---
        List<Brand> brands = brandDAO.getAllBrands();
        List<ProductSeries> series = seriesDAO.getAllSeries();

        req.setAttribute("brands", brands);
        req.setAttribute("series", series);
        // -------------------------------------------

        req.setAttribute("product", product);

        req.setAttribute("pageCss","product-from.css");
        req.setAttribute("contentPage","/WEB-INF/views/admin/product-form.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                .forward(req, resp);
    }

    /* ================================
                UPDATE PRODUCT
       ================================ */
    private void updateProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Product p = new Product();
        p.setId(parseInt(req.getParameter("id")));
        p.setName(req.getParameter("name"));
        p.setDescription(req.getParameter("description"));
        p.setPrice(parseDouble(req.getParameter("price")));
        p.setSalePrice(parseDouble(req.getParameter("salePrice")));
        p.setThumbnailUrl(req.getParameter("thumbnailUrl"));
        p.setSeriesId(parseInt(req.getParameter("seriesId")));
        p.setModel(req.getParameter("model"));
        p.setStorage(req.getParameter("storage"));
        p.setStatus(parseInt(req.getParameter("status")));

        Brand b = new Brand();
        b.setId(parseInt(req.getParameter("brandId")));
        p.setBrand(b);

        productDAO.updateProduct(p);

        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }

    /* ================================
                DELETE PRODUCT
       ================================ */
    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = parseInt(req.getParameter("id"));
        productDAO.deleteProduct(id);

        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }

    /* ================================
                TOGGLE STATUS
       ================================ */
    private void toggleStatus(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = parseInt(req.getParameter("id"));
        productDAO.toggleStatus(id);

        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }

    /* ================================
                Helper
       ================================ */
    private int parseInt(String s) {
        try { return Integer.parseInt(s); }
        catch (Exception e) { return 0; }
    }

    private double parseDouble(String s) {
        try { return Double.parseDouble(s); }
        catch (Exception e) { return 0; }
    }
}