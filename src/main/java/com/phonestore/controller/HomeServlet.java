package com.phonestore.controller;

import com.phonestore.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Danh sách sản phẩm giả lập (sau này có thể lấy từ DB)
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "iPhone 15 Pro Max", 29990000.0, "images/iphone15.jpg"));
        productList.add(new Product(2, "Samsung Galaxy S24", 20990000.0, "images/samsung24.jpg"));
        productList.add(new Product(3, "Google Pixel 8", 15490000.0, "images/pixel8.jpg"));

        // Gửi sang JSP
        request.setAttribute("productList", productList);

        // Forward đến trang home.jsp trong thư mục views
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}
