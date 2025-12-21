<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN"/>

<c:set var="pageCss" value="home.css" scope="request" />
<jsp:include page="/WEB-INF/layout/header.jsp" />

<main class="container">
    <c:forEach var="entry" items="${productMap}">
        <c:set var="brand" value="${entry.key}" />
        <c:set var="productsInBrand" value="${entry.value}" />

        <section class="brand-section">
            <div class="section-header">
                <h2>${brand.name} Chính Hãng</h2>
                <a href="<c:url value='/products?brand=${brand.slug}'/>" class="view-all-link">Xem tất cả</a>
            </div>

            <div class="product-grid">
                <c:forEach var="p" items="${productsInBrand}" varStatus="loop">
                    <c:if test="${p.status == 1}">

                        <c:if test="${loop.index < 5}">
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
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </section>
    </c:forEach>
</main>

<script>
    const isUserLoggedIn = ${not empty sessionScope.user};
</script>

<script src="<c:url value='/js/home.js'/>"></script>

<jsp:include page="/WEB-INF/layout/footer.jsp" />