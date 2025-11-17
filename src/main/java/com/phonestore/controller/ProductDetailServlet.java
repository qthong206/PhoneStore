package com.phonestore.controller;

import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.ReviewDAO;
import com.phonestore.dao.SpecificationDAO;
import com.phonestore.dao.WishlistDAO;
import com.phonestore.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet; // 1. IMPORT HASHSET
import java.util.List;
import java.util.Set; // 2. IMPORT SET

@WebServlet(urlPatterns = {"/product-detail"})
public class ProductDetailServlet extends HttpServlet {

    private ProductDAO productDAO;
    private ReviewDAO reviewDAO;
    private WishlistDAO wishlistDAO;
    private SpecificationDAO specificationDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        reviewDAO = new ReviewDAO();
        wishlistDAO = new WishlistDAO();
        specificationDAO = new SpecificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int productId = Integer.parseInt(request.getParameter("id"));

            // --- 1. LẤY SẢN PHẨM CHÍNH ---
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
                return;
            }

            // --- 2. LẤY THÔNG TIN LIÊN QUAN (Variants, Colors...) ---
            ProductSeries series = productDAO.getProductSeriesById(product.getSeriesId());
            List<Product> variants = productDAO.getVariantsBySeriesAndModel(product.getSeriesId(), product.getModel());
            List<Color> colors = productDAO.getColorsBySeriesId(product.getSeriesId());
            List<String> galleryImages = productDAO.getGalleryImagesByProductId(productId);

            // --- 3. LẤY REVIEWS & SPECS ---
            List<ReviewDetailDTO> reviews = reviewDAO.getReviewsByProductId(productId);
            ReviewSummaryDTO reviewSummary = reviewDAO.getReviewSummary(productId);
            List<Specification> specsList = specificationDAO.getSpecificationsByProductId(productId);

            // --- 4. LẤY THÔNG TIN WISHLIST (CHO CẢ SẢN PHẨM CHÍNH VÀ SP TƯƠNG TỰ) ---
            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("user") : null;

            boolean isFavorited = false; // Chỉ cho sản phẩm chính
            Set<Integer> wishlistIds = new HashSet<>(); // Cho sản phẩm tương tự

            if (user != null) {
                isFavorited = wishlistDAO.isProductInWishlist(user.getId(), productId);
                wishlistIds = wishlistDAO.getWishlistProductIds(user.getId()); // Lấy toàn bộ
            }

            // --- 5. LOGIC MỚI: LẤY 5 SẢN PHẨM TƯƠNG TỰ ---
            int brandId = product.getBrand().getId();
            List<Product> relatedProducts = productDAO.getRelatedProductsByBrand(brandId, productId, 5); // ĐÃ SỬA THÀNH 5


            // --- 6. GỬI TẤT CẢ DỮ LIỆU SANG JSP ---
            request.setAttribute("product", product);
            request.setAttribute("series", series);
            request.setAttribute("variants", variants);
            request.setAttribute("colors", colors);
            request.setAttribute("galleryImages", galleryImages);
            request.setAttribute("reviews", reviews);
            request.setAttribute("reviewSummary", reviewSummary);
            request.setAttribute("specsList", specsList);
            request.setAttribute("isFavorited", isFavorited); // Dùng cho nút tim chính
            request.setAttribute("wishlistIds", wishlistIds); // Dùng cho các nút tim SP tương tự
            request.setAttribute("relatedProducts", relatedProducts); // DANH SÁCH MỚI

            // --- 7. FORWARD ---
            request.setAttribute("pageTitle", product.getName());
            request.setAttribute("pageCss", "productDetail.css");
            request.getRequestDispatcher("/WEB-INF/views/productDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra");
        }
    }
}