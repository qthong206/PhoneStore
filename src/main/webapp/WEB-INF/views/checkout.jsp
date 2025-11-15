<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%-- Kiểm tra logic: Nếu giỏ hàng rỗng, không cho vào trang thanh toán --%>
<c:if test="${empty sessionScope.cart.items}">
    <c:redirect url="/cart" />
</c:if>

<c:set var="pageTitle" value="Thanh toán" scope="request"/>
<jsp:include page="/WEB-INF/layout/header.jsp" />

<link rel="stylesheet" href="<c:url value='/css/cart.css'/>">
<link rel="stylesheet" href="<c:url value='/css/checkout.css'/>">

<main class="container container-narrow">

    <div class="checkout-header">
        <a href="<c:url value='/cart'/>" class="checkout-back-btn" id="back-to-cart-btn"><i class="fa-solid fa-chevron-left"></i></a>
        <a href="#" class="checkout-back-btn" id="back-to-step-1-btn" style="display:none;"><i class="fa-solid fa-chevron-left"></i></a>
        <h2 id="checkout-title">Thông tin</h2>
    </div>

    <div class="checkout-steps">
        <div class="step-item active" id="step-header-1"><span>1</span> Thông tin</div>
        <div class="step-item" id="step-header-2"><span>2</span> Thanh toán</div>
    </div>

    <form action="<c:url value='/checkout'/>" method="post" id="checkout-form">

        <c:if test="${not empty checkoutError}">
            <div class="checkout-error-box">
                <i class="fa-solid fa-triangle-exclamation"></i>
                <span>${checkoutError}</span>
            </div>
        </c:if>

        <%-- =================================== --%>
        <%-- NỘI DUNG BƯỚC 1: THÔNG TIN --%>
        <%-- =================================== --%>
        <div id="step-1-content">
            <div class="checkout-section">
                <c:forEach var="item" items="${sessionScope.cart.items}">
                    <div class="summary-item">
                        <img src="<c:url value='${item.product.thumbnailUrl}'/>" alt="${item.product.name}">
                        <div class="item-info">
                            <p>${item.product.name}</p>
                            <c:set var="priceToUse" value="${item.product.price}" />
                            <c:if test="${item.product.salePrice > 0}"><c:set var="priceToUse" value="${item.product.salePrice}" /></c:if>
                            <p class="item-price-step1"><fmt:formatNumber value="${priceToUse}" type="number" pattern="#,##0"/> ₫</p>
                        </div>
                        <span class="item-quantity">Số lượng: ${item.quantity}</span>
                    </div>
                </c:forEach>
            </div>
            <div class="checkout-section">
                <h3>Thông tin khách hàng</h3>
                <c:if test="${empty sessionScope.user}">
                    <%-- Giữ required cho các ô của khách (vì nó luôn ở Bước 1) --%>
                    <div class="form-group"><label for="name-guest">Họ và tên</label><input type="text" name="recipient_name_guest" id="name-guest" class="form-control" required></div>
                    <div class="form-group"><label for="phone-guest">Số điện thoại</label><input type="tel" name="recipient_phone_guest" id="phone-guest" class="form-control" required></div>
                    <div class="form-group"><label for="email-guest">Email (để nhận hóa đơn)</label><input type="email" name="recipient_email_guest" id="email-guest" class="form-control" required></div>
                </c:if>
                <c:if test="${not empty sessionScope.user}"><div class="user-info-box"><i class="fa-solid fa-user-check"></i> Đăng nhập với: <strong>${sessionScope.user.fullName}</strong> (${sessionScope.user.email})</div></c:if>
            </div>
            <div class="checkout-section">
                <h3>Thông tin nhận hàng</h3>
                <div class="address-toggle-group">
                    <c:if test="${not empty sessionScope.user}"><div class="radio-group"><input type="radio" name="address_option" id="addr-default" value="default" checked><label for="addr-default">Giao đến địa chỉ của tôi</label></div></c:if>
                    <div class="radio-group"><input type="radio" name="address_option" id="addr-new" value="new" <c:if test="${empty sessionScope.user}">checked</c:if>><label for="addr-new">Giao đến địa chỉ mới</label></div>
                </div>
                <div id="default-address-box">
                    <c:if test="${not empty sessionScope.user}">
                        <div class="form-group"><label for="name-default">Người nhận</label><input type="text" name="recipient_name_default" id="name-default" class="form-control" value="${sessionScope.user.fullName}"></div>
                        <div class="form-group"><label for="phone-default">SĐT Nhận</label><input type="tel" name="recipient_phone_default" id="phone-default" class="form-control" value="${sessionScope.user.phoneNumber}"></div>

                        <%-- ĐÃ XÓA 'required' --%>
                        <div class="form-group"><label for="address-default">Địa chỉ</label><textarea name="shipping_address_default" id="address-default" class="form-control" rows="3">${sessionScope.user.address}</textarea></div>
                    </c:if>
                </div>
                <div id="new-address-box" <c:if test="${not empty sessionScope.user}">style="display:none;"</c:if>>
                    <c:if test="${not empty sessionScope.user}">
                        <div class="form-group"><label for="name-new">Họ và tên người nhận</label><input type="text" name="recipient_name_new" id="name-new" class="form-control"></div>
                        <div class="form-group"><label for="phone-new">SĐT người nhận</label><input type="tel" name="recipient_phone_new" id="phone-new" class="form-control"></div>
                    </c:if>

                    <%-- ĐÃ XÓA 'required' --%>
                    <div class="form-group"><label for="address-new">Địa chỉ nhận hàng</label><textarea name="shipping_address_new" id="address-new" class="form-control" rows="3" placeholder="Vui lòng nhập Tỉnh/Thành, Quận/Huyện, Phường/Xã, Số nhà..."></textarea></div>
                </div>
            </div>
            <div class="checkout-footer">
                <div class="footer-total"><span>Tổng tiền tạm tính:</span><strong><fmt:formatNumber value="${sessionScope.cart.total}" type="number" pattern="#,##0"/> ₫</strong></div>
                <button type="button" class="btn btn-primary" id="btn-to-step-2">Tiếp tục</button>
            </div>
        </div>

        <%-- NỘI DUNG BƯỚC 2 (Giữ nguyên) --%>
        <div id="step-2-content" style="display:none;">
            <%-- (Tất cả code của Bước 2 giữ nguyên) --%>
            <div class="checkout-section"><div class="voucher-box"><input type="text" class="form-control" placeholder="Nhập mã giảm giá (nếu có)"><button type="button" class="btn-apply-voucher">Áp dụng</button></div><div class="voucher-select"><i class="fa-solid fa-ticket"></i><span>hoặc chọn từ 1 mã giảm giá có sẵn</span><i class="fa-solid fa-chevron-right"></i></div></div>
            <div class="checkout-section"><div class="summary-totals-step2">
                <div class="summary-row"><span>Số lượng sản phẩm</span><span>${sessionScope.cart.totalQuantity}</span></div>
                <div class="summary-row"><span>Tổng tiền hàng</span><span><fmt:formatNumber value="${sessionScope.cart.total}" type="number" pattern="#,##0"/> ₫</span></div>
                <div class="summary-row"><span>Phí vận chuyển</span><span>Miễn phí</span></div>
                <div class="summary-row total"><span>Tổng tiền</span><span><fmt:formatNumber value="${sessionScope.cart.total}" type="number" pattern="#,##0"/> ₫</span></div>
            </div></div>
            <div class="checkout-section">
                <h3>Phương thức thanh toán</h3>
                <div class="radio-group payment-method"><input type="radio" name="payment_method" id="payment-cod" value="cod" checked><label for="payment-cod"><i class="fa-solid fa-truck-fast"></i> Thanh toán khi nhận hàng (COD)</label></div>
                <div class="radio-group payment-method"><input type="radio" name="payment_method" id="payment-bank" value="bank_transfer"><label for="payment-bank"><i class="fa-solid fa-university"></i> Chuyển khoản ngân hàng</label></div>
            </div>
            <div class="checkout-section">
                <h3>Thông tin nhận hàng</h3>
                <div class="shipping-info-summary">
                    <p><strong>Khách hàng:</strong> <span id="summary-name"></span></p>
                    <p><strong>Số điện thoại:</strong> <span id="summary-phone"></span></p>
                    <p><strong>Địa chỉ:</strong> <span id="summary-address"></span></p>
                </div>
            </div>
            <div class="checkout-footer">
                <div class="footer-total"><span>Tổng tiền tạm tính:</span><strong><fmt:formatNumber value="${sessionScope.cart.total}" type="number" pattern="#,##0"/> ₫</strong></div>
                <button type="submit" class="btn btn-primary"><i class="fa-solid fa-shield-halved"></i> Thanh Toán</button>
            </div>
            <a href="#" class="check-items-link" id="btn-check-items">Kiểm tra danh sách sản phẩm</a>
        </div>
    </form>

    <%-- MODAL (Giữ nguyên) --%>
    <div id="product-list-modal" class="modal-backdrop" style="display:none;">
        <div class="modal-content form-modal">
            <button class="modal-close" id="modal-close-list-btn">&times;</button>
            <h3>Danh sách sản phẩm</h3>
            <div class="summary-item-list" style="max-height: 300px; overflow-y: auto;">
                <c:forEach var="item" items="${sessionScope.cart.items}">
                    <div class="summary-item">
                        <img src="<c:url value='${item.product.thumbnailUrl}'/>" alt="${item.product.name}">
                        <div class="item-info">
                            <p>${item.product.name}</p>
                            <c:set var="priceToUse" value="${item.product.price}" />
                            <c:if test="${item.product.salePrice > 0}"><c:set var="priceToUse" value="${item.product.salePrice}" /></c:if>
                            <p style="font-size: 0.9rem; color: #555;"><fmt:formatNumber value="${priceToUse}" type="number" pattern="#,##0"/> ₫ x ${item.quantity}</p>
                        </div>
                        <span class="item-price"><fmt:formatNumber value="${priceToUse * item.quantity}" type="number" pattern="#,##0"/> ₫</span>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</main>

<script src="<c:url value='/js/checkout.js'/>"></script>
<jsp:include page="/WEB-INF/layout/footer.jsp" />