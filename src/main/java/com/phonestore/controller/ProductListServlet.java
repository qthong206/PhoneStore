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
    private BrandDAO brandDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        wishlistDAO = new WishlistDAO();
        brandDAO = new BrandDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Lấy tham số từ URL
            String categorySlug = request.getParameter("category");
            String brandSlug = request.getParameter("brand");
            String sortType = request.getParameter("sort"); // <--- LẤY THAM SỐ SORT

            // 2. Xây dựng Query String để giữ bộ lọc cho các link sắp xếp ở JSP
            // (Ví dụ: đang ở ?category=dien-thoai, bấm sort giá thì URL phải là ?category=dien-thoai&sort=price_asc)
            StringBuilder sb = new StringBuilder();
            if (categorySlug != null) sb.append("category=").append(categorySlug).append("&");
            if (brandSlug != null) sb.append("brand=").append(brandSlug).append("&");
            // Lưu ý: KHÔNG append "sort" vào đây để tránh bị lặp khi bấm sort mới
            request.setAttribute("currentQueryString", sb.toString());


            List<Product> productList;
            Category currentCategory = null;
            Brand currentBrand = null;
            String pageTitle = "Sản phẩm";

            if (brandSlug != null && !brandSlug.isEmpty()) {
                // --- TRƯỜNG HỢP 1: LỌC THEO BRAND ---
                currentBrand = brandDAO.getBrandBySlug(brandSlug);
                if (currentBrand == null) {
                    response.sendError(404, "Không tìm thấy thương hiệu");
                    return;
                }

                currentCategory = categoryDAO.getCategoryById(currentBrand.getCategoryId());
                if (currentCategory == null) {
                    response.sendError(404, "Không tìm thấy danh mục cha");
                    return;
                }

                // Gọi hàm DAO mới có tham số sortType
                productList = productDAO.getProductsByBrandSlug(brandSlug, sortType);
                pageTitle = currentBrand.getName();

            } else if (categorySlug != null && !categorySlug.isEmpty()) {
                // --- TRƯỜNG HỢP 2: LỌC THEO CATEGORY ---
                currentCategory = categoryDAO.getCategoryBySlug(categorySlug);
                if (currentCategory == null) {
                    response.sendError(404, "Không tìm thấy danh mục");
                    return;
                }

                // Gọi hàm DAO mới có tham số sortType
                productList = productDAO.getProductsByCategoryId(currentCategory.getId(), sortType);
                pageTitle = currentCategory.getName();

            } else {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            // --- LẤY WISHLIST (GIỮ NGUYÊN) ---
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
            request.setAttribute("currentCategory", currentCategory);
            request.setAttribute("currentBrand", currentBrand);
            request.setAttribute("pageCss", "productList.css");

            request.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, e.getMessage());
        }
    }
}