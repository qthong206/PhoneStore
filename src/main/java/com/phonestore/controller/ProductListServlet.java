package com.phonestore.controller;

import com.phonestore.dao.CategoryDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.WishlistDAO;
import com.phonestore.dao.BrandDAO;
import com.phonestore.model.Category;
import com.phonestore.model.Product;
import com.phonestore.model.Brand;
import com.phonestore.utils.ViewHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/products"})
public class ProductListServlet extends HttpServlet {

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private WishlistDAO wishlistDAO;
    private BrandDAO brandDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        wishlistDAO = new WishlistDAO();
        brandDAO = new BrandDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Lấy tham số từ URL
            String categorySlug = request.getParameter("category");
            String brandSlug = request.getParameter("brand");
            String search = request.getParameter("q"); // Từ khóa tìm kiếm
            String sortType = request.getParameter("sort");

            // Xử lý trang (Pagination)
            int page = 1;
            int pageSize = 12; // Số sản phẩm trên 1 trang
            try {
                String pageParam = request.getParameter("page");
                if (pageParam != null) page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) { page = 1; }

            // Xử lý lọc giá
            Double minPrice = parseDouble(request.getParameter("min_price"));
            Double maxPrice = parseDouble(request.getParameter("max_price"));

            // 2. Phân giải Slug thành ID
            // Vì filterProducts cần ID (int), nhưng URL lại là Slug (String)
            Integer categoryId = null;
            Category currentCategory = null;
            if (categorySlug != null && !categorySlug.isEmpty()) {
                currentCategory = categoryDAO.getCategoryBySlug(categorySlug);
                if (currentCategory != null) {
                    categoryId = currentCategory.getId();
                }
            }

            Integer brandId = null;
            Brand currentBrand = null;
            if (brandSlug != null && !brandSlug.isEmpty()) {
                currentBrand = brandDAO.getBrandBySlug(brandSlug);
                if (currentBrand != null) {
                    brandId = currentBrand.getId();
                }
            }

            // 3. Gọi DAO để Lọc và Phân trang (Sử dụng hàm filterProducts MỚI)
            // Lưu ý: Hàm filterProducts của bạn chưa hỗ trợ lọc theo categoryId.
            // Nếu bạn muốn lọc Category, bạn cần cập nhật thêm tham số categoryId vào filterProducts trong DAO.
            // Tạm thời ở đây tôi dùng logic: Nếu có categoryId -> Dùng getProductsByCategoryId (như cũ nhưng có sort)
            // Nếu dùng bộ lọc nâng cao -> Dùng filterProducts.

            // TUY NHIÊN, tốt nhất là bạn nên update filterProducts để nhận cả categoryId.
            // Ở đây tôi giả định bạn sẽ dùng filterProducts cho Brand/Search/Price
            // Còn nếu click Category menu thì dùng hàm getProductsByCategoryId.

            List<Product> productList;
            int totalProducts = 0;

            // Logic chọn hàm DAO phù hợp
            if (categoryId != null) {
                // Trường hợp xem danh mục (chưa hỗ trợ lọc giá/brand kết hợp trong hàm getProductsByCategoryId cũ)
                productList = productDAO.getProductsByCategoryId(categoryId, sortType);
                // (Lưu ý: Bạn nên nâng cấp hàm filterProducts để nhận thêm tham số categoryId sẽ tốt hơn)
                totalProducts = productList.size(); // Tạm thời
            } else {
                // Trường hợp xem Brand, Search, hoặc Tất cả (Có hỗ trợ lọc giá, phân trang)
                productList = productDAO.filterProducts(brandId, minPrice, maxPrice, search, sortType, page, pageSize);
                totalProducts = productDAO.countProducts(brandId, minPrice, maxPrice, search);
            }

            // Tính tổng số trang
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            // 4. Thiết lập Tiêu đề trang
            String pageTitle = "Tất cả sản phẩm";
            if (currentBrand != null) pageTitle = currentBrand.getName();
            else if (currentCategory != null) pageTitle = currentCategory.getName();
            else if (search != null && !search.isEmpty()) pageTitle = "Tìm kiếm: " + search;

            // 5. Build lại QueryString để giữ bộ lọc khi chuyển trang
            StringBuilder qs = new StringBuilder();
            if (categorySlug != null) qs.append("&category=").append(categorySlug);
            if (brandSlug != null) qs.append("&brand=").append(brandSlug);
            if (search != null) qs.append("&q=").append(search);
            if (sortType != null) qs.append("&sort=").append(sortType);
            if (minPrice != null) qs.append("&min_price=").append(minPrice.intValue());
            if (maxPrice != null) qs.append("&max_price=").append(maxPrice.intValue());
            request.setAttribute("queryString", qs.toString());

            // 6. Load Wishlist & Gửi dữ liệu sang View
            ViewHelper.loadWishlistData(request, wishlistDAO);

            request.setAttribute("products", productList);
            request.setAttribute("pageTitle", pageTitle);

            request.setAttribute("currentCategory", currentCategory);
            request.setAttribute("currentBrand", currentBrand);

            // Gửi dữ liệu phân trang & bộ lọc lại cho JSP hiển thị
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("searchKeyword", search);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);
            request.setAttribute("sortBy", sortType);

            request.setAttribute("pageCss", "productList.css");
            request.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, e.getMessage());
        }
    }

    // Helper parse double an toàn
    private Double parseDouble(String s) {
        if (s == null || s.isEmpty()) return null;
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }
}