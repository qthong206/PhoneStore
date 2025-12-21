package com.phonestore.controller;

import com.phonestore.dao.AddressDAO;
import com.phonestore.dao.CheckoutDAO;
import com.phonestore.model.Cart;
import com.phonestore.model.CartItem;
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

    /**
     * doGet: CHỈ DÙNG ĐỂ HIỂN THỊ GIAO DIỆN THANH TOÁN
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart checkoutCart = (Cart) session.getAttribute("checkoutCart");

        // Nếu chưa có giỏ hàng thanh toán (User truy cập trực tiếp URL), đá về giỏ hàng
        if (checkoutCart == null || checkoutCart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Load địa chỉ nếu là thành viên
        if (user != null) {
            List<UserAddress> addresses = addressDAO.getAllByUserId(user.getId());
            UserAddress defaultAddress = null;
            if (addresses != null && !addresses.isEmpty()) {
                for (UserAddress addr : addresses) {
                    if (addr.isDefaultAddress()) {
                        defaultAddress = addr;
                        break;
                    }
                }
                if (defaultAddress == null) defaultAddress = addresses.get(0);
            }
            request.setAttribute("userAddresses", addresses);
            request.setAttribute("defaultAddress", defaultAddress);
        }

        request.setAttribute("pageTitle", "Thanh toán");
        request.setAttribute("pageCss2", "checkout.css");
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    /**
     * doPost: XỬ LÝ 2 TRƯỜNG HỢP (SETUP GIỎ HÀNG & ĐẶT HÀNG)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Cart mainCart = (Cart) session.getAttribute("cart");

        // ==================================================================
        // TRƯỜNG HỢP 1: SETUP (Nhận request từ Cart.jsp chuyển sang)
        // ==================================================================
        String[] selectedIds = request.getParameterValues("selectedItems");

        if (selectedIds != null) {
            if (mainCart == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            Cart checkoutCart = new Cart();
            for (String idStr : selectedIds) {
                try {
                    int id = Integer.parseInt(idStr);
                    // Tìm item trong giỏ chính và copy sang giỏ thanh toán
                    for (CartItem item : mainCart.getItems()) {
                        if (item.getProduct().getId() == id) {
                            checkoutCart.addItem(item.getProduct(), item.getQuantity());
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            // Lưu giỏ tạm vào session và chuyển hướng sang doGet để hiện giao diện
            session.setAttribute("checkoutCart", checkoutCart);
            response.sendRedirect(request.getContextPath() + "/checkout");
            return;
        }

        // ==================================================================
        // TRƯỜNG HỢP 2: PLACE ORDER (Nhận request từ Checkout.jsp để đặt hàng)
        // ==================================================================
        Cart checkoutCart = (Cart) session.getAttribute("checkoutCart");
        User user = (User) session.getAttribute("user");

        if (checkoutCart == null || checkoutCart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        try {
            ProductOrder order = new ProductOrder();
            if (user != null) order.setUserId(user.getId());

            // Lấy thông tin người nhận
            String addressOption = request.getParameter("address_option");
            if (user == null || "new".equals(addressOption)) {
                // Khách vãng lai HOẶC Thành viên chọn địa chỉ mới
                // (Lưu ý: name-guest, name-new cần khớp với checkout.jsp)
                if (user == null) {
                    order.setRecipientName(request.getParameter("recipient_name_guest"));
                    order.setRecipientPhone(request.getParameter("recipient_phone_guest"));
                    order.setRecipientEmail(request.getParameter("recipient_email_guest"));
                } else {
                    order.setRecipientName(request.getParameter("recipient_name_new"));
                    order.setRecipientPhone(request.getParameter("recipient_phone_new"));
                    order.setRecipientEmail(user.getEmail());
                }
                order.setShippingAddress(request.getParameter("shipping_address_new"));
            } else {
                // Thành viên chọn địa chỉ có sẵn
                order.setRecipientName(request.getParameter("recipient_name_default"));
                order.setRecipientPhone(request.getParameter("recipient_phone_default"));
                order.setRecipientEmail(user.getEmail());
                order.setShippingAddress(request.getParameter("shipping_address_default"));
            }

            order.setPaymentMethod(request.getParameter("payment_method"));
            order.setTotalAmount(checkoutCart.getTotal());
            order.setStatus("pending");

            // Gọi DAO tạo đơn hàng
            int newOrderId = checkoutDAO.createOrder(order, checkoutCart);

            if (newOrderId != -1) {
                // --- QUAN TRỌNG: Xóa sản phẩm đã mua khỏi giỏ hàng chính ---
                if (mainCart != null) {
                    for (CartItem item : checkoutCart.getItems()) {
                        mainCart.removeItem(item.getProduct().getId());
                    }
                    session.setAttribute("cart", mainCart); // Cập nhật lại session
                }

                // Xóa giỏ tạm
                session.removeAttribute("checkoutCart");

                // Chuyển sang trang thành công
                session.setAttribute("latestOrderId", newOrderId);
                response.sendRedirect(request.getContextPath() + "/order-success");
            } else {
                request.setAttribute("checkoutError", "Có lỗi xảy ra khi tạo đơn hàng. Vui lòng thử lại.");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("checkoutError", "Lỗi hệ thống: " + e.getMessage());
            doGet(request, response);
        }
    }
}