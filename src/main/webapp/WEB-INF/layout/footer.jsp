<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="<c:url value='/css/footer.css'/>">

<footer>
    <div class="footer-main">
        <div class="container footer-grid">
            <div class="footer-column">
                <h4>Về chúng tôi</h4>
                <ul>
                    <%-- Cập nhật link về trang info-center --%>
                    <li><a href="<c:url value='/about?tab=gioi-thieu'/>">Giới thiệu về công ty</a></li>
                    <li><a href="<c:url value='/about?tab=lien-he'/>">Liên hệ hợp tác kinh doanh</a></li>
                    <li><a href="<c:url value='/about?tab=cua-hang'/>">Danh sách cửa hàng</a></li>
                    <li><a href="<c:url value='/about?tab=tuyen-dung'/>">Tuyển dụng mới nhất</a></li>
                    <li><a href="<c:url value='/about?tab=huong-dan'/>">Hướng dẫn mua hàng Online</a></li>
                    <li><a href="<c:url value='/about?tab=tra-gop'/>">Hướng dẫn mua hàng trả góp</a></li>
                </ul>
            </div>
            <div class="footer-column">
                <h4>Chính sách</h4>
                <ul>
                    <%-- Cập nhật link về trang info-center --%>
                    <li><a href="<c:url value='/about?tab=bao-hanh'/>">Chính sách bảo hành</a></li>
                    <li><a href="<c:url value='/about?tab=ban-hang'/>">Chính sách bán hàng</a></li>
                    <li><a href="<c:url value='/about?tab=bao-mat'/>">Chính sách bảo mật</a></li>
                    <li><a href="<c:url value='/about?tab=su-dung'/>">Chính sách sử dụng</a></li>
                    <li><a href="<c:url value='/about?tab=khieu-nai'/>">Chính sách khiếu nại</a></li>
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
                    <a href="https://zalo.me" target="_blank" class="icon-zalo">Z</a>
                    <a href="https://facebook.com" target="_blank" class="icon-fb">F</a>
                    <a href="https://instagram.com" target="_blank" class="icon-ig">I</a>
                    <a href="https://tiktok.com" target="_blank" class="icon-tt">T</a>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-nav">
        <div class="container">
            <%-- Có thể trỏ về trang danh sách sản phẩm kèm filter nếu bạn đã có --%>
            <a href="<c:url value='/products?category=iphone'/>">Điện thoại iPhone</a> |
            <a href="<c:url value='/products?category=samsung'/>">Điện thoại Samsung</a> |
            <a href="<c:url value='/products?category=xiaomi'/>">Điện thoại Xiaomi</a> |
            <a href="<c:url value='/products?category=macbook'/>">Macbook</a> |
            <a href="<c:url value='/products?category=airpods'/>">Tai nghe Airpods</a>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            <p>Đồ án môn học Lập Trình Web - Học kỳ I năm 2025-2026</p>

            <p>Thực hiện bởi:
                <strong>Trịnh Quang Thông (22130275)</strong> &
                <strong>Nguyễn Long Vũ (22130329)</strong>
            </p>
            <p>© PhoneStore Project - All rights reserved. <%= new java.util.Date().getYear() + 1900 %></p>
        </div>
    </div>
</footer>

<%-- Modal và Script giữ nguyên --%>
<div id="review-login-modal" class="modal-backdrop" style="display:none;">
    <div class="modal-content custom-modal">
        <button class="modal-close" id="modal-close-login-btn">&times;</button>
        <div class="modal-icon"><i class="fa-solid fa-lock"></i></div>
        <h3>Cần Đăng Nhập</h3>
        <p>Vui lòng đăng nhập hoặc đăng ký tài khoản để thực hiện chức năng này.</p>
        <div class="modal-buttons">
            <a href="<c:url value='/register'/>" class="btn btn-gradient-secondary">Đăng Ký</a>
            <a href="<c:url value='/login'/>" class="btn btn-gradient-primary">Đăng Nhập</a>
        </div>
    </div>
</div>

<script src="<c:url value='/js/common.js'/>"></script>