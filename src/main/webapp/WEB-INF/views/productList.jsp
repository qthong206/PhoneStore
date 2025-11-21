<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<main class="container">

    <%-- =================================== --%>
    <%-- BREADCRUMBS MỚI (THAY THẾ H1) --%>
    <%-- =================================== --%>
    <nav class="breadcrumb-nav">
        <a href="<c:url value='/home'/>">Trang chủ</a>
        <i class="fa-solid fa-chevron-right"></i>

        <c:if test="${not empty currentCategory}">
            <%--
               Trường hợp 1: Chỉ lọc Category (brand=null)
               Link "Điện thoại" không click được (vì đang ở trang đó).
            --%>
            <c:if test="${empty currentBrand}">
                <span>${currentCategory.name}</span>
            </c:if>

            <%--
               Trường hợp 2: Lọc cả Brand (brand!=null)
               Link "Điện thoại" CÓ click được, trỏ về trang category cha.
            --%>
            <c:if test="${not empty currentBrand}">
                <a href="<c:url value='/products?category=${currentCategory.slug}'/>">${currentCategory.name}</a>
                <i class="fa-solid fa-chevron-right"></i>
                <span>${currentBrand.name}</span>
            </c:if>
        </c:if>
    </nav>

    <%-- (Khối .product-list-grid giữ nguyên) --%>
    <div class="product-list-grid">
        <c:forEach var="p" items="${products}">
            <div class="product-card">
                <a href="<c:url value='/product-detail?id=${p.id}'/>" class="product-link">
                    <div class="product-tags">
                        <c:if test="${p.salePrice > 0}">
                            <c:set var="discountPercent" value="${(p.price - p.salePrice) / p.price}" />
                            <span class="tag tag-discount">
                                Giảm <fmt:formatNumber value="${discountPercent}" type="percent" maxFractionDigits="0" />
                            </span>
                        </c:if>
                    </div>
                    <img src="<c:url value='/${p.thumbnailUrl}'/>" alt="${p.name}">
                    <h3>${p.name}</h3>
                    <div class="price-container">
                        <c:choose>
                            <c:when test="${p.salePrice > 0}">
                                <p class="sale-price"><fmt:formatNumber value="${p.salePrice}" type="number" pattern="#,##0"/> ₫</p>
                                <p class="original-price"><fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> ₫</p>
                            </c:when>
                            <c:otherwise>
                                <p class="sale-price"><fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> D ₫</p>
                                <p class="original-price">&nbsp;</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </a>

                <div class="rating-wishlist-box">
                    <div class="rating">
                        <c:if test="${p.reviewCount > 0}">
                            <i class="fa-solid fa-star"></i>
                            <span><fmt:formatNumber value="${p.avgRating}" maxFractionDigits="1" /></span>
                        </c:if>
                    </div>
                    <button class="btn-wishlist ${wishlistIds.contains(p.id) ? 'active' : ''}"
                            data-product-id="${p.id}">
                        <i class="icon-heart-empty fa-regular fa-heart"></i>
                        <i class="icon-heart-filled fa-solid fa-heart"></i>
                        <span>Yêu thích</span>
                    </button>
                </div>
            </div>
        </c:forEach>
    </div>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />