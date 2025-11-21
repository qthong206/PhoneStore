package com.phonestore.controller;

import com.phonestore.dao.ProductOrderDAO;
import com.phonestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {

    private ProductOrderDAO productOrderDAO;

    @Override
    public void init() {
        productOrderDAO = new ProductOrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        // 2. Lấy dữ liệu thống kê (Để thanh Info Bar phía trên không bị số 0)
        int totalOrders = productOrderDAO.countOrdersByUserId(user.getId());
        double totalSpent = productOrderDAO.sumTotalSpentByUserId(user.getId());

        // 3. (Giai đoạn sau) Gọi AddressDAO lấy danh sách địa chỉ...
        // List<Address> addressList = addressDAO.getAll(user.getId());
        // request.setAttribute("addressList", addressList);

        // 4. Gửi dữ liệu sang JSP
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalSpent", totalSpent);

        // 5. Highlight Menu bên trái
        request.setAttribute("currentView", "account");

        // 6. Chuyển đến file JSP (Lưu ý đường dẫn trong thư mục /user/)
        request.getRequestDispatcher("/WEB-INF/views/user/account.jsp").forward(request, response);
    }
}