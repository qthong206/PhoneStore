package com.phonestore.controller;

import com.phonestore.dao.ProductGalleryDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
        "/admin/product/gallery",
        "/admin/product/gallery/add",
        "/admin/product/gallery/delete"
})
public class AdminProductGalleryServlet extends HttpServlet {

    private ProductGalleryDAO galleryDAO = new ProductGalleryDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        int productId = parseInt(req.getParameter("productId"));
        String path = req.getServletPath();

        switch (path) {
            case "/admin/product/gallery":
                Product product = productDAO.getProductById(productId);
                List<String> images = galleryDAO.getImagesByProductId(productId);

                req.setAttribute("product", product);
                req.setAttribute("images", images);

                req.setAttribute("pageCss","gallery.css");
                req.setAttribute("contentPage","/WEB-INF/views/admin/product-gallery.jsp");
                req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp")
                        .forward(req, resp);
                break;

            case "/admin/product/gallery/delete":
                String url = req.getParameter("url");
                galleryDAO.deleteImage(productId, url);
                resp.sendRedirect(req.getContextPath() + "/admin/product/gallery?productId=" + productId);
                break;

            default:
                resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int productId = parseInt(req.getParameter("productId"));
        String url = req.getParameter("imageUrl");
        galleryDAO.insertImage(productId, url);

        resp.sendRedirect(req.getContextPath() + "/admin/product/gallery?productId=" + productId);
    }

    private int parseInt(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}

