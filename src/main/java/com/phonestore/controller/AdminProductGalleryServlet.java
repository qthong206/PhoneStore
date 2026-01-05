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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));
        String path = req.getServletPath();

        if (path.equals("/admin/product/gallery")) {
            req.setAttribute("product", productDAO.getProductById(productId));
            req.setAttribute("images", galleryDAO.getImagesByProductId(productId));
            req.setAttribute("contentPage","/WEB-INF/views/admin/product-gallery.jsp");
            req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp").forward(req, resp);
        } else if (path.equals("/admin/product/gallery/delete")) {
            galleryDAO.deleteImage(productId, req.getParameter("url"));
            resp.sendRedirect(req.getContextPath() + "/admin/product/gallery?productId=" + productId);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int productId = Integer.parseInt(req.getParameter("productId"));
        String url = req.getParameter("imageUrl");

        // Nhận thêm sortOrder từ giao diện Gallery (nếu có), mặc định là 0
        int sortOrder = 0;
        try {
            sortOrder = Integer.parseInt(req.getParameter("sortOrder"));
        } catch (Exception e) {}

        // GỌI HÀM DAO: Đã có 3 tham số, khớp hoàn toàn với DAO mới
        galleryDAO.insertImage(productId, url, sortOrder);

        resp.sendRedirect(req.getContextPath() + "/admin/product/gallery?productId=" + productId);
    }
}