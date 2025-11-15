package com.phonestore.controller;

import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.ReviewDAO;
import com.phonestore.dao.WishlistDAO; // 1. Import WishlistDAO
import com.phonestore.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/product-detail"})
public class ProductDetailServlet extends HttpServlet {

    private ProductDAO productDAO;
    private ReviewDAO reviewDAO;
    private WishlistDAO wishlistDAO; // 2. Khai báo

    @Override
    public void init() {
        productDAO = new ProductDAO();
        reviewDAO = new ReviewDAO();
        wishlistDAO = new WishlistDAO(); // 3. Khởi tạo
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            Product currentProduct = productDAO.getProductById(productId);

            if (currentProduct != null) {
                // ... (code cũ lấy 5 đối tượng)
                int seriesId = currentProduct.getSeriesId();
                String model = currentProduct.getModel();
                ProductSeries series = productDAO.getProductSeriesById(seriesId);
                List<Product> allVariants = productDAO.getVariantsBySeriesAndModel(seriesId, model);
                List<Color> allColors = productDAO.getColorsBySeriesId(seriesId);
                List<String> galleryImages = productDAO.getGalleryImagesByProductId(productId);

                // ... (code cũ lấy 2 đối tượng review)
                List<ReviewDetailDTO> reviews = reviewDAO.getReviewsByProductId(productId);
                ReviewSummaryDTO reviewSummary = reviewDAO.getReviewSummary(productId);

                // 4. KIỂM TRA WISHLSIT (MỚI)
                HttpSession session = request.getSession(false);
                User user = (session != null) ? (User) session.getAttribute("user") : null;
                boolean isFavorited = false;
                if (user != null) {
                    isFavorited = wishlistDAO.isProductInWishlist(user.getId(), productId);
                }

                // 5. Gửi TẤT CẢ (5 + 2 + 1 MỚI) sang JSP
                request.setAttribute("product", currentProduct);
                request.setAttribute("series", series);
                request.setAttribute("variants", allVariants);
                request.setAttribute("colors", allColors);
                request.setAttribute("galleryImages", galleryImages);
                request.setAttribute("reviews", reviews);
                request.setAttribute("reviewSummary", reviewSummary);
                request.setAttribute("isFavorited", isFavorited); // 6. Gửi biến "đã thích"

                // ... (code cũ gửi pageTitle, pageCss, và forward)
                if (series != null) {
                    request.setAttribute("pageTitle", series.getName() + " " + model);
                } else {
                    request.setAttribute("pageTitle", currentProduct.getName());
                }
                request.setAttribute("pageCss", "productDetail.css");

                request.getRequestDispatcher("/WEB-INF/views/productDetail.jsp").forward(request, response);

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra");
        }
    }
}