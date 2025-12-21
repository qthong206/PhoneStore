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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart mainCart = (Cart) session.getAttribute("cart"); // Giỏ hàng chính
        User user = (User) session.getAttribute("user");

        // 1. Kiểm tra giỏ hàng chính rỗng
        if (mainCart == null || mainCart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // --- MỚI: XỬ LÝ LỌC SẢN PHẨM ĐƯỢC CHỌN ---
        String[] selectedIds = request.getParameterValues("selectedItems");

        // Nếu user vào thẳng link /checkout mà không qua form (không có selectedItems)
        // Thì kiểm tra xem session đã có checkoutCart chưa, nếu chưa thì đá về cart
        Cart checkoutCart = null;

        if (selectedIds != null && selectedIds.length > 0) {
            // Trường hợp 1: User nhấn nút "Thanh toán" từ giỏ hàng
            checkoutCart = new Cart(); // Tạo giỏ hàng tạm để thanh toán

            // --- ĐÃ SỬA ĐOẠN NÀY ĐỂ TRÁNH LỖI NẾU getItems() LÀ LIST ---
            for (String idStr : selectedIds) {
                try {
                    int id = Integer.parseInt(idStr);

                    // Tìm item trong giỏ hàng chính thủ công (an toàn nhất)
                    CartItem foundItem = null;
                    for (CartItem item : mainCart.getItems()) {
                        if (item.getProduct().getId() == id) {
                            foundItem = item;
                            break;
                        }
                    }

                    // Nếu tìm thấy thì thêm vào giỏ hàng thanh toán
                    if (foundItem != null) {
                        checkoutCart.addItem(foundItem.getProduct(), foundItem.getQuantity());
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            // -----------------------------------------------------------

            // Lưu checkoutCart vào session để dùng cho doPost và hiển thị
            session.setAttribute("checkoutCart", checkoutCart);
        } else {
            // Trường hợp 2: User F5 hoặc Back lại trang checkout
            checkoutCart = (Cart) session.getAttribute("checkoutCart");
            if (checkoutCart == null || checkoutCart.getItems().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
        }

        // 2. CHỈ Load địa chỉ NẾU là thành viên
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
                if (defaultAddress == null) {
                    defaultAddress = addresses.get(0);
                }
            }

            request.setAttribute("userAddresses", addresses);
            request.setAttribute("defaultAddress", defaultAddress);
        }

        request.setAttribute("pageTitle", "Thanh toán");
        request.setAttribute("pageCss2", "checkout.css");
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // --- MỚI: Lấy checkoutCart thay vì cart chính ---
        Cart checkoutCart = (Cart) session.getAttribute("checkoutCart");
        Cart mainCart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (checkoutCart == null || checkoutCart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        try {
            ProductOrder order = new ProductOrder();
            if (user != null) {
                order.setUserId(user.getId());
            }

            // ... (Phần lấy thông tin địa chỉ giữ nguyên) ...
            String addressOption = request.getParameter("address_option");
            if (user == null) {
                order.setRecipientName(request.getParameter("recipient_name_guest"));
                order.setRecipientPhone(request.getParameter("recipient_phone_guest"));
                order.setRecipientEmail(request.getParameter("recipient_email_guest"));
                order.setShippingAddress(request.getParameter("shipping_address_new"));
            } else {
                if ("new".equals(addressOption)) {
                    order.setRecipientName(request.getParameter("recipient_name_new"));
                    order.setRecipientPhone(request.getParameter("recipient_phone_new"));
                    order.setShippingAddress(request.getParameter("shipping_address_new"));
                } else {
                    order.setRecipientName(request.getParameter("recipient_name_default"));
                    order.setRecipientPhone(request.getParameter("recipient_phone_default"));
                    order.setShippingAddress(request.getParameter("shipping_address_default"));
                }
                order.setRecipientEmail(user.getEmail());
            }

            order.setPaymentMethod(request.getParameter("payment_method"));
            order.setTotalAmount(checkoutCart.getTotal());
            order.setStatus("pending");

            // Tạo đơn hàng với checkoutCart
            int newOrderId = checkoutDAO.createOrder(order, checkoutCart);

            if (newOrderId != -1) {
                // --- ĐÃ SỬA ĐOẠN NÀY ĐỂ HẾT LỖI MÀU ĐỎ ---
                // Duyệt qua từng Item trong giỏ thanh toán để xóa khỏi giỏ chính
                for (CartItem item : checkoutCart.getItems()) {
                    // Lấy ID sản phẩm từ item
                    int productId = item.getProduct().getId();
                    // Xóa khỏi giỏ chính
                    mainCart.removeItem(productId);
                }
                // ------------------------------------------

                session.setAttribute("cart", mainCart); // Cập nhật lại giỏ chính
                session.removeAttribute("checkoutCart"); // Xóa giỏ tạm

                session.setAttribute("latestOrderId", newOrderId);
                response.sendRedirect(request.getContextPath() + "/order-success");
            } else {
                request.setAttribute("checkoutError", "Lỗi tạo đơn hàng.");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("checkoutError", "Hệ thống bận: " + e.getMessage());
            doGet(request, response);
        }
    }
}