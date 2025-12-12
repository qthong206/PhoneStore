package com.phonestore.controller;

import com.phonestore.dao.AddressDAO;
import com.phonestore.dao.ProductOrderDAO;
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

    // --- GET: HIỂN THỊ & HÀNH ĐỘNG LINK ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

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

        loadAccountPageData(request, response, user);
    }

    // --- POST: XỬ LÝ FORM ---
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
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
                // Gọi DAO update
                userDAO.updateUserInfo(user);
                session.setAttribute("user", user);
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
            // --- XỬ LÝ ĐỔI MẬT KHẨU ---
            else if ("update-password".equals(action)) {
                String oldPass = request.getParameter("oldPassword");
                String newPass = request.getParameter("newPassword");
                String confirmPass = request.getParameter("confirmPassword");

                if (!newPass.equals(confirmPass)) {
                    response.sendRedirect(request.getContextPath() + "/account?error=pass_mismatch");
                    return;
                }

                // Gọi hàm changePassword đã thêm vào UserDAO trước đó
                boolean success = userDAO.changePassword(user.getId(), oldPass, newPass);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/account?msg=pass_updated");
                } else {
                    response.sendRedirect(request.getContextPath() + "/account?error=wrong_old_pass");
                }
                return; // Kết thúc để tránh redirect mặc định bên dưới
            }

            response.sendRedirect(request.getContextPath() + "/account");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/account?error=true");
        }
    }

    private void loadAccountPageData(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        int totalOrders = productOrderDAO.countOrdersByUserId(user.getId());
        double totalSpent = productOrderDAO.sumTotalSpentByUserId(user.getId());
        List<UserAddress> addressList = addressDAO.getAllByUserId(user.getId());

        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalSpent", totalSpent);
        request.setAttribute("addressList", addressList);
        request.setAttribute("currentView", "account");

        // Gửi thời gian hiện tại để hiển thị "Cập nhật lần cuối" (giả lập)
        request.setAttribute("now", new java.util.Date());

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