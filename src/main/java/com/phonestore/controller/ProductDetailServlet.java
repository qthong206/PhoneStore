package com.phonestore.controller;

import com.phonestore.dao.ProductDAO;
import com.phonestore.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/product-detail"})
public class ProductDetailServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Lấy ID sản phẩm từ URL
            int productId = Integer.parseInt(request.getParameter("id"));

            // 2. Gọi DAO để tìm sản phẩm trong database
            Product product = productDAO.getProductById(productId);

            if (product != null) {
                // 3. Nếu tìm thấy, gửi đối tượng product sang cho JSP
                request.setAttribute("product", product);

                // 4. Chuyển hướng đến trang JSP để hiển thị
                request.getRequestDispatcher("/WEB-INF/views/productDetail.jsp").forward(request, response);
            } else {
                // Nếu không tìm thấy sản phẩm, báo lỗi 404
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sản phẩm không tồn tại");
            }
        } catch (NumberFormatException e) {
            // Nếu id trên URL không phải là số, báo lỗi
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ");
        }
    }
}