package com.phonestore.controller;

import com.phonestore.dao.ProductDAO;
import com.phonestore.model.Brand;
import com.phonestore.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

// Đặt servlet làm trang mặc định của web
@WebServlet(name = "HomeServlet", urlPatterns = {"/home", ""})
public class HomeServlet extends HttpServlet {

    private ProductDAO productDAO;

    // Phương thức init() được gọi một lần khi servlet khởi tạo
    // Dùng để khởi tạo DAO, tránh tạo lại mỗi lần có request
    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Gọi ProductDAO để lấy dữ liệu đã được nhóm theo thương hiệu
        Map<Brand, List<Product>> productMap = productDAO.getProductsGroupedByBrand();

        // 2. Gửi Map này sang JSP
        request.setAttribute("productMap", productMap);

        // 3. Forward đến trang view để hiển thị
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}