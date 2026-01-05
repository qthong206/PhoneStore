<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty pageTitle ? pageTitle : "PhoneStore"}</title>

    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/account.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>

    <c:if test="${not empty pageCss}">
        <link rel="stylesheet" href="<c:url value='/css/${pageCss}'/>">
    </c:if>

    <script src="<c:url value='/js/header.js'/>" defer></script>
</head>
<body data-context-path="${pageContext.request.contextPath}">
<header>
    <div class="container header-container">

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

                <div class="category-menu-container">
                    <ul class="category-menu-list">
                        <c:forEach var="cat" items="${applicationScope.allCategories}">
                            <li>
                                <a href="<c:url value='/products?category=${cat.slug}'/>">
                                    <span><i class="${cat.iconClass}"></i> ${cat.name}</span>

                                    <c:if test="${cat.slug == 'dien-thoai' || cat.slug == 'tablet'}">
                                        <i class="fa-solid fa-chevron-right"></i>
                                    </c:if>
                                </a>

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

        <%-- [QUAN TRỌNG] Cập nhật Form tìm kiếm --%>
        <div class="search-bar">
            <form action="<c:url value='/products'/>" method="GET" class="search-form" autocomplete="off" style="width: 100%; display: flex;">
                <input type="text" id="searchInput" name="search" placeholder="Bạn cần tìm gì?" required>
                <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>

                <%-- Khối hiển thị gợi ý (Mới) --%>
                <div id="search-suggestions-box" class="search-suggestions-box" style="display: none;"></div>
            </form>
        </div>

        <nav class="header-nav">
            <a href="tel:18006018" class="nav-item">
                <i class="fa-solid fa-phone-volume nav-icon"></i>
                <span>Đặt hàng</span>
            </a>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="<c:url value='/order'/>" class="nav-item">
                        <i class="fa-solid fa-box-archive nav-icon"></i>
                        <span>Tra cứu đơn</span>
                    </a>
                </c:when>
                <c:otherwise>
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