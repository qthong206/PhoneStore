package com.phonestore.controller;

import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.ReviewDAO;
import com.phonestore.dao.SpecificationDAO;
import com.phonestore.dao.WishlistDAO;
import com.phonestore.model.*;
import com.phonestore.utils.ViewHelper;
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

            // 1. Sản phẩm chính
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
                return;
            }

            // 2. Thông tin liên quan
            ProductSeries series = productDAO.getProductSeriesById(product.getSeriesId());
            List<Product> variants = productDAO.getVariantsBySeriesAndModel(product.getSeriesId(), product.getModel());
            List<Color> colors = productDAO.getColorsBySeriesId(product.getSeriesId());
            List<String> galleryImages = productDAO.getGalleryImagesByProductId(productId);

            // 3. Reviews & Specs
            List<ReviewDetailDTO> reviews = reviewDAO.getReviewsByProductId(productId);
            ReviewSummaryDTO reviewSummary = reviewDAO.getReviewSummary(productId);
            List<Specification> specsList = specificationDAO.getSpecificationsByProductId(productId);

            // --- 4. WISHLIST (SỬ DỤNG VIEW HELPER) ---
            ViewHelper.loadWishlistData(request, wishlistDAO);

            // Riêng trang Detail cần check thêm biến "isFavorited" (sản phẩm này có tim chưa)
            // Ta có thể tận dụng dữ liệu wishlistIds vừa được ViewHelper lấy
            // (Lưu ý: Phải ép kiểu Set vì setAttribute lưu Object)
            @SuppressWarnings("unchecked")
            java.util.Set<Integer> wishlistIds = (java.util.Set<Integer>) request.getAttribute("wishlistIds");
            boolean isFavorited = wishlistIds.contains(productId);
            request.setAttribute("isFavorited", isFavorited);
            // ----------------------------------------

            // 5. Sản phẩm tương tự
            int brandId = product.getBrand().getId();
            List<Product> relatedProducts = productDAO.getRelatedProductsByBrand(brandId, productId, 5);

            // 6. Breadcrumbs (Category)
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

            // 7. Gửi dữ liệu
            request.setAttribute("product", product);
            request.setAttribute("series", series);
            request.setAttribute("variants", variants);
            request.setAttribute("colors", colors);
            request.setAttribute("galleryImages", galleryImages);
            request.setAttribute("reviews", reviews);
            request.setAttribute("reviewSummary", reviewSummary);
            request.setAttribute("specsList", specsList);
            request.setAttribute("relatedProducts", relatedProducts);
            request.setAttribute("category", currentCategory);

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