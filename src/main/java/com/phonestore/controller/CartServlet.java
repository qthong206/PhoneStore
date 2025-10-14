package com.phonestore.controller;

import com.phonestore.model.Cart;
import com.phonestore.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    // --- "Cơ sở dữ liệu" giả lập để tìm sản phẩm ---
    private Product findProductById(int id) {
        // Trong dự án thật, bạn sẽ gọi ProductDAO.getProductById(id)
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("remove")) {
            removeItemFromCart(request);
            response.sendRedirect("cart"); // Tải lại trang giỏ hàng
            return;
        }

        // Mặc định là hiển thị trang giỏ hàng
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equals("add")) {
            addItemToCart(request);
        }

        // Sau khi thêm, chuyển hướng đến trang giỏ hàng để xem kết quả
        response.sendRedirect("cart");
    }

    private void addItemToCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // Nếu giỏ hàng chưa tồn tại trong session, tạo mới
        if (cart == null) {
            cart = new Cart();
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product product = findProductById(productId);
            if (product != null) {
                cart.addItem(product, quantity);
            }
        } catch (NumberFormatException e) {
            // Xử lý lỗi nếu id hoặc quantity không phải là số
            e.printStackTrace();
        }

        // Cập nhật lại giỏ hàng trong session
        session.setAttribute("cart", cart);
    }

    private void removeItemFromCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            try {
                int productId = Integer.parseInt(request.getParameter("id"));
                cart.removeItem(productId);
                session.setAttribute("cart", cart);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}