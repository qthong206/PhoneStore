package com.phonestore.controller;

import com.phonestore.dao.CheckoutDAO;
import com.phonestore.model.Cart;
import com.phonestore.model.ProductOrder;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    private CheckoutDAO checkoutDAO;

    @Override
    public void init() {
        checkoutDAO = new CheckoutDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        request.setAttribute("pageTitle", "Thanh toán");
        request.setAttribute("pageCss", "cart.css");
        request.setAttribute("pageCss2", "checkout.css");
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        try {
            ProductOrder order = new ProductOrder();

            if (user != null) {
                order.setUserId(user.getId());
            }

            String addressOption = request.getParameter("address_option");

            if ("new".equals(addressOption)) {
                if (user != null) {
                    order.setRecipientName(request.getParameter("recipient_name_new"));
                    order.setRecipientPhone(request.getParameter("recipient_phone_new"));
                    order.setRecipientEmail(user.getEmail());
                } else {
                    order.setRecipientName(request.getParameter("recipient_name_guest"));
                    order.setRecipientPhone(request.getParameter("recipient_phone_guest"));
                    order.setRecipientEmail(request.getParameter("recipient_email_guest"));
                }
                order.setShippingAddress(request.getParameter("shipping_address_new"));

            } else { // "default"
                order.setRecipientName(request.getParameter("recipient_name_default"));
                order.setRecipientPhone(request.getParameter("recipient_phone_default"));
                order.setShippingAddress(request.getParameter("shipping_address_default"));
                order.setRecipientEmail(user.getEmail());
            }

            // LẤY PHƯƠNG THỨC THANH TOÁN
            String paymentMethod = request.getParameter("payment_method");
            order.setPaymentMethod(paymentMethod);

            order.setTotalAmount(cart.getTotal());

            int newOrderId = checkoutDAO.createOrder(order, cart);

            if (newOrderId != -1) {
                session.removeAttribute("cart");

                session.setAttribute("latestOrderId", newOrderId);

                response.sendRedirect(request.getContextPath() + "/order-success");
            } else {
                request.setAttribute("checkoutError", "Đã có lỗi xảy ra. Vui lòng thử lại.");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("checkoutError", "Hệ thống bận, xin vui lòng thử lại sau.");
            doGet(request, response);
        }
    }
}