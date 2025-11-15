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

    /**
     * HÀM NÀY ĐÃ ĐƯỢC SỬA
     * Nó sẽ gọi ProductDAO để lấy sản phẩm thật từ DB.
     */
    private Product findProductById(int id) {
        try {
            // 4. GỌI DAO THẬT
            return productDAO.getProductById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null nếu DAO có lỗi
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("remove".equals(action)) {
            removeItemFromCart(request);
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
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private void addItemToCart(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // Lấy giỏ hàng từ session, hoặc tạo mới nếu chưa có
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product product = findProductById(productId);

            if (product != null) {
                cart.addItem(product, quantity);
            } else {
                // Ghi log lỗi nếu không tìm thấy sản phẩm
                System.err.println("CartServlet: Không tìm thấy Product với ID=" + productId);
            }
        } catch (NumberFormatException e) {
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
                // file cart.jsp của bạn dùng ?id=... nên đây là 'id'
                int productId = Integer.parseInt(request.getParameter("id"));
                cart.removeItem(productId);
                session.setAttribute("cart", cart);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}