package com.phonestore.controller;

import com.phonestore.dao.CategoryDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.WishlistDAO;
import com.phonestore.dao.BrandDAO;
import com.phonestore.model.Category;
import com.phonestore.model.Product;
import com.phonestore.model.Brand;
import com.phonestore.utils.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

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
            String categorySlug = request.getParameter("category");
            String brandSlug = request.getParameter("brand");
            String sortType = request.getParameter("sort");

            StringBuilder sb = new StringBuilder();
            if (categorySlug != null) sb.append("category=").append(categorySlug).append("&");
            if (brandSlug != null) sb.append("brand=").append(brandSlug).append("&");
            request.setAttribute("currentQueryString", sb.toString());

            List<Product> productList;
            Category currentCategory = null;
            Brand currentBrand = null;
            String pageTitle = "Tất cả sản phẩm";

            if (brandSlug != null && !brandSlug.isEmpty()) {
                currentBrand = brandDAO.getBrandBySlug(brandSlug);

                if (currentBrand != null) {
                    currentCategory = categoryDAO.getCategoryById(currentBrand.getCategoryId());
                    productList = productDAO.getProductsByBrandSlug(brandSlug, sortType);
                    pageTitle = currentBrand.getName();
                } else {
                    productList = productDAO.getAllProducts(sortType);
                }

            } else if (categorySlug != null && !categorySlug.isEmpty()) {
                currentCategory = categoryDAO.getCategoryBySlug(categorySlug);

                if (currentCategory != null) {
                    productList = productDAO.getProductsByCategoryId(currentCategory.getId(), sortType);
                    pageTitle = currentCategory.getName();
                } else {
                    productList = productDAO.getAllProducts(sortType);
                }

            } else {
                productList = productDAO.getAllProducts(sortType);
            }

            ViewHelper.loadWishlistData(request, wishlistDAO);

            request.setAttribute("products", productList);
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