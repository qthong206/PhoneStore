package com.phonestore.controller;

import com.phonestore.dao.ProductDAO;
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

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    private Product findProductById(int id) {
        try {
            return productDAO.getProductById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Xử lý Xóa
        if ("remove".equals(action)) {
            removeItemFromCart(request);
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Xử lý Cập nhật (Tăng/Giảm số lượng)
        if ("update".equals(action)) {
            updateItemQuantity(request);
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addItemToCart(request);
        }
        // Sau khi thêm, chuyển hướng về trang giỏ hàng hoặc trang hiện tại
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private void addItemToCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) cart = new Cart();

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            // Mặc định là 1 nếu không có tham số quantity
            String qtyParam = request.getParameter("quantity");
            int quantity = (qtyParam != null && !qtyParam.isEmpty()) ? Integer.parseInt(qtyParam) : 1;

            Product product = findProductById(productId);
            if (product != null) {
                cart.addItem(product, quantity);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
            } catch (NumberFormatException e) { e.printStackTrace(); }
        }
    }

    // --- HÀM MỚI: CẬP NHẬT SỐ LƯỢNG ---
    private void updateItemQuantity(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            try {
                int productId = Integer.parseInt(request.getParameter("id"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                // Nếu số lượng <= 0 thì xóa, ngược lại thì cập nhật
                if (quantity <= 0) {
                    cart.removeItem(productId);
                } else {
                    // Bạn cần đảm bảo class Cart có hàm update (ví dụ: cart.update(id, qty))
                    // Nếu class Cart của bạn dùng addItem để cộng dồn, bạn cần viết thêm hàm setQuantity
                    // Dưới đây là ví dụ giả định bạn có hàm updateItem(productId, quantity)
                    cart.updateItem(productId, quantity);
                }
                session.setAttribute("cart", cart);
            } catch (NumberFormatException e) { e.printStackTrace(); }
        }
    }
}