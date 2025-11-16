package com.phonestore.controller;

import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.WishlistDAO; // 1. IMPORT WISHLIST DAO
import com.phonestore.model.Brand;
import com.phonestore.model.Product;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // 2. IMPORT SESSION

import java.io.IOException;
import java.util.HashSet; // 3. IMPORT HASHSET
import java.util.List;
import java.util.Map;
import java.util.Set; // 4. IMPORT SET

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", ""})
public class HomeServlet extends HttpServlet {

    private ProductDAO productDAO;
    private WishlistDAO wishlistDAO; // 5. KHAI BÁO WISHLIST DAO

    @Override
    public void init() {
        productDAO = new ProductDAO();
        wishlistDAO = new WishlistDAO(); // 6. KHỞI TẠO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 7. LẤY SẢN PHẨM (Code cũ của bạn)
            Map<Brand, List<Product>> productMap = productDAO.getProductsGroupedByBrand();
            request.setAttribute("productMap", productMap);

            // 8. LẤY WISHLIST (LOGIC MỚI BỊ THIẾU)
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            // Khởi tạo một Set rỗng
            Set<Integer> wishlistIds = new HashSet<>();

            if (user != null) {
                // Nếu user đã đăng nhập, lấy danh sách ID yêu thích của họ
                wishlistIds = wishlistDAO.getWishlistProductIds(user.getId());
            }

            // Gửi danh sách ID này sang JSP (KỂ CẢ KHI NÓ RỖNG)
            request.setAttribute("wishlistIds", wishlistIds);

            // 9. FORWARD (Code cũ của bạn)
            // (Thêm 2 dòng setAttribute để đảm bảo layout không lỗi)
            request.setAttribute("pageTitle", "Trang chủ");
            request.setAttribute("pageCss", "home.css");
            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}