package com.phonestore.controller;

import com.phonestore.dao.AddressDAO;
import com.phonestore.dao.ProductOrderDAO; // Import DAO
import com.phonestore.dao.UserDAO;
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

@WebServlet(name = "AccountServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {

    private ProductOrderDAO productOrderDAO;
    private AddressDAO addressDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        productOrderDAO = new ProductOrderDAO();
        addressDAO = new AddressDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Xử lý các action (Xóa, Set Default) từ URL
        String action = request.getParameter("action");
        try {
            if (action != null) {
                int addressId;
                if ("delete-address".equals(action)) {
                    addressId = Integer.parseInt(request.getParameter("id"));
                    addressDAO.deleteAddress(addressId, user.getId());
                    response.sendRedirect(request.getContextPath() + "/account");
                    return;
                } else if ("set-default".equals(action)) {
                    addressId = Integer.parseInt(request.getParameter("id"));
                    addressDAO.setDefaultAddress(user.getId(), addressId);
                    response.sendRedirect(request.getContextPath() + "/account");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // LOAD DỮ LIỆU HIỂN THỊ
        loadAccountPageData(request, response, user);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("update-info".equals(action)) {
                String fullName = request.getParameter("fullName");
                user.setFullName(fullName);
                // Gọi DAO update User info nếu cần: userDAO.update(user);
                session.setAttribute("user", user); // Cập nhật session
            }
            else if ("add-address".equals(action)) {
                UserAddress addr = new UserAddress();
                addr.setUserId(user.getId());
                populateAddress(addr, request);
                addressDAO.addAddress(addr);
            }
            else if ("update-address".equals(action)) {
                int id = Integer.parseInt(request.getParameter("addressId"));
                UserAddress addr = new UserAddress();
                addr.setId(id);
                addr.setUserId(user.getId());
                populateAddress(addr, request);
                addressDAO.updateAddress(addr);
            }
            response.sendRedirect(request.getContextPath() + "/account");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/account?error=true");
        }
    }

    // --- HÀM LOAD DỮ LIỆU (QUAN TRỌNG) ---
    private void loadAccountPageData(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {

        // 1. Lấy thống kê đơn hàng (Giống OrderServlet) -> Fix lỗi không hiện số
        int totalOrders = productOrderDAO.countOrdersByUserId(user.getId());
        double totalSpent = productOrderDAO.sumTotalSpentByUserId(user.getId());

        // 2. Lấy danh sách địa chỉ
        List<UserAddress> addressList = addressDAO.getAllByUserId(user.getId());

        // 3. Đẩy dữ liệu sang JSP
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalSpent", totalSpent);
        request.setAttribute("addressList", addressList);

        // 4. Biến quan trọng để Sidebar biết đang ở trang nào -> Fix lỗi Sidebar không active
        request.setAttribute("currentView", "account");

        request.getRequestDispatcher("/WEB-INF/views/user/account.jsp").forward(request, response);
    }

    private void populateAddress(UserAddress addr, HttpServletRequest request) {
        addr.setReceiverName(request.getParameter("receiverName"));
        addr.setPhoneNumber(request.getParameter("phoneNumber"));
        addr.setStreetAddress(request.getParameter("streetAddress"));
        addr.setAddressType(request.getParameter("addressType"));
        addr.setDefaultAddress(request.getParameter("isDefault") != null);
    }
}