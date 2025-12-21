<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Giỏ Hàng" scope="request"/>
<jsp:include page="/WEB-INF/layout/header.jsp" />

<%-- Load CSS --%>
<link rel="stylesheet" href="<c:url value='/css/cart.css'/>">

<main class="container">
    <div class="cart-header-title">
        <h2>Giỏ Hàng Của Bạn</h2>
        <span class="item-count">
            (${empty sessionScope.cart.totalQuantity ? 0 : sessionScope.cart.totalQuantity} sản phẩm)
        </span>
    </div>

    <%-- TRƯỜNG HỢP GIỎ HÀNG TRỐNG --%>
    <c:if test="${empty sessionScope.cart.items}">
        <div class="empty-cart">
            <img src="https://cdn-icons-png.flaticon.com/512/11329/11329060.png" alt="Empty Cart" style="width: 150px; margin-bottom: 20px; opacity: 0.6;">
            <p>Giỏ hàng của bạn đang trống.</p>
            <a href="<c:url value='/'/>" class="btn btn-primary">Tiếp tục mua sắm</a>
        </div>
    </c:if>

    <%-- TRƯỜNG HỢP CÓ SẢN PHẨM --%>
    <c:if test="${not empty sessionScope.cart.items}">
        <div class="cart-layout">

            <div class="cart-items-container">
                <form id="cart-form" action="<c:url value='/checkout'/>" method="POST">
                    <table class="cart-table">
                        <thead>
                        <tr>
                            <th class="col-checkbox">
                                <input type="checkbox" id="selectAll" class="cart-checkbox" checked>
                            </th>
                            <th class="col-product">Sản phẩm</th>
                            <th class="col-price">Đơn giá</th>
                            <th class="col-qty">Số lượng</th>
                            <th class="col-total">Thành tiền</th>
                            <th class="col-action"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${sessionScope.cart.items}">
                            <c:set var="priceToUse" value="${item.product.price}" />
                            <c:if test="${item.product.salePrice > 0}">
                                <c:set var="priceToUse" value="${item.product.salePrice}" />
                            </c:if>
                            <c:set var="lineTotal" value="${priceToUse * item.quantity}" />

                            <tr>
                                <td class="checkbox-cell">
                                    <input type="checkbox"
                                           name="selectedItems"
                                           value="${item.product.id}"
                                           class="cart-checkbox item-checkbox"
                                           data-total="${lineTotal}"
                                           checked>
                                </td>

                                <td>
                                    <div class="product-info-cell">
                                        <a href="<c:url value='/product-detail?id=${item.product.id}'/>">
                                            <img src="<c:url value='/${item.product.thumbnailUrl}'/>" alt="${item.product.name}">
                                        </a>
                                        <div class="product-details">
                                            <a href="<c:url value='/product-detail?id=${item.product.id}'/>" class="product-name-link">
                                                    ${item.product.name}
                                            </a>
                                            <span class="product-variant">Phiên bản: ${item.product.storage}</span>
                                        </div>
                                    </div>
                                </td>

                                <td class="price-cell">
                                    <span class="current-price"><fmt:formatNumber value="${priceToUse}" type="number" pattern="#,##0"/> ₫</span>
                                    <c:if test="${item.product.salePrice > 0 && item.product.price > item.product.salePrice}">
                                        <span class="old-price"><fmt:formatNumber value="${item.product.price}" type="number" pattern="#,##0"/> ₫</span>
                                    </c:if>
                                </td>

                                <td>
                                    <div class="quantity-box">
                                        <a href="<c:url value='/cart?action=update&id=${item.product.id}&quantity=${item.quantity - 1}'/>" class="qty-btn minus"><i class="fa-solid fa-minus"></i></a>
                                        <input type="text" value="${item.quantity}" readonly class="qty-input">
                                        <a href="<c:url value='/cart?action=update&id=${item.product.id}&quantity=${item.quantity + 1}'/>" class="qty-btn plus"><i class="fa-solid fa-plus"></i></a>
                                    </div>
                                </td>

                                <td class="total-cell">
                                    <strong><fmt:formatNumber value="${lineTotal}" type="number" pattern="#,##0"/> ₫</strong>
                                </td>

                                <td>
                                    <a href="<c:url value='/cart?action=remove&id=${item.product.id}'/>" class="remove-btn" title="Xóa sản phẩm">
                                        <i class="fa-regular fa-trash-can"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>

                <%-- CỘT PHẢI: TỔNG KẾT --%>
            <div class="cart-sidebar">
                <div class="cart-summary">
                    <h3>Tổng Cộng</h3>
                    <div class="summary-row">
                        <span>Tạm tính:</span>
                        <span id="subtotal-display"><fmt:formatNumber value="${sessionScope.cart.total}" type="number" pattern="#,##0"/> ₫</span>
                    </div>
                    <div class="summary-row">
                        <span>Đã chọn:</span>
                        <span id="selected-count-display">${sessionScope.cart.totalQuantity} sản phẩm</span>
                    </div>
                    <div class="divider"></div>
                    <div class="summary-row total">
                        <span>Thành tiền:</span>
                        <span class="total-price-red" id="total-display"><fmt:formatNumber value="${sessionScope.cart.total}" type="number" pattern="#,##0"/> ₫</span>
                    </div>
                    <p class="vat-note">(Đã bao gồm VAT nếu có)</p>

                    <button type="button" id="btn-checkout-action" class="btn btn-full btn-checkout">
                        Tiến hành thanh toán
                    </button>

                    <a href="<c:url value='/'/>" class="continue-shopping">
                        <i class="fa-solid fa-arrow-left"></i> Tiếp tục mua sắm
                    </a>
                </div>
            </div>
        </div>
    </c:if>
</main>

<script src="<c:url value='/js/cart.js'/>"></script>

<jsp:include page="/WEB-INF/layout/footer.jsp" />