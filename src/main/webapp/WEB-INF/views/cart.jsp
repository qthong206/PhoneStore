<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="Giỏ Hàng" scope="request"/>
<jsp:include page="/WEB-INF/layout/header.jsp" />

<link rel="stylesheet" href="<c:url value='/css/cart.css'/>">

<main class="container">
    <h2>Giỏ Hàng Của Bạn</h2>

    <c:if test="${empty sessionScope.cart.items}">
        <div class="empty-cart">
            <p>Giỏ hàng của bạn đang trống.</p>
            <a href="<c:url value='/'/>" class="btn">Tiếp tục mua sắm</a>
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.cart.items}">
        <div class="cart-layout">
            <table class="cart-table">
                <thead>
                <tr>
                    <th colspan="2">Sản phẩm</th>
                    <th>Đơn giá</th>
                    <th>Số lượng</th>
                    <th>Thành tiền</th>
                    <th>Xóa</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${sessionScope.cart.items}">
                    <tr>
                        <td><img src="<c:url value='/${item.product.image}'/>" alt="${item.product.name}"></td>
                        <td>${item.product.name}</td>
                        <td><fmt:formatNumber value="${item.product.price}" type="number" pattern="#,##0"/> ₫</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.product.price * item.quantity}" type="number" pattern="#,##0"/> ₫</td>
                        <td><a href="<c:url value='/cart?action=remove&id=${item.product.id}'/>" class="remove-btn">×</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="cart-summary">
                <h3>Tổng Cộng</h3>
                <div class="summary-row">
                    <span>Tổng tiền hàng</span>
                    <span><fmt:formatNumber value="${sessionScope.cart.total}" type="number" pattern="#,##0"/> ₫</span>
                </div>
                <div class="summary-row total">
                    <span>Thanh toán</span>
                    <span><fmt:formatNumber value="${sessionScope.cart.total}" type="number" pattern="#,##0"/> ₫</span>
                </div>
                <a href="#" class="btn checkout-btn">Tiến hành thanh toán</a>
            </div>
        </div>
    </c:if>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />