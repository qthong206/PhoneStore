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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>
    <c:if test="${not empty pageCss}">
        <link rel="stylesheet" href="<c:url value='/css/${pageCss}'/>">
    </c:if>

    <script src="<c:url value='/js/header.js'/>" defer></script>
</head>
<body data-context-path="${pageContext.request.contextPath}">
<header>
    <div class="container header-container">
        <%-- (Logo giữ nguyên) --%>
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
                        <%-- Lặp qua danh sách 'allCategories' mà Listener đã tải --%>
                        <c:forEach var="cat" items="${applicationScope.allCategories}">
                            <li>
                                <a href="<c:url value='/products?category=${cat.slug}'/>">
                                    <span><i class="${cat.iconClass}"></i> ${cat.name}</span>

                                        <%-- Chỉ hiển thị mũi tên (>) nếu là 2 mục có mega-menu --%>
                                    <c:if test="${cat.slug == 'dien-thoai' || cat.slug == 'tablet'}">
                                        <i class="fa-solid fa-chevron-right"></i>
                                    </c:if>
                                </a>

                                    <%--
                                        ==========================================
                                        MEGA MENU ĐỘNG (HIỆN TÊN)
                                        ==========================================
                                    --%>
                                <c:if test="${cat.slug == 'dien-thoai' || cat.slug == 'tablet'}">
                                    <div class="mega-menu-content">
                                        <h4>Thương hiệu</h4>

                                        <div class="mega-brand-list">
                                            <c:forEach var="brand" items="${applicationScope.allBrands}">
                                                <c:if test="${brand.categoryId == cat.id}">
                                                    <%-- (Link sẽ sửa sau, tạm thời #) --%>
                                                    <a href="#" class="mega-brand-item">${brand.name}</a>
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

        <%-- (Thanh tìm kiếm và Nav bên phải giữ nguyên) --%>
        <div class="search-bar">
            <input type="text" placeholder="Bạn cần tìm gì?">
            <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
        </div>
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