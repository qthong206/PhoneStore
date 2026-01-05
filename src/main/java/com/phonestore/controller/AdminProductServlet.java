package com.phonestore.controller;

import com.phonestore.dao.*;
import com.phonestore.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {
        "/admin/product", "/admin/product/add", "/admin/product/insert",
        "/admin/product/edit", "/admin/product/update", "/admin/product/delete", "/admin/product/toggle"
})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AdminProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    private BrandDAO brandDAO = new BrandDAO();
    private ProductSeriesDAO seriesDAO = new ProductSeriesDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private ProductGalleryDAO galleryDAO = new ProductGalleryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/admin/product": listProducts(req, resp); break;
            case "/admin/product/add": showAddForm(req, resp); break;
            case "/admin/product/edit": showEditForm(req, resp); break;
            case "/admin/product/delete": deleteProduct(req, resp); break;
            case "/admin/product/toggle": toggleStatus(req, resp); break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.contains("/insert")) insertProduct(req, resp);
        else if (path.contains("/update")) updateProduct(req, resp);
    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Product p = extractProduct(req);
        List<String> paths = saveFiles(req);

        if (!paths.isEmpty()) p.setThumbnailUrl(paths.get(0)); // Ảnh 1 làm thumbnail

        int productId = productDAO.insertProduct(p); // Hàm này phải trả về ID (Generated Keys)

        for (int i = 0; i < paths.size(); i++) {
            galleryDAO.insertImage(productId, paths.get(i), i); // Lưu gallery kèm thứ tự
        }
        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product p = extractProduct(req);
        p.setId(id);

        List<String> newPaths = saveFiles(req);
        if (!newPaths.isEmpty()) {
            p.setThumbnailUrl(newPaths.get(0));
            galleryDAO.deleteAllByProductId(id); // Xóa cũ ghi mới để cập nhật thứ tự
            for (int i = 0; i < newPaths.size(); i++) {
                galleryDAO.insertImage(id, newPaths.get(i), i);
            }
        } else {
            p.setThumbnailUrl(req.getParameter("oldThumbnail"));
        }

        productDAO.updateProduct(p);
        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }

    private List<String> saveFiles(HttpServletRequest req) throws IOException, ServletException {
        List<String> filePaths = new ArrayList<>();
        String uploadPath = getServletContext().getRealPath("/") + "images/products";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        for (Part part : req.getParts()) {
            if (part.getName().equals("imageFiles") && part.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + part.getSubmittedFileName();
                part.write(uploadPath + File.separator + fileName);
                filePaths.add("images/products/" + fileName);
            }
        }
        return filePaths;
    }

    private Product extractProduct(HttpServletRequest req) {
        Product p = new Product();

        p.setName(req.getParameter("name"));
        p.setDescription(req.getParameter("description"));
        p.setModel(req.getParameter("model"));
        p.setStorage(req.getParameter("storage"));

        // Sử dụng các hàm parseInt/parseDouble có bọc try-catch
        p.setPrice(parseDouble(req.getParameter("price")));
        p.setSalePrice(parseDouble(req.getParameter("salePrice")));
        p.setStockQuantity(parseInt(req.getParameter("stockQuantity")));
        p.setCategoryId(parseInt(req.getParameter("categoryId")));
        p.setSeriesId(parseInt(req.getParameter("seriesId")));
        p.setStatus(parseInt(req.getParameter("status")));
        int brandId = parseInt(req.getParameter("brandId"));
        p.setBrand(new Brand(brandId));

        return p;
    }

    // Đảm bảo bạn đã có 2 hàm helper này ở cuối file Servlet
    private int parseInt(String s) {
        try {
            return (s == null || s.trim().isEmpty()) ? 0 : Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDouble(String s) {
        try {
            return (s == null || s.trim().isEmpty()) ? 0 : Double.parseDouble(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    // Các hàm showAddForm, showEditForm, listProducts giữ nguyên như bạn đã có...
    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productMap", productDAO.getAllProductsGroupedByBrandForAdmin());
        req.setAttribute("contentPage","/WEB-INF/views/admin/product-list.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp").forward(req, resp);
    }
    private void showAddForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("brands", brandDAO.getAllBrands());
        req.setAttribute("series", seriesDAO.getAllSeries());
        req.setAttribute("categories", categoryDAO.getAllCategories());
        req.setAttribute("contentPage","/WEB-INF/views/admin/product-form.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp").forward(req, resp);
    }
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = productDAO.getProductById(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("brands", brandDAO.getAllBrands());
        req.setAttribute("series", seriesDAO.getAllSeries());
        req.setAttribute("categories", categoryDAO.getAllCategories());
        req.setAttribute("product", product);
        req.setAttribute("contentPage","/WEB-INF/views/admin/product-form.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/layout-admin.jsp").forward(req, resp);
    }
    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productDAO.deleteProduct(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }
    private void toggleStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productDAO.toggleStatus(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }
}