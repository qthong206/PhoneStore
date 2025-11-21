<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%-- Thiết lập Locale tiếng Việt --%>
<fmt:setLocale value="vi_VN"/>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<%-- Load CSS: Cần cả user.css (cho bố cục) và order.css (cho giao diện thẻ đơn hàng) --%>
<link rel="stylesheet" href="<c:url value='/css/user-layout.css'/>">
<link rel="stylesheet" href="<c:url value='/css/user.css'/>">
<link rel="stylesheet" href="<c:url value='/css/order.css'/>">

<main class="container user-dashboard">

    <c:if test="${not empty sessionScope.user}">
        <%-- 1. User Info Bar --%>
        <jsp:include page="/WEB-INF/views/user/components/user-info-bar.jsp" />

        <div class="dashboard-body">
                <%-- 2. Sidebar --%>
            <jsp:include page="/WEB-INF/views/user/components/user-sidebar.jsp" />

                <%-- 3. Nội dung chính --%>
            <div class="user-content">

                    <%-- KHỐI 1: ĐƠN HÀNG GẦN ĐÂY (Đã cập nhật cấu trúc HTML mới nhất) --%>
                <div class="content-block">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                        <h3 style="margin: 0; border: none;">Đơn hàng gần đây</h3>
                        <a href="<c:url value='/order'/>" class="btn-link">Xem tất cả <i class="fa-solid fa-arrow-right"></i></a>
                    </div>

                    <c:choose>
                        <c:when test="${not empty recentOrders}">
                            <div class="order-list">
                                <c:forEach var="o" items="${recentOrders}">

                                    <div class="order-card">

                                            <%-- HEADER: Cấu trúc 2 bên (Trái: Info - Phải: Status) --%>
                                        <div class="order-card-header">
                                                <%-- Bên trái: Mã đơn & Ngày --%>
                                            <div class="left-info">
                                                <span style="color: #777;">Đơn hàng:</span> <strong>#${o.id}</strong>
                                                <span class="sep">•</span>
                                                <span style="color: #777;">Ngày đặt:</span> <strong><fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy"/></strong>
                                            </div>

                                                <%-- Bên phải: Badge trạng thái --%>
                                            <div class="right-status">
                                                <span class="status-badge ${o.status}">
                                                    <c:choose>
                                                        <c:when test="${o.status == 'pending'}">Chờ xác nhận</c:when>
                                                        <c:when test="${o.status == 'shipping'}">Đang vận chuyển</c:when>
                                                        <c:when test="${o.status == 'delivered'}">Giao thành công</c:when>
                                                        <c:when test="${o.status == 'cancelled'}">Đã hủy</c:when>
                                                        <c:otherwise>${o.status}</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                        </div>

                                            <%-- BODY: Cấu trúc Flex ngang (Trái: SP - Phải: Tiền) --%>
                                        <div class="order-body">

                                                <%-- CỘT TRÁI: Thông tin sản phẩm --%>
                                            <a href="<c:url value='/order-detail?id=${o.id}'/>" class="product-info-col">
                                                <img src="<c:url value='/${not empty o.firstProductImage ? o.firstProductImage : "images/no-image.png"}'/>"
                                                     alt="Sản phẩm" class="product-thumb">

                                                <div class="product-text">
                                                    <h4 class="product-name">
                                                            ${not empty o.firstProductName ? o.firstProductName : "Xem chi tiết đơn hàng..."}
                                                    </h4>
                                                    <div class="product-price-u">
                                                            <%-- Giá từng món (nếu có) hoặc ẩn đi --%>
                                                        <fmt:formatNumber value="${o.totalAmount}" type="currency"/>
                                                    </div>
                                                </div>
                                            </a>

                                                <%-- CỘT PHẢI: Tổng tiền & Nút xem chi tiết --%>
                                            <div class="order-summary-col">
                                                <div class="total-row">
                                                    Tổng thanh toán: <span class="total-price-red"><fmt:formatNumber value="${o.totalAmount}" type="currency"/></span>
                                                </div>
                                                <a href="<c:url value='/order-detail?id=${o.id}'/>" class="detail-link">
                                                    Xem chi tiết <i class="fa-solid fa-chevron-right"></i>
                                                </a>
                                            </div>

                                        </div>
                                    </div>

                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <i class="fa-solid fa-box-open"></i>
                                <p>Bạn chưa có đơn hàng nào gần đây.</p>
                                <a href="<c:url value='/home'/>" class="btn">Mua sắm ngay</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                    <%-- KHỐI 2: SẢN PHẨM YÊU THÍCH --%>
                <div class="content-block">
                    <h3>Sản phẩm yêu thích mới nhất</h3>
                    <c:choose>
                        <c:when test="${not empty wishlistItems}">
                            <div class="wishlist-grid">
                                <c:forEach var="p" items="${wishlistItems}">
                                    <div class="wishlist-card" id="wishlist-item-${p.id}">
                                        <a href="<c:url value='/product-detail?id=${p.id}'/>" class="wishlist-card-link">
                                            <img class="wishlist-img" src="<c:url value='/${p.thumbnailUrl}'/>" alt="${p.name}">
                                            <div class="wishlist-info">
                                                <h4 class="wishlist-name">${p.name}</h4>
                                                <div class="wishlist-price-box">
                                                     <span class="wishlist-sale-price">
                                                         <fmt:formatNumber value="${p.salePrice > 0 ? p.salePrice : p.price}" type="currency"/>
                                                     </span>
                                                </div>
                                            </div>
                                        </a>
                                        <button class="wishlist-remove-btn"
                                                onclick="removeFromWishlist(event, ${p.id}, '<c:url value="/wishlist/remove"/>')"
                                                title="Bỏ thích">
                                            <i class="fa-solid fa-heart"></i>
                                        </button>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <i class="fa-solid fa-heart"></i>
                                <p>Hãy lấp đầy trái tim này bằng những sản phẩm bạn yêu thích nhé!</p>
                                <a href="<c:url value='/home'/>" class="btn">Khám phá ngay</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </div>
    </c:if>

    <c:if test="${empty sessionScope.user}">
        <div class="login-prompt-card">
            <p>Vui lòng đăng nhập để xem thông tin tài khoản.</p>
            <a href="<c:url value='/login'/>" class="btn">Đi đến trang đăng nhập</a>
        </div>
    </c:if>
</main>

<script src="<c:url value='/js/user.js'/>"></script>
<jsp:include page="/WEB-INF/layout/footer.jsp" />