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
                <a href="<c:url value='/products'/>" class="category-button">
                    <i class="fa-solid fa-bars category-icon"></i>
                    <span>Danh mục</span>
                </a>

                <div class="category-menu-container">

                    <%-- [ĐÃ XÓA]: Tiêu đề "Loại sản phẩm" ở đây theo yêu cầu --%>

                    <ul class="category-menu-list">
                        <c:forEach var="cat" items="${applicationScope.allCategories}">
                            <li>
                                <a href="<c:url value='/products?category=${cat.slug}'/>">
                                    <span><i class="${cat.iconClass}"></i> ${cat.name}</span>

                                        <%-- Mũi tên nếu có menu con --%>
                                    <c:if test="${not empty applicationScope.brandsByCategory[cat.id]}">
                                        <i class="fa-solid fa-chevron-right arrow-icon"></i>
                                    </c:if>
                                </a>

                                    <%-- MEGA MENU (Hiện khi hover vào Category) --%>
                                <c:if test="${not empty applicationScope.brandsByCategory[cat.id]}">
                                    <div class="mega-menu-content">
                                        <h4>Hãng ${cat.name}</h4>
                                        <div class="mega-brand-list">
                                            <c:forEach var="brand" items="${applicationScope.brandsByCategory[cat.id]}">
                                                <a href="<c:url value='/products?category=${cat.slug}&brand=${brand.slug}'/>" class="mega-brand-item">
                                                        ${brand.name}
                                                </a>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>
                            </li>
                        </c:forEach>

                        <%-- [MỚI] Tiêu đề "Chuyên trang thương hiệu" giống ảnh mẫu --%>
                        <li>
                            <div class="brand-section-header">
                                Chuyên trang thương hiệu
                            </div>

                            <div class="bottom-brand-grid">
                                <c:forEach var="brand" items="${applicationScope.allBrands}">
                                    <a href="<c:url value='/products?brand=${brand.slug}'/>" class="bottom-brand-item">
                                            <%-- Logic: Nếu có Logo thì hiện Logo, không thì hiện Tên --%>
                                        <c:choose>
                                            <c:when test="${not empty brand.logoUrl}">
                                                <img src="<c:url value='/${brand.logoUrl}'/>" alt="${brand.name}" class="brand-logo-img">
                                                <span class="brand-name-text">${brand.name}</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${brand.name}
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                </c:forEach>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <%-- Form tìm kiếm --%>
        <div class="search-bar">
            <form action="<c:url value='/products'/>" method="GET" class="search-form" autocomplete="off" style="width: 100%; display: flex;">
                <input type="text" id="searchInput" name="q" placeholder="Bạn cần tìm gì?" required>
                <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
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