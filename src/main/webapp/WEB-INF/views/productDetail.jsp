<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%-- KHÔNG CẦN JSTL FUNCTIONS NỮA --%>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<main class="container">
    <c:if test="${not empty product && not empty series}">

        <%-- 1. THANH ĐIỀU HƯỚNG (BREADCRUMBS) - ĐÃ SỬA --%>
        <nav class="breadcrumb-nav">
            <a href="<c:url value='/home'/>">Trang chủ</a>
            <i class="fa-solid fa-chevron-right"></i>
            <a href="#">Điện thoại</a>
            <i class="fa-solid fa-chevron-right"></i>
            <a href="#">${product.brand.name}</a>
            <i class="fa-solid fa-chevron-right"></i>
            <span>${series.name} ${product.model}</span>
        </nav>

        <%-- 2. KHUNG CHÍNH (2 CỘT) --%>
        <div class="product-detail-grid">

                <%-- CỘT BÊN TRÁI --%>
            <div class="product-col-left">
                    <%-- Tên đầy đủ: "iPhone 15 Pro Max 256GB" --%>
                <h1>${product.name}</h1>

                <div class="rating-summary">
                    <span class="rating-stars">
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-solid fa-star-half-stroke"></i>
                    </span>
                    <span class="rating-text">4.9 (346 đánh giá)</span>
                </div>
                <div class="action-links">
                    <a href="#reviews-section" class="action-link"><i class="fa-solid fa-star"></i> Đánh giá</a>
                    <a href="#specs-section" class="action-link"><i class="fa-solid fa-list"></i> Thông số</a>
                    <a href="#" class="action-link"><i class="fa-regular fa-heart"></i> Yêu thích</a>
                </div>
                <div class="product-gallery">
                    <div class="gallery-main-image">
                        <img src="<c:url value='${product.thumbnailUrl}'/>" alt="${product.name}">
                    </div>
                    <div class="gallery-thumbnails">
                        <img src="<c:url value='${product.thumbnailUrl}'/>" alt="Thumbnail 1" class="active">
                        <img src="https://placehold.co/100x100/f0f0f0/ccc?text=Ảnh+2" alt="Thumbnail 2">
                        <img src="https://placehold.co/100x100/f0f0f0/ccc?text=Ảnh+3" alt="Thumbnail 3">
                        <img src="https://placehold.co/100x100/f0f0f0/ccc?text=Ảnh+4" alt="Thumbnail 4">
                    </div>
                </div>
            </div>

                <%-- CỘT BÊN PHẢI --%>
            <div class="product-col-right">
                <div class="price-box sticky-sidebar">
                    <div class="price-container">
                        <c:choose>
                            <c:when test="${product.salePrice > 0}">
                                <p class="price sale-price"><fmt:formatNumber value="${product.salePrice}" type="number" pattern="#,##0"/> ₫</p>
                                <p class="price original-price"><fmt:formatNumber value="${product.price}" type="number" pattern="#,##0"/> ₫</p>
                            </c:when>
                            <c:otherwise>
                                <p class="price sale-price"><fmt:formatNumber value="${product.price}" type="number" pattern="#,##0"/> ₫</p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="variant-picker">
                        <label>Dung lượng</label>
                        <div class="options-list">
                            <c:forEach var="v" items="${variants}">
                                <a href="<c:url value='/product-detail?id=${v.id}'/>"
                                   class="option-item ${v.id == product.id ? 'active' : ''}">
                                        ${v.storage}
                                </a>
                            </c:forEach>
                        </div>
                    </div>

                        <%-- Lựa chọn màu sắc --%>
                    <div class="variant-picker">
                        <label>Màu sắc</label>
                        <div class="options-list">
                            <c:forEach var="c" items="${colors}">
                                <a href="#" class="option-item">
                                    <span class="color-swatch" style="background-color: ${c.hexCode};"></span>
                                        ${c.name}
                                </a>
                            </c:forEach>
                        </div>
                    </div>

                        <%-- Form Thêm vào giỏ --%>
                    <form action="<c:url value='/cart'/>" method="post" class="add-to-cart-form">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.id}">
                        <input type="hidden" name="quantity" value="1">

                        <button type_submit" class="btn btn-primary btn-full">
                        <i class="fa-solid fa-cart-plus"></i> Thêm vào giỏ
                        </button>
                        <button type="submit" class="btn btn-secondary btn-full">
                            Mua ngay
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <%-- 3. KHUNG NỘI DUNG (FULL-WIDTH) --%>
        <div class="product-content-fullwidth">

            <section id="article-section" class="content-section">
                <h2>Bài viết đánh giá sản phẩm</h2>
                <div class="article-content">
                        <%-- Dùng mô tả của biến thể (vì bảng series đã bỏ cột description) --%>
                    <p>${product.description}</p>
                </div>
            </section>

            <section id="specs-section" class="content-section">
                <h2>Thông số kỹ thuật</h2>
                <table class="specs-table">
                    <tr><td>Kích thước màn hình</td><td>6.9 inches</td></tr>
                    <tr><td>Công nghệ màn hình</td><td>Super Retina XDR OLED</td></tr>
                    <tr><td>Camera sau</td><td>Camera chính: 48MP...</td></tr>
                    <tr><td>Chipset</td><td>Apple A18 Pro (Giả lập)</td></tr>
                </table>
            </section>

            <section id="related-section" class="content-section">
                <h2>Sản phẩm tương tự</h2>
                <div class="product-grid" style="grid-template-columns: repeat(4, 1fr);">
                    <div class="product-card">...</div>
                </div>
            </section>

            <section id="reviews-section" class="content-section">
                <h2>Đánh giá & Nhận xét</h2>
                <div class="reviews-summary-box">
                    <div class="reviews-score">...</div>
                    <div class="reviews-bars">...</div>
                    <button class="btn btn-primary">Viết đánh giá</button>
                </div>
            </section>

        </div>
    </c:if>

    <%-- Xử lý nếu Servlet không tìm thấy sản phẩm --%>
    <c:if test="${empty product}">
        <div class="empty-state" style="text-align: center; padding: 50px;">
            <h2>Không tìm thấy sản phẩm</h2>
            <p>Sản phẩm bạn đang tìm kiếm không tồn tại hoặc đã bị xóa.</p>
            <a href="<c:url value='/home'/>" class="btn">Quay về trang chủ</a>
        </div>
    </c:if>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />