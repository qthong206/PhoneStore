package com.phonestore.controller;

import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.WishlistDAO;
import com.phonestore.model.Brand;
import com.phonestore.model.Product;
import com.phonestore.utils.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", ""})
public class HomeServlet extends HttpServlet {

    private ProductDAO productDAO;
    private WishlistDAO wishlistDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        wishlistDAO = new WishlistDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. LẤY SẢN PHẨM
            Map<Brand, List<Product>> productMap = productDAO.getProductsGroupedByBrand();
            request.setAttribute("productMap", productMap);

            // 2. LẤY WISHLIST
            ViewHelper.loadWishlistData(request, wishlistDAO);

            // 3. THIẾT LẬP GIAO DIỆN
            request.setAttribute("pageTitle", "Trang chủ");
            request.setAttribute("pageCss", "home.css");
            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}