package com.phonestore.controller;

import com.phonestore.dao.CategoryDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.WishlistDAO;
import com.phonestore.model.Category;
import com.phonestore.model.Product;
import com.phonestore.model.User;
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

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        wishlistDAO = new WishlistDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String categorySlug = request.getParameter("category");

            List<Product> productList = null;
            String pageTitle = "Tất cả sản phẩm";

            if (categorySlug != null && !categorySlug.isEmpty()) {
                Category category = categoryDAO.getCategoryBySlug(categorySlug);

                if (category != null) {
                    pageTitle = category.getName();
                    productList = productDAO.getProductsByCategoryId(category.getId());
                } else {
                    response.sendError(404, "Không tìm thấy danh mục");
                    return;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("user") : null;
            Set<Integer> wishlistIds = new HashSet<>();
            if (user != null) {
                wishlistIds = wishlistDAO.getWishlistProductIds(user.getId());
            }

            request.setAttribute("products", productList);
            request.setAttribute("pageTitle", pageTitle);
            request.setAttribute("wishlistIds", wishlistIds);
            request.setAttribute("pageCss", "productList.css");

            request.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}