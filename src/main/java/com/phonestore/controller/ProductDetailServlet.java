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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                return; // <-- Dòng này đã đúng
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

            // --- 4. LẤY THÔNG TIN WISHLIST ---
            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("user") : null;
            boolean isFavorited = false;
            Set<Integer> wishlistIds = new HashSet<>();
            if (user != null) {
                isFavorited = wishlistDAO.isProductInWishlist(user.getId(), productId);
                wishlistIds = wishlistDAO.getWishlistProductIds(user.getId());
            }

            // --- 5. LẤY SẢN PHẨM TƯƠNG TỰ (5 SẢN PHẨM) ---
            int brandId = product.getBrand().getId();
            List<Product> relatedProducts = productDAO.getRelatedProductsByBrand(brandId, productId, 5);

            // --- 6. TÌM CATEGORY CHA (CHO BREADCRUMBS) ---
            int categoryId = product.getBrand().getCategoryId();
            List<Category> allCategories = (List<Category>) getServletContext().getAttribute("allCategories");
            Category currentCategory = null;
            if (allCategories != null) {
                for (Category cat : allCategories) {
                    if (cat.getId() == categoryId) {
                        currentCategory = cat;
                        break;
                    }
                }
            }

            // --- 7. GỬI TẤT CẢ DỮ LIỆU SANG JSP ---
            request.setAttribute("product", product);
            request.setAttribute("series", series);
            request.setAttribute("variants", variants);
            request.setAttribute("colors", colors);
            request.setAttribute("galleryImages", galleryImages);
            request.setAttribute("reviews", reviews);
            request.setAttribute("reviewSummary", reviewSummary);
            request.setAttribute("specsList", specsList);
            request.setAttribute("isFavorited", isFavorited);
            request.setAttribute("wishlistIds", wishlistIds);
            request.setAttribute("relatedProducts", relatedProducts);
            request.setAttribute("category", currentCategory);

            // --- 8. FORWARD (Dòng 99 của bạn) ---
            request.setAttribute("pageTitle", product.getName());
            request.setAttribute("pageCss", "productDetail.css");
            request.getRequestDispatcher("/WEB-INF/views/productDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ");
            return;

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra");
            return;
        }
    }
}