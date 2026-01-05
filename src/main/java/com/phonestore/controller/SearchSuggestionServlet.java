package com.phonestore.controller;

import com.google.gson.Gson;
import com.phonestore.dao.ProductDAO;
import com.phonestore.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/api/search-suggestion"})
public class SearchSuggestionServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("q");
        Map<String, Object> result = new HashMap<>();

        if (keyword != null && keyword.trim().length() > 0) {
            String q = keyword.trim();

            List<String> keywords = productDAO.searchKeywords(q, 5);

            // 2. Lấy sản phẩm gợi ý (Giữ nguyên logic cũ)
            List<Product> products = productDAO.searchProductsLimit(q, 5);

            result.put("keywords", keywords);
            result.put("products", products);
        }

        new Gson().toJson(result, response.getWriter());
    }
}