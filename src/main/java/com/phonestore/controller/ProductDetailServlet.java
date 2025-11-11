package com.phonestore.controller;

import com.phonestore.dao.ProductDAO;
import com.phonestore.model.Color;
import com.phonestore.model.Product;
import com.phonestore.model.ProductSeries;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/product-detail"})
public class ProductDetailServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO(); // Khởi tạo DAO 1 LẦN
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Lấy ID của "biến thể" (Product) mà người dùng click
            int productId = Integer.parseInt(request.getParameter("id"));

            // 2. Tải "biến thể" (Product) đó
            Product currentProduct = productDAO.getProductById(productId);

            if (currentProduct != null) {
                // 3. Lấy Series ID và Model từ biến thể đó
                int seriesId = currentProduct.getSeriesId();
                String model = currentProduct.getModel(); // VD: "Pro Max"

                // 4. Tải TẤT CẢ các đối tượng liên quan
                ProductSeries series = productDAO.getProductSeriesById(seriesId);
                List<Product> allVariants = productDAO.getVariantsBySeriesAndModel(seriesId, model);
                List<Color> allColors = productDAO.getColorsBySeriesId(seriesId);
                List<String> galleryImages = productDAO.getGalleryImagesByProductId(productId); // <-- Lấy album ảnh

                // 5. Gửi TẤT CẢ (5) dữ liệu sang JSP
                request.setAttribute("product", currentProduct);
                request.setAttribute("series", series);
                request.setAttribute("variants", allVariants);
                request.setAttribute("colors", allColors);
                request.setAttribute("galleryImages", galleryImages); // <-- Gửi album ảnh

                // 6. Gửi thông tin cho <head>
                // (Giả sử 'series' không null, nếu có thể null, cần thêm 1 bước kiểm tra)
                if (series != null) {
                    request.setAttribute("pageTitle", series.getName() + " " + model);
                } else {
                    request.setAttribute("pageTitle", currentProduct.getName()); // Tạm thời
                }
                request.setAttribute("pageCss", "productDetail.css");

                // 7. Chuyển hướng
                request.getRequestDispatcher("/WEB-INF/views/productDetail.jsp").forward(request, response);

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra");
        }
    }
}