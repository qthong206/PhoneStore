<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="<c:url value='/css/footer.css'/>">

<footer>
    <%-- (Toàn bộ code footer-main, footer-nav, footer-copyright của bạn giữ nguyên) --%>
    <div class="footer-main">
        <div class="container footer-grid">
            <div class="footer-column">
                <h4>Về chúng tôi</h4>
                <ul>
                    <li><a href="#">Giới thiệu về công ty</a></li>
                    <li><a href="#">Liên hệ hợp tác kinh doanh</a></li>
                    <li><a href="#">Danh sách cửa hàng</a></li>
                    <li><a href="#">Tuyển dụng mới nhất</a></li>
                    <li><a href="#">Hướng dẫn mua hàng Online</a></li>
                    <li><a href="#">Hướng dẫn mua hàng trả góp</a></li>
                </ul>
            </div>
            <div class="footer-column">
                <h4>Chính sách</h4>
                <ul>
                    <li><a href="#">Chính sách bảo hành</a></li>
                    <li><a href="#">Chính sách bán hàng</a></li>
                    <li><a href="#">Chính sách bảo mật</a></li>
                    <li><a href="#">Chính sách sử dụng</a></li>
                    <li><a href="#">Chính sách khiếu nại</a></li>
                </ul>
            </div>
            <div class="footer-column">
                <h4>Tổng đài hỗ trợ (Miễn phí)</h4>
                <p>Mua ngay: <strong>1800.6018</strong> (07:30 – 21:30)</p>
                <p>Bảo hành tại: <strong>1800.6729</strong> (08:30 – 21:30)</p>
                <p>Góp ý: <strong>1800.6306</strong> (08:30 – 21:30)</p>
            </div>
            <div class="footer-column">
                <h4>Kết nối với PhoneStore</h4>
                <div class="social-icons">
                    <a href="#" class="icon-zalo">Z</a>
                    <a href="#" class="icon-fb">F</a>
                    <a href="#" class="icon-ig">I</a>
                    <a href="#" class="icon-tt">T</a>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-nav">
        <div class="container">
            <a href="#">Điện thoại iPhone</a> |
            <a href="#">Điện thoại Samsung</a> |
            <a href="#">Điện thoại Xiaomi</a> |
            <a href="#">Macbook</a> |
            <a href="#">Tai nghe Airpods</a>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            <p>Công Ty Cổ Phần Công Nghệ PhoneStore Việt. MST: 0311937144. GPĐKKD: 8371937144 do sở KH & ĐT TP.HCM cấp.</p>
            <p>© PhoneStore - All rights reserved. <%= new java.util.Date().getYear() + 1900 %></p>
        </div>
    </div>
</footer>

<%-- ============================================= --%>
<%-- MODAL "CẦN ĐĂNG NHẬP" (ĐÃ DI DỜI TỪ productDetail.jsp SANG ĐÂY) --%>
<%-- ============================================= --%>
<div id="review-login-modal" class="modal-backdrop" style="display:none;">
    <div class="modal-content custom-modal">
        <button class="modal-close" id="modal-close-login-btn">&times;</button>
        <div class="modal-icon"><i class="fa-solid fa-lock"></i></div>
        <h3>Cần Đăng Nhập</h3>
        <%-- Sửa lại text cho chung chung (vì cả Review và Wishlist đều dùng) --%>
        <p>Vui lòng đăng nhập hoặc đăng ký tài khoản để thực hiện chức năng này.</p>
        <div class="modal-buttons">
            <a href="<c:url value='/register'/>" class="btn btn-modal-secondary">Đăng Ký</a>
            <a href="<c:url value='/login'/>" class="btn btn-modal-primary">Đăng Nhập</a>
        </div>
    </div>
</div>

</body>
</html>