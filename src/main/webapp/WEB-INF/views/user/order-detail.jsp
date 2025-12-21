<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="vi_VN"/>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<%-- Load CSS: Layout chung và CSS riêng cho chi tiết --%>
<link rel="stylesheet" href="<c:url value='/css/user-layout.css'/>">
<link rel="stylesheet" href="<c:url value='/css/order-detail.css'/>">

<main class="container user-dashboard">

    <c:if test="${not empty sessionScope.user}">

        <%-- 1. User Info Bar --%>
        <jsp:include page="/WEB-INF/views/user/components/user-info-bar.jsp" />

        <div class="dashboard-body">
                <%-- 2. Sidebar --%>
            <jsp:include page="/WEB-INF/views/user/components/user-sidebar.jsp" />

                <%-- 3. Nội dung chính --%>
            <div class="user-content">
                <div class="content-block">

                        <%-- HEADER: Nút quay lại & Trạng thái đơn --%>
                    <div class="detail-header">
                        <a href="<c:url value='/order'/>" class="btn-back">
                            <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
                        </a>
                        <div class="order-id-badge">
                            <span class="text-muted">Mã đơn hàng: #${order.id}</span>
                            <span class="sep">|</span>
                                <%-- Tái sử dụng class status-badge để đồng bộ màu sắc --%>
                            <span class="status-badge ${order.status}">
                                <c:choose>
                                    <c:when test="${order.status == 'pending'}">Chờ xác nhận</c:when>
                                    <c:when test="${order.status == 'confirmed'}">Đã xác nhận</c:when>
                                    <c:when test="${order.status == 'shipping'}">Đang vận chuyển</c:when>
                                    <c:when test="${order.status == 'delivered'}">Giao thành công</c:when>
                                    <c:when test="${order.status == 'cancelled'}">Đã hủy</c:when>
                                    <c:otherwise>${order.status}</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </div>

                        <%-- INFO GRID: Địa chỉ & Thông tin thanh toán --%>
                    <div class="order-info-grid">
                        <div class="info-card">
                            <h4><i class="fa-solid fa-location-dot"></i> Địa chỉ nhận hàng</h4>
                            <div class="info-content">
                                <p class="recipient-name">${order.recipientName}</p>
                                <p class="recipient-phone">${order.recipientPhone}</p>
                                <p class="recipient-address">${order.shippingAddress}</p>
                            </div>
                        </div>

                        <div class="info-card">
                            <h4><i class="fa-solid fa-file-invoice"></i> Thông tin đơn hàng</h4>
                            <div class="info-content">
                                <p><strong>Ngày đặt:</strong> <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/></p>
                                <p><strong>Phương thức thanh toán:</strong>
                                    <c:choose>
                                        <c:when test="${order.paymentMethod == 'cod'}">Thanh toán khi nhận hàng (COD)</c:when>
                                        <c:when test="${order.paymentMethod == 'bank_transfer'}">Chuyển khoản ngân hàng</c:when>
                                        <c:otherwise>${order.paymentMethod}</c:otherwise>
                                    </c:choose>
                                </p>
                                <c:if test="${not empty order.recipientEmail}">
                                    <p><strong>Email:</strong> ${order.recipientEmail}</p>
                                </c:if>
                            </div>
                        </div>
                    </div>

                        <%-- PRODUCT TABLE: Danh sách sản phẩm --%>
                    <h4 class="section-title">Sản phẩm</h4>
                    <div class="table-responsive">
                        <table class="product-table">
                            <thead>
                            <tr>
                                <th width="50%">Sản phẩm</th>
                                <th class="text-center">Đơn giá</th>
                                <th class="text-center">Số lượng</th>
                                <th class="text-right">Thành tiền</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${details}">
                                <tr>
                                    <td>
                                        <div class="product-item">
                                            <img src="<c:url value='/${item.thumbnailUrl}'/>" alt="${item.productName}">
                                            <div class="product-info">
                                                <a href="<c:url value='/product-detail?id=${item.productId}'/>" class="product-link">
                                                        ${item.productName}
                                                </a>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <fmt:formatNumber value="${item.priceAtPurchase}" type="currency"/>
                                    </td>
                                    <td class="text-center">x${item.quantity}</td>
                                    <td class="text-right price-highlight">
                                        <fmt:formatNumber value="${item.totalMoney}" type="currency"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                        <%-- SUMMARY: Tổng tiền --%>
                    <div class="order-summary-footer">
                        <div class="summary-row">
                            <span>Tạm tính:</span>
                            <span><fmt:formatNumber value="${order.totalAmount}" type="currency"/></span>
                        </div>
                        <div class="summary-row">
                            <span>Phí vận chuyển:</span>
                            <span>0 ₫</span>
                        </div>
                        <div class="summary-row total">
                            <span>Tổng thanh toán:</span>
                            <span class="total-price"><fmt:formatNumber value="${order.totalAmount}" type="currency"/></span>
                        </div>

                            <%-- Action: Hủy đơn (Chỉ hiện khi Pending) --%>
                        <c:if test="${order.status == 'pending'}">
                            <div class="action-buttons">
                                <a href="<c:url value='/order?action=cancel&id=${order.id}'/>"
                                   class="btn-cancel"
                                   onclick="return confirm('Bạn có chắc chắn muốn hủy đơn hàng này?')">
                                    Hủy đơn hàng
                                </a>
                            </div>
                        </c:if>
                    </div>

                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${empty sessionScope.user}">
        <div class="login-prompt-card">
            <p>Phiên đăng nhập hết hạn.</p>
            <a href="<c:url value='/login'/>" class="btn">Đăng nhập lại</a>
        </div>
    </c:if>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />