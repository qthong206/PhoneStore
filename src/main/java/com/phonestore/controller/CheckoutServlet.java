package com.phonestore.controller;

import com.phonestore.dao.AddressDAO;
import com.phonestore.dao.CheckoutDAO;
import com.phonestore.model.Cart;
import com.phonestore.model.ProductOrder;
import com.phonestore.model.User;
import com.phonestore.model.UserAddress;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    private CheckoutDAO checkoutDAO;
    private AddressDAO addressDAO;

    @Override
    public void init() {
        checkoutDAO = new CheckoutDAO();
        addressDAO = new AddressDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // --- LẤY ĐỊA CHỈ NẾU ĐÃ LOGIN ---
        if (user != null) {
            List<UserAddress> addresses = addressDAO.getAllByUserId(user.getId());
            UserAddress defaultAddress = null;

            // Tìm địa chỉ mặc định
            if (addresses != null && !addresses.isEmpty()) {
                for (UserAddress addr : addresses) {
                    if (addr.isDefaultAddress()) {
                        defaultAddress = addr;
                        break;
                    }
                }
                // Nếu chưa set default, lấy cái đầu tiên
                if (defaultAddress == null) {
                    defaultAddress = addresses.get(0);
                }
            }

            request.setAttribute("userAddresses", addresses);
            request.setAttribute("defaultAddress", defaultAddress);
        }
        // ---------------------------------

        request.setAttribute("pageTitle", "Thanh toán");
        request.setAttribute("pageCss2", "checkout.css");
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
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

            // XỬ LÝ LẤY THÔNG TIN NGƯỜI NHẬN
            if (user == null) {
                // Khách vãng lai
                order.setRecipientName(request.getParameter("recipient_name_guest"));
                order.setRecipientPhone(request.getParameter("recipient_phone_guest"));
                order.setRecipientEmail(request.getParameter("recipient_email_guest"));
                order.setShippingAddress(request.getParameter("shipping_address_new")); // Guest luôn nhập mới
            } else {
                // User đã login
                if ("new".equals(addressOption)) {
                    order.setRecipientName(request.getParameter("recipient_name_new"));
                    order.setRecipientPhone(request.getParameter("recipient_phone_new"));
                    order.setShippingAddress(request.getParameter("shipping_address_new"));
                } else { // "default" - Lấy từ input readonly đã được điền bởi JS hoặc Servlet
                    order.setRecipientName(request.getParameter("recipient_name_default"));
                    order.setRecipientPhone(request.getParameter("recipient_phone_default"));
                    order.setShippingAddress(request.getParameter("shipping_address_default"));
                }
                order.setRecipientEmail(user.getEmail());
            }

            order.setPaymentMethod(request.getParameter("payment_method"));
            order.setTotalAmount(cart.getTotal());
            order.setStatus("pending");

            int newOrderId = checkoutDAO.createOrder(order, cart);

            if (newOrderId != -1) {
                session.removeAttribute("cart");
                // Reset cart trong DB nếu cần (nếu bạn lưu cart item vào DB)
                // session.setAttribute("latestOrderId", newOrderId);
                response.sendRedirect(request.getContextPath() + "/order-success");
            } else {
                request.setAttribute("checkoutError", "Đã có lỗi xảy ra. Vui lòng thử lại.");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("checkoutError", "Hệ thống bận: " + e.getMessage());
            doGet(request, response);
        }
    }
}