<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="vi_VN"/>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<main class="container">

    <%-- 1. BREADCRUMBS --%>
    <nav class="breadcrumb-nav">
        <a href="<c:url value='/home'/>">Trang chủ</a>
        <i class="fa-solid fa-chevron-right"></i>

        <c:choose>
            <c:when test="${not empty currentCategory}">
                <c:choose>
                    <c:when test="${empty currentBrand}">
                        <span>${currentCategory.name}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/products?category=${currentCategory.slug}'/>">${currentCategory.name}</a>
                        <i class="fa-solid fa-chevron-right"></i>
                        <span>${currentBrand.name}</span>
                    </c:otherwise>
                </c:choose>
            </c:when>
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
                        <div class="product-tags">
                            <c:if test="${p.salePrice > 0}">
                                <c:set var="discountPercent" value="${(p.price - p.salePrice) / p.price}" />
                                <span class="tag tag-discount">
                                    Giảm <fmt:formatNumber value="${discountPercent}" type="percent" maxFractionDigits="0" />
                                </span>
                            </c:if>
                        </div>

                        <img src="<c:url value='/${p.thumbnailUrl}'/>" alt="${p.name}" loading="lazy">

                        <h3>${p.name}</h3>

                        <div class="price-container">
                            <c:choose>
                                <c:when test="${p.salePrice > 0}">
                                    <p class="sale-price"><fmt:formatNumber value="${p.salePrice}" type="number" pattern="#,##0"/> ₫</p>
                                    <p class="original-price"><fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> ₫</p>
                                </c:when>
                                <c:otherwise>
                                    <p class="sale-price"><fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> ₫</p>
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