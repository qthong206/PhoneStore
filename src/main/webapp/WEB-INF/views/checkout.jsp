<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%-- Redirect nếu giỏ hàng rỗng --%>
<c:if test="${empty sessionScope.cart.items}">
    <c:redirect url="/cart" />
</c:if>

<c:set var="pageTitle" value="Thanh toán" scope="request"/>
<jsp:include page="/WEB-INF/layout/header.jsp" />

<%-- Load CSS --%>
<link rel="stylesheet" href="<c:url value='/css/cart.css'/>">
<link rel="stylesheet" href="<c:url value='/css/checkout.css'/>">

<main class="container container-narrow">

    <%-- Header --%>
    <div class="checkout-header">
        <a href="<c:url value='/cart'/>" class="checkout-back-btn" id="back-to-cart-btn" title="Quay lại giỏ hàng">
            <i class="fa-solid fa-arrow-left"></i>
        </a>
        <a href="#" class="checkout-back-btn" id="back-to-step-1-btn" style="display:none;" title="Quay lại bước trước">
            <i class="fa-solid fa-arrow-left"></i>
        </a>
        <h2 id="checkout-title">Thông Tin Giao Hàng</h2>
    </div>

    <%-- Thanh tiến trình --%>
    <div class="checkout-steps">
        <div class="step-item active" id="step-header-1">
            <span>1</span> Thông tin
        </div>
        <div class="step-item" id="step-header-2">
            <span>2</span> Thanh toán
        </div>
    </div>

    <form action="<c:url value='/checkout'/>" method="post" id="checkout-form">

        <c:if test="${not empty checkoutError}">
            <div class="checkout-error-box">
                <i class="fa-solid fa-triangle-exclamation"></i>
                <span>${checkoutError}</span>
            </div>
        </c:if>

        <%-- === BƯỚC 1: THÔNG TIN GIAO HÀNG === --%>
        <div id="step-1-content">

            <%-- Danh sách sản phẩm tóm tắt --%>
            <div class="checkout-section">
                <h3>Đơn hàng của bạn</h3>
                <c:forEach var="item" items="${sessionScope.checkoutCart.items}">
                    <div class="summary-item">
                        <img src="<c:url value='/${item.product.thumbnailUrl}'/>" alt="${item.product.name}">
                        <div class="item-info">
                            <p>${item.product.name}</p>

                            <c:set var="priceToUse" value="${item.product.price}" />
                            <c:if test="${item.product.salePrice > 0}">
                                <c:set var="priceToUse" value="${item.product.salePrice}" />
                            </c:if>

                            <p class="item-price-step1">
                                <fmt:formatNumber value="${priceToUse}" type="number" pattern="#,##0"/> ₫
                            </p>
                        </div>
                        <span class="item-quantity">x${item.quantity}</span>
                    </div>
                </c:forEach>
            </div>

            <%-- Thông tin khách hàng --%>
            <div class="checkout-section">
                <h3>Thông tin khách hàng</h3>
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <div class="form-group">
                            <label>Họ và tên</label>
                            <input type="text" name="recipient_name_guest" id="name-guest" class="form-control" placeholder="Nhập họ tên người nhận" required>
                        </div>
                        <div class="form-row-2">
                            <div class="form-group">
                                <label>Số điện thoại</label>
                                <input type="tel" name="recipient_phone_guest" id="phone-guest" class="form-control" placeholder="Nhập số điện thoại" required>
                            </div>
                            <div class="form-group">
                                <label>Email <span style="font-weight:normal; color:#888; font-size:0.9em;">(Không bắt buộc)</span></label>
                                <input type="email" name="recipient_email_guest" id="email-guest" class="form-control" placeholder="Nhập email nếu muốn nhận thông báo">
                            </div>
                        </div>
                    </c:when>
                    <%-- Trường hợp đã đăng nhập (USER) --%>
                    <c:otherwise>
                        <div class="user-info-box">
                            <i class="fa-solid fa-circle-user"></i>
                            <div>
                                Đăng nhập với: <strong>${sessionScope.user.fullName}</strong><br>
                                <small>(${sessionScope.user.email})</small>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="checkout-section">
                <h3>Địa chỉ nhận hàng</h3>

                <%-- LOGIC KHI ĐÃ LOGIN --%>
                <c:if test="${not empty sessionScope.user}">
                    <div class="address-toggle-group">
                        <div class="radio-group">
                            <input type="radio" name="address_option" id="addr-default" value="default" checked>
                            <label for="addr-default">Sử dụng sổ địa chỉ</label>
                        </div>
                        <div class="radio-group">
                            <input type="radio" name="address_option" id="addr-new" value="new">
                            <label for="addr-new">Địa chỉ khác</label>
                        </div>
                    </div>

                    <%-- A. CHỌN TỪ SỔ ĐỊA CHỈ --%>
                    <div id="default-address-box">
                        <c:choose>
                            <c:when test="${not empty userAddresses}">
                                <div class="form-group">
                                    <label>Chọn địa chỉ có sẵn:</label>
                                    <select id="address-selector" class="form-control">
                                        <c:forEach var="addr" items="${userAddresses}">
                                            <option value="${addr.id}"
                                                    data-name="${addr.receiverName}"
                                                    data-phone="${addr.phoneNumber}"
                                                    data-address="${addr.streetAddress}"
                                                ${not empty defaultAddress && addr.id == defaultAddress.id ? 'selected' : ''}>
                                                    ${addr.addressType} - ${addr.streetAddress}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-row-2">
                                    <div class="form-group">
                                        <label>Người nhận</label>
                                        <input type="text" name="recipient_name_default" id="name-default" class="form-control" value="${defaultAddress.receiverName}" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label>SĐT</label>
                                        <input type="tel" name="recipient_phone_default" id="phone-default" class="form-control" value="${defaultAddress.phoneNumber}" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>Địa chỉ chi tiết</label>
                                    <textarea name="shipping_address_default" id="address-default" class="form-control" rows="2" readonly>${defaultAddress.streetAddress}</textarea>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="empty-address-msg">
                                    <i class="fa-regular fa-folder-open"></i><br>
                                    Bạn chưa lưu địa chỉ nào.<br>Vui lòng chọn "Địa chỉ khác" để nhập mới.
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <%-- B. NHẬP ĐỊA CHỈ MỚI --%>
                    <div id="new-address-box" style="display:none;">
                        <div class="form-row-2">
                            <div class="form-group">
                                <label>Họ tên người nhận</label>
                                <input type="text" name="recipient_name_new" id="name-new" class="form-control" placeholder="Nhập họ tên người nhận">
                            </div>
                            <div class="form-group">
                                <label>SĐT người nhận</label>
                                <input type="tel" name="recipient_phone_new" id="phone-new" class="form-control" placeholder="Nhập số điện thoại">
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Địa chỉ nhận hàng</label>
                            <textarea name="shipping_address_new" id="address-new" class="form-control" rows="3" placeholder="Số nhà, tên đường, phường/xã, quận/huyện..."></textarea>
                        </div>
                    </div>
                </c:if>

                <%-- LOGIC KHI CHƯA LOGIN --%>
                <c:if test="${empty sessionScope.user}">
                    <input type="hidden" name="address_option" value="new">
                    <div class="form-group">
                        <label>Địa chỉ chi tiết</label>
                        <textarea name="shipping_address_new" id="address-guest" class="form-control" rows="3" required placeholder="Số nhà, tên đường, phường/xã, quận/huyện..."></textarea>
                    </div>
                </c:if>
            </div>

            <div class="checkout-footer">
                <div class="footer-total">
                    <span>Tổng tiền tạm tính:</span>
                    <strong><fmt:formatNumber value="${sessionScope.checkoutCart.total}" type="number" pattern="#,##0"/> ₫</strong>
                </div>
                <button type="button" class="btn btn-primary" id="btn-to-step-2">
                    Tiếp tục <i class="fa-solid fa-arrow-right"></i>
                </button>
            </div>
        </div>

        <%-- === BƯỚC 2: THANH TOÁN & XÁC NHẬN === --%>
        <div id="step-2-content" style="display:none;">

            <%-- Tóm tắt chi phí --%>
            <div class="checkout-section">
                <h3>Chi phí thanh toán</h3>
                <div class="summary-totals-step2">
                    <div class="summary-row">
                        <span>Tạm tính</span>
                        <span><fmt:formatNumber value="${sessionScope.checkoutCart.total}" type="number" pattern="#,##0"/> ₫</span>
                    </div>
                    <div class="summary-row">
                        <span>Phí vận chuyển</span>
                        <span style="color:var(--color-success);">Miễn phí</span>
                    </div>
                    <div class="summary-row total">
                        <span>Tổng thanh toán</span>
                        <span><fmt:formatNumber value="${sessionScope.checkoutCart.total}" type="number" pattern="#,##0"/> ₫</span>
                    </div>
                </div>
            </div>

            <%-- Phương thức thanh toán --%>
            <div class="checkout-section">
                <h3>Phương thức thanh toán</h3>
                <div class="payment-method">
                    <input type="radio" name="payment_method" id="payment-cod" value="cod" checked hidden>
                    <label for="payment-cod">
                        <i class="fa-solid fa-money-bill-wave"></i>
                        <div>
                            <strong>Thanh toán khi nhận hàng (COD)</strong><br>
                            <small style="color:#666;">Thanh toán tiền mặt cho shipper khi nhận được hàng.</small>
                        </div>
                    </label>
                </div>
                <div class="payment-method">
                    <input type="radio" name="payment_method" id="payment-bank" value="bank_transfer" hidden>
                    <label for="payment-bank">
                        <i class="fa-solid fa-building-columns"></i>
                        <div>
                            <strong>Chuyển khoản ngân hàng</strong><br>
                            <small style="color:#666;">Chuyển khoản qua QR Code hoặc Internet Banking.</small>
                        </div>
                    </label>
                </div>
            </div>

            <%-- Review thông tin --%>
            <div class="checkout-section">
                <h3>Thông tin nhận hàng</h3>
                <div class="shipping-info-summary">
                    <p><i class="fa-regular fa-user" style="width:20px;"></i> <strong>Người nhận:</strong> <span id="summary-name"></span></p>
                    <p><i class="fa-solid fa-phone" style="width:20px;"></i> <strong>SĐT:</strong> <span id="summary-phone"></span></p>
                    <p><i class="fa-solid fa-location-dot" style="width:20px;"></i> <strong>Địa chỉ:</strong> <span id="summary-address"></span></p>
                </div>
            </div>

            <div class="checkout-footer">
                <div class="footer-total">
                    <span>Tổng thanh toán:</span>
                    <strong><fmt:formatNumber value="${sessionScope.checkoutCart.total}" type="number" pattern="#,##0"/> ₫</strong>
                </div>
                <button type="submit" class="btn btn-primary">
                    Đặt Hàng
                </button>
            </div>
        </div>
    </form>
</main>

<script src="<c:url value='/js/checkout.js'/>"></script>
<jsp:include page="/WEB-INF/layout/footer.jsp" />