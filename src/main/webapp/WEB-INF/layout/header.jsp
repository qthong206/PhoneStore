<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty pageTitle ? pageTitle : "PhoneStore"}</title>

    <%-- CSS Core --%>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">

    <%-- Load account.css để có style cho các nút (btn) trong Modal --%>
    <link rel="stylesheet" href="<c:url value='/css/account.css'/>">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>

    <%-- CSS riêng cho từng trang (nếu có) --%>
    <c:if test="${not empty pageCss}">
        <link rel="stylesheet" href="<c:url value='/css/${pageCss}'/>">
    </c:if>

    <%-- JS Header (Xử lý logic Menu và Modal) --%>
    <script src="<c:url value='/js/header.js'/>" defer></script>
</head>
<body data-context-path="${pageContext.request.contextPath}">
<header>
    <div class="container header-container">

        <%-- === KHỐI BÊN TRÁI: LOGO VÀ DANH MỤC === --%>
        <div class="header-left">
            <div id="branding">
                <a href="<c:url value='/home'/>">
                    <img src="<c:url value='/images/logo.png'/>" alt="PhoneStore Logo" class="header-logo">
                </a>
            </div>

            <div class="category-dropdown">
                <a href="#" class="category-button">
                    <i class="fa-solid fa-bars category-icon"></i>
                    <span>Danh mục</span>
                </a>

                <%-- Menu Danh mục Động (Lấy từ Listener/Database) --%>
                <div class="category-menu-container">
                    <ul class="category-menu-list">
                        <c:forEach var="cat" items="${applicationScope.allCategories}">
                            <li>
                                <a href="<c:url value='/products?category=${cat.slug}'/>">
                                    <span><i class="${cat.iconClass}"></i> ${cat.name}</span>

                                        <%-- Mũi tên chỉ hiện cho mục có menu con --%>
                                    <c:if test="${cat.slug == 'dien-thoai' || cat.slug == 'tablet'}">
                                        <i class="fa-solid fa-chevron-right"></i>
                                    </c:if>
                                </a>

                                    <%-- Mega Menu Thương hiệu (Brand) --%>
                                <c:if test="${cat.slug == 'dien-thoai' || cat.slug == 'tablet'}">
                                    <div class="mega-menu-content">
                                        <h4>Thương hiệu</h4>
                                        <div class="mega-brand-list">
                                            <c:forEach var="brand" items="${applicationScope.allBrands}">
                                                <c:if test="${brand.categoryId == cat.id}">
                                                    <a href="<c:url value='/products?category=${cat.slug}&brand=${brand.slug}'/>" class="mega-brand-item">${brand.name}</a>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>

        <%-- === KHỐI Ở GIỮA: TÌM KIẾM === --%>
        <div class="search-bar">
            <input type="text" placeholder="Bạn cần tìm gì?">
            <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
        </div>

        <%-- === KHỐI BÊN PHẢI: ICON ĐIỀU HƯỚNG === --%>
        <nav class="header-nav">

            <%-- 1. ĐẶT HÀNG: Gọi Hotline --%>
            <a href="tel:18006018" class="nav-item">
                <i class="fa-solid fa-phone-volume nav-icon"></i>
                <span>Đặt hàng</span>
            </a>

            <%-- 2. TRA CỨU ĐƠN: Xử lý logic đăng nhập --%>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <%-- ĐÃ ĐĂNG NHẬP -> Vào trang Lịch sử đơn hàng --%>
                    <a href="<c:url value='/order'/>" class="nav-item">
                        <i class="fa-solid fa-box-archive nav-icon"></i>
                        <span>Tra cứu đơn</span>
                    </a>
                </c:when>
                <c:otherwise>
                    <%-- CHƯA ĐĂNG NHẬP -> Gọi JS bật Modal (Modal nằm ở Footer) --%>
                    <a href="#" onclick="showLoginModal(event)" class="nav-item">
                        <i class="fa-solid fa-box-archive nav-icon"></i>
                        <span>Tra cứu đơn</span>
                    </a>
                </c:otherwise>
            </c:choose>

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
                <a href="${pageContext.request.contextPath}/admin/product" class="nav-item">
                    <c:if test="${sessionScope.user != null && sessionScope.user.admin}">
                        <i class="fa-brands fa-black-tie nav-icon"></i>
                        <span>Admin Panel</span>
                    </c:if>
                </a>


        </nav>
    </div>
</header>