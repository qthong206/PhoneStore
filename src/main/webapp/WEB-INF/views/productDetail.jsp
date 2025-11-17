<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<main class="container">
    <c:if test="${not empty product && not empty series}">

        <%-- (Breadcrumbs, Grid, 2 Cột... giữ nguyên) --%>
        <nav class="breadcrumb-nav">
            <a href="<c:url value='/home'/>">Trang chủ</a>
            <i class="fa-solid fa-chevron-right"></i>
            <a href="#">Điện thoại</a>
            <i class="fa-solid fa-chevron-right"></i>
            <a href="#">${product.brand.name}</a>
            <i class="fa-solid fa-chevron-right"></i>
            <span>${series.name} ${product.model}</span>
        </nav>
        <div class="product-detail-grid">
            <div class="product-col-left">
                <h1>${product.name}</h1>
                <div class="rating-summary">
                    <span class="rating-stars">
                        <c:if test="${reviewSummary.totalReviews > 0}">
                            <c:forEach begin="1" end="5" var="i">
                                <c:choose>
                                    <c:when test="${i <= reviewSummary.avgRating}"><i class="fa-solid fa-star"></i></c:when>
                                    <c:otherwise><i class="fa-regular fa-star"></i></c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                    </span>
                    <span class="rating-text">
                        <c:if test="${reviewSummary.totalReviews > 0}">
                            <fmt:formatNumber value="${reviewSummary.avgRating}" maxFractionDigits="1" /> (${reviewSummary.totalReviews} đánh giá)
                        </c:if>
                        <c:if test="${reviewSummary.totalReviews == 0}">(Chưa có đánh giá)</c:if>
                    </span>
                </div>
                <div class="action-links">
                    <a href="#reviews-section" class="action-link"><i class="fa-solid fa-star"></i> Đánh giá</a>
                    <a href="#specs-section" class="action-link"><i class="fa-solid fa-list"></i> Thông số</a>
                    <button id="btn-favorite" class="action-link-btn ${isFavorited ? 'active' : ''}"
                            data-product-id="${product.id}">
                        <i class="icon-heart-empty fa-regular fa-heart"></i>
                        <i class="icon-heart-filled fa-solid fa-heart"></i>
                    </button>
                </div>

                <div class="product-gallery">
                    <div class="gallery-main-image">
                        <img src="<c:url value='/${product.thumbnailUrl}'/>" alt="${product.name}">
                    </div>
                    <div class="gallery-thumbnails">
                        <img src="<c:url value='/${product.thumbnailUrl}'/>" alt="Thumbnail 1" class="active">
                        <c:forEach var="imgUrl" items="${galleryImages}">
                            <c:if test="${imgUrl != product.thumbnailUrl}">
                                <img src="<c:url value='/${imgUrl}'/>" alt="Thumbnail">
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>

                <%-- (Cột bên phải giữ nguyên) --%>
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
                    <div class="variant-picker">
                        <label>Màu sắc</label>
                        <div class="options-list" id="color-options-list">
                            <c:forEach var="c" items="${colors}" varStatus="loop">
                                <a href="#" class="option-item ${loop.index == 0 ? 'active' : ''}">
                                    <span class="color-swatch" style="background-color: ${c.hexCode};"></span>
                                        ${c.name}
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                    <form action="<c:url value='/cart'/>" method="post" class="add-to-cart-form">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.id}">
                        <input type="hidden" name="quantity" value="1">
                        <button type="submit" class="btn btn-primary btn-full">
                            <i class="fa-solid fa-cart-plus"></i> Thêm vào giỏ
                        </button>
                        <button type="submit" class="btn btn-secondary btn-full">
                            Mua ngay
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <div class="product-content-fullwidth">
                <%-- (Section Bài viết và Thông số giữ nguyên) --%>
            <section id="article-section" class="content-section">
                <h2>Bài viết đánh giá sản phẩm</h2>
                <div class="article-content">
                    <p style="white-space: pre-line;">${product.description}</p>
                </div>
            </section>

            <section id="specs-section" class="content-section">
                <h2>Thông số kỹ thuật</h2>
                <c:if test="${not empty specsList}">
                    <table class="specs-table">
                        <c:forEach var="spec" items="${specsList}">
                            <tr>
                                <td>${spec.specKey}</td>
                                <td>${spec.specValue}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
                <c:if test="${empty specsList}">
                    <p>(Chưa có thông số kỹ thuật cho sản phẩm này)</p>
                </c:if>
            </section>

                <%-- (Section Sản phẩm tương tự (5 cột) giữ nguyên) --%>
            <section id="related-section" class="content-section">
                <h2>Sản phẩm tương tự</h2>
                <div class="product-grid" style="grid-template-columns: repeat(5, 1fr);">
                    <c:forEach var="p" items="${relatedProducts}">
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
                    </c:forEach>
                </div>
            </section>

            <section id="reviews-section" class="content-section">
                <h2>Đánh giá & Nhận xét</h2>
                <div class="reviews-summary-box-v2">
                    <div class="summary-score-v2">
                            <%-- (Khối điểm trung bình giữ nguyên) --%>
                        <c:choose>
                            <c:when test="${reviewSummary.totalReviews > 0}">
                                <div class="score-number-v2"><fmt:formatNumber value="${reviewSummary.avgRating}" maxFractionDigits="1" /><span>/5</span></div>
                                <div class="score-stars-v2">
                                    <c:forEach begin="1" end="5" var="i">
                                        <c:choose>
                                            <c:when test="${i <= reviewSummary.avgRating}"><i class="fa-solid fa-star"></i></c:when>
                                            <c:otherwise><i class="fa-regular fa-star"></i></c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                                <div class="score-count-v2">${reviewSummary.totalReviews} đánh giá</div>
                            </c:when>
                            <c:otherwise>
                                <div class="score-number-v2">Chưa có</div>
                                <div class="score-count-v2">(0 đánh giá)</div>
                            </c:otherwise>
                        </c:choose>
                        <button class="btn btn-primary" id="btn-write-review">Viết đánh giá</button>
                    </div>

                        <%-- =================================== --%>
                        <%-- SỬA KHỐI THANH THỐNG KÊ (ĐÃ HẾT CỨNG) --%>
                        <%-- =================================== --%>
                    <div class="summary-bars-v2">
                            <%-- Kiểm tra để tránh lỗi chia cho 0 --%>
                        <c:set var="total" value="${reviewSummary.totalReviews > 0 ? reviewSummary.totalReviews : 1}" />

                        <div class="bar-item">
                            <span>5 <i class="fa-solid fa-star"></i></span>
                            <div class="progress-bar"><div class="progress" style="width: ${reviewSummary.count5 * 100 / total}%;"></div></div>
                            <span>(${reviewSummary.count5})</span>
                        </div>
                        <div class="bar-item">
                            <span>4 <i class="fa-solid fa-star"></i></span>
                            <div class="progress-bar"><div class="progress" style="width: ${reviewSummary.count4 * 100 / total}%;"></div></div>
                            <span>(${reviewSummary.count4})</span>
                        </div>
                        <div class="bar-item">
                            <span>3 <i class="fa-solid fa-star"></i></span>
                            <div class="progress-bar"><div class="progress" style="width: ${reviewSummary.count3 * 100 / total}%;"></div></div>
                            <span>(${reviewSummary.count3})</span>
                        </div>
                        <div class="bar-item">
                            <span>2 <i class="fa-solid fa-star"></i></span>
                            <div class="progress-bar"><div class="progress" style="width: ${reviewSummary.count2 * 100 / total}%;"></div></div>
                            <span>(${reviewSummary.count2})</span>
                        </div>
                        <div class="bar-item">
                            <span>1 <i class="fa-solid fa-star"></i></span>
                            <div class="progress-bar"><div class="progress" style="width: ${reviewSummary.count1 * 100 / total}%;"></div></div>
                            <span>(${reviewSummary.count1})</span>
                        </div>
                    </div>
                </div>

                    <%-- (Danh sách review giữ nguyên) --%>
                <div class="review-list">
                    <c:forEach var="review" items="${reviews}">
                        <div class="review-item">
                            <div class="review-author">
                                <span class="avatar">${review.userFullName.substring(0, 1)}</span>
                                <span class="author-name">${review.userFullName}</span>
                            </div>
                            <div class="review-content">
                                <div class="review-rating">
                                    <c:forEach begin="1" end="5" var="i">
                                        <c:choose>
                                            <c:when test="${i <= review.rating}"><i class="fa-solid fa-star" style="color: #ffc107;"></i></c:when>
                                            <c:otherwise><i class="fa-regular fa-star" style="color: #ccc;"></i></c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                                <p class="review-body">${review.commentBody}</p>
                                <span class="review-date">
                                    Đã đánh giá vào <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy"/>
                                </span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </section>
        </div>
    </c:if>

    <c:if test="${empty product}">
        <div class="empty-state">(Placeholder Lỗi)</div>
    </c:if>

    <%-- (Modal giữ nguyên) --%>
    <c:if test="${not empty sessionScope.user}">
        <div id="review-form-modal" class="modal-backdrop" style="display:none;">
            <div class="modal-content form-modal">
                <button class="modal-close" id="modal-close-form-btn">&times;</button>
                <h3>Viết đánh giá của bạn</h3>
                <form action="<c:url value='/review'/>" method="post" class="review-form-container">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="productId" value="${product.id}">
                    <div class="form-group">
                        <label>Đánh giá chung của bạn:</label>
                        <div class="rating-input">
                            <input type="radio" id="star5" name="rating" value="5" required><label for="star5" title="Tuyệt vời"><i class="fa-solid fa-star"></i></label>
                            <input type="radio" id="star4" name="rating" value="4"><label for="star4" title="Tốt"><i class="fa-solid fa-star"></i></label>
                            <input type="radio" id="star3" name="rating" value="3"><label for="star3" title="Bình thường"><i class="fa-solid fa-star"></i></label>
                            <input type="radio" id="star2" name="rating" value="2"><label for="star2" title="Tệ"><i class="fa-solid fa-star"></i></label>
                            <input type="radio" id="star1" name="rating" value="1"><label for="star1" title="Rất Tệ"><i class="fa-solid fa-star"></i></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="review-comment">Nhận xét của bạn:</label>
                        <textarea name="comment_body" id="review-comment" class="form-control" rows="4" placeholder="Xin mời chia sẻ một số cảm nhận về sản phẩm..."></textarea>
                    </div>
                    <button type="submit" class="btn btn-modal-primary" style="width: 100%;">Gửi đánh giá</button>
                </form>
            </div>
        </div>
    </c:if>
</main>

<script src="<c:url value='/js/productDetail.js'/>"></script>
<jsp:include page="/WEB-INF/layout/footer.jsp" />