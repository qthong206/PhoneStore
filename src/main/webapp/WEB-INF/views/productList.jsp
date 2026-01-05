<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="vi_VN"/>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<main class="container">

    <%-- 1. BREADCRUMBS (ĐÃ SỬA LOGIC HIỂN THỊ) --%>
    <nav class="breadcrumb-nav">
        <a href="<c:url value='/home'/>">Trang chủ</a>
        <i class="fa-solid fa-chevron-right"></i>

        <c:choose>
            <%-- TRƯỜNG HỢP 1: Có Category (VD: Điện thoại, hoặc Điện thoại > Apple) --%>
            <c:when test="${not empty currentCategory}">
                <c:choose>
                    <c:when test="${empty currentBrand}">
                        <%-- Chỉ xem Category --%>
                        <span>${currentCategory.name}</span>
                    </c:when>
                    <c:otherwise>
                        <%-- Xem Brand trong Category --%>
                        <a href="<c:url value='/products?category=${currentCategory.slug}'/>">${currentCategory.name}</a>
                        <i class="fa-solid fa-chevron-right"></i>
                        <span>${currentBrand.name}</span>
                    </c:otherwise>
                </c:choose>
            </c:when>

            <%-- TRƯỜNG HỢP 2: Không có Category, nhưng CÓ Brand (Click từ Trang chủ) --%>
            <c:when test="${not empty currentBrand}">
                <span>Thương hiệu ${currentBrand.name}</span>
            </c:when>

            <%-- TRƯỜNG HỢP 3: Tìm kiếm --%>
            <c:when test="${not empty param.q}">
                <span>Tìm kiếm: "${param.q}"</span>
            </c:when>

            <%-- TRƯỜNG HỢP 4: Mặc định --%>
            <c:otherwise>
                <span>Tất cả sản phẩm</span>
            </c:otherwise>
        </c:choose>
    </nav>

    <%-- 2. SORTING TOOLBAR --%>
    <div class="sort-bar">
        <h3>Sắp xếp theo</h3>
        <div class="sort-options">
            <a href="?${currentQueryString}sort=popular"
               class="btn-sort ${empty param.sort || param.sort == 'popular' ? 'active' : ''}">
                <i class="fa-regular fa-star"></i> Phổ biến
            </a>

            <a href="?${currentQueryString}sort=price_asc"
               class="btn-sort ${param.sort == 'price_asc' ? 'active' : ''}">
                <i class="fa-solid fa-arrow-up-wide-short"></i> Giá Thấp - Cao
            </a>

            <a href="?${currentQueryString}sort=price_desc"
               class="btn-sort ${param.sort == 'price_desc' ? 'active' : ''}">
                <i class="fa-solid fa-arrow-down-wide-short"></i> Giá Cao - Thấp
            </a>
        </div>
    </div>

    <%-- 3. PRODUCT GRID --%>
    <div class="product-list-grid" id="productGrid">
        <c:forEach var="p" items="${products}" varStatus="status">

            <c:if test="${p.status == 1}">
                <div class="product-card ${status.index >= 20 ? 'hidden-item' : ''}">
                    <a href="<c:url value='/product-detail?id=${p.id}'/>" class="product-link">

                            <%-- Tag giảm giá --%>
                        <div class="product-tags">
                            <c:if test="${p.onSale}">
                                <span class="tag tag-discount">
                                    Giảm ${p.discountPercent}%
                                </span>
                            </c:if>
                        </div>

                        <img src="<c:url value='/${p.thumbnailUrl}'/>" alt="${p.name}" loading="lazy">

                        <h3>${p.name}</h3>

                        <div class="price-container">
                            <c:choose>
                                <c:when test="${p.onSale}">
                                    <p class="sale-price"><fmt:formatNumber value="${p.salePrice}" type="number" pattern="#,##0"/> ₫</p>
                                    <p class="original-price"><fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> ₫</p>
                                </c:when>
                                <c:otherwise>
                                    <p class="sale-price"><fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> ₫</p>
                                    <p class="original-price" style="visibility: hidden;">&nbsp;</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </a>

                    <div class="rating-wishlist-box">
                        <div class="rating">
                            <c:if test="${p.reviewCount > 0}">
                                <i class="fa-solid fa-star"></i>
                                <span><fmt:formatNumber value="${p.avgRating}" maxFractionDigits="1" /></span>
                                <span style="font-weight: 400; font-size: 0.8em; margin-left: 2px;">(${p.reviewCount})</span>
                            </c:if>
                        </div>
                        <button class="btn-wishlist ${wishlistIds.contains(p.id) ? 'active' : ''}"
                                data-product-id="${p.id}"
                                title="Thêm vào yêu thích">
                            <i class="icon-heart-empty fa-regular fa-heart"></i>
                            <i class="icon-heart-filled fa-solid fa-heart"></i>
                            <span>Yêu thích</span>
                        </button>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>

    <%-- 4. LOAD MORE BUTTON --%>
    <c:if test="${products.size() > 20}">
        <div class="load-more-container">
            <button id="btnLoadMore" class="btn-load-more">
                Xem thêm <span id="remainingCount">${products.size() - 20}</span> sản phẩm
                <i class="fa-solid fa-chevron-down"></i>
            </button>
        </div>
    </c:if>

</main>

<script>
    const isUserLoggedIn = ${not empty sessionScope.user};
</script>
<script src="<c:url value='/js/productList.js'/>"></script>

<jsp:include page="/WEB-INF/layout/footer.jsp" />