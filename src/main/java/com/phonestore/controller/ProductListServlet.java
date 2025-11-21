package com.phonestore.controller;

import com.phonestore.dao.CategoryDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.WishlistDAO;
import com.phonestore.dao.BrandDAO;
import com.phonestore.model.Category;
import com.phonestore.model.Product;
import com.phonestore.model.User;
import com.phonestore.model.Brand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = {"/products"})
public class ProductListServlet extends HttpServlet {

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private WishlistDAO wishlistDAO;
    private BrandDAO brandDAO; // 3. KHAI BÁO BRAND DAO

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        wishlistDAO = new WishlistDAO();
        brandDAO = new BrandDAO(); // 4. KHỞI TẠO BRAND DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String categorySlug = request.getParameter("category");
            String brandSlug = request.getParameter("brand");

            List<Product> productList;
            Category currentCategory = null; // Đối tượng Category (VD: "Điện thoại")
            Brand currentBrand = null; // Đối tượng Brand (VD: "Apple")
            String pageTitle = "Sản phẩm";

            if (brandSlug != null && !brandSlug.isEmpty()) {
                // --- TRƯỜNG HỢP 1: LỌC THEO BRAND (VD: /products?brand=apple) ---
                currentBrand = brandDAO.getBrandBySlug(brandSlug);
                if (currentBrand == null) {
                    response.sendError(404, "Không tìm thấy thương hiệu");
                    return;
                }

                // Tìm Category cha của Brand này
                currentCategory = categoryDAO.getCategoryById(currentBrand.getCategoryId());
                if (currentCategory == null) {
                    response.sendError(404, "Không tìm thấy danh mục cha");
                    return;
                }

                productList = productDAO.getProductsByBrandSlug(brandSlug);
                pageTitle = currentBrand.getName();

            } else if (categorySlug != null && !categorySlug.isEmpty()) {
                // --- TRƯỜNG HỢP 2: LỌC THEO CATEGORY (VD: /products?category=dien-thoai) ---
                currentCategory = categoryDAO.getCategoryBySlug(categorySlug);
                if (currentCategory == null) {
                    response.sendError(404, "Không tìm thấy danh mục");
                    return;
                }

                productList = productDAO.getProductsByCategoryId(currentCategory.getId());
                pageTitle = currentCategory.getName();

            } else {
                // Không có filter, quay về trang chủ
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            // --- LẤY WISHLIST (NHƯ CŨ) ---
            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("user") : null;
            Set<Integer> wishlistIds = new HashSet<>();
            if (user != null) {
                wishlistIds = wishlistDAO.getWishlistProductIds(user.getId());
            }

            // --- GỬI DỮ LIỆU SANG JSP ---
            request.setAttribute("products", productList);
            request.setAttribute("wishlistIds", wishlistIds);
            request.setAttribute("pageTitle", pageTitle);

            // Gửi các đối tượng breadcrumb
            request.setAttribute("currentCategory", currentCategory);
            request.setAttribute("currentBrand", currentBrand); // Sẽ là null nếu chỉ lọc Category

            request.setAttribute("pageCss", "productList.css");
            request.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, e.getMessage());
        }
    }
}