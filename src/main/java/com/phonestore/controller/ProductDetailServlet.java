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

import java.io.IOException;
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
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            int productId = Integer.parseInt(idParam);

            // 1. Lấy Sản phẩm chính
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
                return;
            }

            // 2. Lấy thông tin liên quan (Series, Variants, Colors)
            ProductSeries series = productDAO.getProductSeriesById(product.getSeriesId());
            List<Product> variants = productDAO.getVariantsBySeriesAndModel(product.getSeriesId(), product.getModel());

            // [MÀU SẮC 1 - LỰA CHỌN]: Lấy tất cả màu của các SP cùng Series và cùng Model để vẽ nút chọn màu
            List<Color> modelColors = productDAO.getSameModelColors(product.getSeriesId(), product.getModel());

            // [MÀU SẮC 2 - HIỆN TẠI]: Lấy màu của chính SP này để biết đang active màu nào
            List<Color> currentColors = productDAO.getColorsByProductId(productId);
            Color activeColor = (currentColors != null && !currentColors.isEmpty()) ? currentColors.get(0) : null;

            // Lấy ảnh Gallery
            List<String> galleryImages = productDAO.getGalleryImagesByProductId(productId);

            // 3. Reviews & Specs
            List<ReviewDetailDTO> reviews = reviewDAO.getReviewsByProductId(productId);
            ReviewSummaryDTO reviewSummary = reviewDAO.getReviewSummary(productId);
            List<Specification> specsList = specificationDAO.getSpecificationsByProductId(productId);

            // 4. Wishlist (Xử lý an toàn)
            ViewHelper.loadWishlistData(request, wishlistDAO);
            boolean isFavorited = false;
            Object wishlistObj = request.getAttribute("wishlistIds");
            if (wishlistObj instanceof Set<?>) {
                @SuppressWarnings("unchecked")
                Set<Integer> wishlistIds = (Set<Integer>) wishlistObj;
                isFavorited = wishlistIds.contains(productId);
            }
            request.setAttribute("isFavorited", isFavorited);

            // 5. Sản phẩm tương tự (Cùng Brand)
            int brandId = (product.getBrand() != null) ? product.getBrand().getId() : 0;
            List<Product> relatedProducts = productDAO.getRelatedProductsByBrand(brandId, productId, 5);

            // 6. Breadcrumbs (Lấy Category từ Product, không phải Brand)
            int categoryId = product.getCategoryId();
            @SuppressWarnings("unchecked")
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

            // 7. Gửi dữ liệu sang JSP
            request.setAttribute("product", product);
            request.setAttribute("series", series);
            request.setAttribute("variants", variants);

            // Gửi cả 2 loại màu để JSP xử lý
            request.setAttribute("colors", modelColors); // Danh sách nút bấm
            request.setAttribute("activeColor", activeColor); // Màu đang chọn

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