<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageCss" value="home.css" scope="request" />
<jsp:include page="/WEB-INF/layout/header.jsp" />

<main class="container">
    <%-- Lặp qua từng thương hiệu trong Map --%>
    <c:forEach var="entry" items="${productMap}">
        <c:set var="brand" value="${entry.key}" />
        <c:set var="productsInBrand" value="${entry.value}" />

        <section class="brand-section">
            <div class="section-header">
                <h2>${brand.name} Chính Hãng</h2>
                <a href="#" class="view-all-link">Xem tất cả</a>
            </div>

            <div class="product-grid">
                    <%-- Lặp qua các sản phẩm của thương hiệu hiện tại --%>
                <c:forEach var="p" items="${productsInBrand}">
                    <div class="product-card">
                        <a href="<c:url value='/product-detail?id=${p.id}'/>" class="product-link">
                            <div class="product-tags">
                                <span class="tag tag-promo">Trả góp 0%</span>
                                    <%-- Thêm logic để hiển thị các tag khác nếu cần --%>
                            </div>
                            <img src="${p.thumbnailUrl}" alt="${p.name}">
                            <h3>${p.name}</h3>
                            <div class="price-container">
                                <c:choose>
                                    <%-- TRƯỜNG HỢP 1: CÓ GIÁ KHUYẾN MÃI --%>
                                    <c:when test="${p.salePrice > 0}">
                                        <p class="sale-price"><fmt:formatNumber value="${p.salePrice}" type="number" pattern="#,##0"/> ₫</p>
                                        <p class="original-price"><fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> ₫</p>
                                    </c:when>

                                    <%-- TRƯỜNG HỢP 2: KHÔNG CÓ GIÁ KHUYẾN MÃI --%>
                                    <c:otherwise>
                                        <p class="sale-price"><fmt:formatNumber value="${p.price}" type="number" pattern="#,##0"/> ₫</p>
                                        <%-- Có thể để một dòng trống để giữ layout --%>
                                        <p class="original-price">&nbsp;</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </section>
    </c:forEach>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />