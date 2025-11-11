<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty pageTitle ? pageTitle : "PhoneStore"}</title>

    <%-- CSS TOÀN CỤC (GLOBAL) --%>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>

    <%-- CSS CỤC BỘ (CHỈ TẢI KHI TRANG CON YÊU CẦU) --%>
    <c:if test="${not empty pageCss}">
        <link rel="stylesheet" href="<c:url value='/css/${pageCss}'/>">
    </c:if>

    <%-- JAVASCRIPT --%>
    <script src="<c:url value='/js/header.js'/>" defer></script>
</head>
<body>

<header>
    <div class="container header-container">
        <%-- KHỐI BÊN TRÁI: LOGO VÀ DANH MỤC --%>
        <div class="header-left">
            <div id="branding">
                <a href="<c:url value='/home'/>">
                    <img src="<c:url value='/images/logo.png'/>" alt="PhoneStore Logo" class="header-logo">
                </a>
            </div>

            <%-- NÚT DANH MỤC GIỜ SẼ LÀ DROPDOWN CONTAINER --%>
            <div class="category-dropdown">
                <%-- Nút bấm --%>
                <a href="#" class="category-button">
                    <i class="fa-solid fa-bars category-icon"></i>
                    <span>Danh mục</span>
                </a>

                <%-- Nội dung dropdown (mặc định bị ẩn) --%>
                <div class="category-menu-container">

                    <%-- ========================================== --%>
                    <%-- DANH SÁCH DANH MỤC ĐÃ ĐƯỢC CẬP NHẬT       --%>
                    <%-- ========================================== --%>
                    <ul class="category-menu-list">

                        <%-- MỤC 1: ĐIỆN THOẠI (Có menu con) --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-solid fa-mobile-screen-button"></i> Điện thoại</span>
                                <i class="fa-solid fa-chevron-right"></i>
                            </a>
                            <%-- Menu con (mega menu) --%>
                            <div class="mega-menu-content">
                                <h4>iPhone Mới Nhất</h4>
                                <div class="mega-product-grid">
                                    <a href="#" class="mega-product-card">
                                        <img src="https://placehold.co/150x150/EAF8FF/00587C?text=iPhone+17" alt="iPhone 17">
                                        <span>iPhone 17 Pro Max</span>
                                    </a>
                                    <a href="#" class="mega-product-card">
                                        <img src="https://placehold.co/150x150/EAF8FF/00587C?text=iPhone+16" alt="iPhone 16">
                                        <span>iPhone 16 Plus</span>
                                    </a>
                                </div>
                            </div>
                        </li>

                        <%-- MỤC 2: TABLET (Có menu con) --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-solid fa-tablet-screen-button"></i> Tablet</span>
                                <i class="fa-solid fa-chevron-right"></i>
                            </a>
                            <%-- Menu con (mega menu) --%>
                            <div class="mega-menu-content">
                                <h4>iPad Bán Chạy</h4>
                                <div class="mega-product-grid">
                                    <a href="#" class="mega-product-card">
                                        <img src="https://placehold.co/150x150/EAF8FF/00587C?text=iPad+Pro" alt="iPad Pro">
                                        <span>iPad Pro 13-inch (M5)</span>
                                    </a>
                                </div>
                            </div>
                        </li>

                        <%-- MỤC 3: MAC (Không có menu con) --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-solid fa-laptop"></i> Mac</span>
                            </a>
                        </li>

                        <%-- MỤC 4: MÁY CŨ GIÁ RẺ --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-solid fa-mobile-screen"></i> Máy cũ giá rẻ</span>
                                <i class="fa-solid fa-chevron-right"></i>
                            </a>
                        </li>

                        <%-- MỤC 5: PHỤ KIỆN --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-solid fa-plug"></i> Phụ kiện</span>
                                <i class="fa-solid fa-chevron-right"></i>
                            </a>
                        </li>

                        <%-- MỤC 6: ĐỒNG HỒ --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-solid fa-clock"></i> Đồng hồ</span>
                                <i class="fa-solid fa-chevron-right"></i>
                            </a>
                        </li>

                        <%-- MỤC 7: ÂM THANH --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-solid fa-headphones"></i> Âm thanh</span>
                            </a>
                        </li>

                        <%-- MỤC 8: ĐIỆN MÁY, GIA DỤNG --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-solid fa-blender"></i> Điện máy, Gia dụng</span>
                                <i class="fa-solid fa-chevron-right"></i>
                            </a>
                        </li>

                        <%-- MỤC 9: APPLE (AAR) --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-brands fa-apple"></i> Apple (AAR)</span>
                                <i class="fa-solid fa-chevron-right"></i>
                            </a>
                        </li>

                        <%-- MỤC 11: MÀN HÌNH, TIVI --%>
                        <li>
                            <a href="#">
                                <span><i class="fa-solid fa-tv"></i> Màn hình, Tivi</span>
                            </a>
                        </li>

                    </ul>
                </div>
            </div>
        </div>

        <%-- KHỐI Ở GIỮA: THANH TÌM KIẾM --%>
        <div class="search-bar">
            <input type="text" placeholder="Bạn cần tìm gì?">
            <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
        </div>

        <%-- KHỐI BÊN PHẢI: CÁC MỤC ĐIỀU HƯỚNG --%>
        <nav class="header-nav">
            <a href="#" class="nav-item">
                <i class="fa-solid fa-phone-volume nav-icon"></i>
                <span>Đặt hàng</span>
            </a>
            <a href="#" class="nav-item">
                <i class="fa-solid fa-box-archive nav-icon"></i>
                <span>Tra cứu đơn</span>
            </a>
            <a href="#" class="nav-item">
                <i class="fa-solid fa-gift nav-icon"></i>
                <span>Khuyến mãi</span>
            </a>
            <a href="<c:url value='/cart'/>" class="nav-item">
                <i class="fa-solid fa-cart-shopping nav-icon"></i>
                <span>Giỏ Hàng</span>
            </a>
            <a href="<c:url value='/user'/>" class="nav-item">
                <i class="fa-solid fa-user nav-icon"></i>
                <span>Tài Khoản</span>
            </a>
        </nav>
    </div>
</header>