<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="vi_VN"/>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<%-- Load CSS Layout và CSS riêng cho Order --%>
<link rel="stylesheet" href="<c:url value='/css/user-layout.css'/>">
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
                <div class="content-block">
                    <h3>Lịch sử mua hàng</h3>

                        <%-- Thanh Tab --%>
                    <nav class="tab-nav">
                        <ul>
                            <li><a href="<c:url value='/order?status=all'/>" class="${currentTab == 'all' ? 'active' : ''}">Tất cả</a></li>
                            <li><a href="<c:url value='/order?status=pending'/>" class="${currentTab == 'pending' ? 'active' : ''}">Chờ xác nhận</a></li>
                            <li><a href="<c:url value='/order?status=confirmed'/>" class="${currentTab == 'confirmed' ? 'active' : ''}">Đã xác nhận</a></li>
                            <li><a href="<c:url value='/order?status=shipping'/>" class="${currentTab == 'shipping' ? 'active' : ''}">Đang vận chuyển</a></li>
                            <li><a href="<c:url value='/order?status=delivered'/>" class="${currentTab == 'delivered' ? 'active' : ''}">Đã giao</a></li>
                            <li><a href="<c:url value='/order?status=cancelled'/>" class="${currentTab == 'cancelled' ? 'active' : ''}">Đã hủy</a></li>
                        </ul>
                    </nav>

                        <%-- Bộ lọc ngày --%>
                    <div class="date-filter">
                        <i class="fa-solid fa-filter"></i>
                        <span>Lọc theo ngày: </span>
                        <input type="date" class="form-control" style="width: auto; display: inline-block;">
                        <span>→</span>
                        <input type="date" class="form-control" style="width: auto; display: inline-block;">
                        <button class="btn-link" style="border: none; background: none; cursor: pointer;">Áp dụng</button>
                    </div>

                        <%-- DANH SÁCH ĐƠN HÀNG --%>
                    <c:choose>
                        <c:when test="${not empty orders}">
                            <div class="order-list">
                                <c:forEach var="o" items="${orders}">

                                    <div class="order-card">

                                            <%-- HEADER: Trái (Info) - Phải (Status) --%>
                                        <div class="order-card-header">
                                            <div class="left-info">
                                                <span style="color: #777;">Đơn hàng:</span> <strong>#${o.id}</strong>
                                                <span class="sep">•</span>
                                                <span style="color: #777;">Ngày đặt:</span> <strong><fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy"/></strong>
                                            </div>

                                            <div class="right-status">
                                                <span class="status-badge ${o.status}">
                                                    <c:choose>
                                                        <c:when test="${o.status == 'pending'}">Chờ xác nhận</c:when>
                                                        <c:when test="${o.status == 'confirmed'}">Đã xác nhận</c:when>
                                                        <c:when test="${o.status == 'shipping'}">Đang vận chuyển</c:when>
                                                        <c:when test="${o.status == 'delivered'}">Giao thành công</c:when>
                                                        <c:when test="${o.status == 'cancelled'}">Đã hủy</c:when>
                                                        <c:otherwise>${o.status}</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                        </div>

                                            <%-- BODY: Trái (SP) - Phải (Tiền) --%>
                                        <div class="order-body">

                                                <%-- CỘT TRÁI: SP --%>
                                            <a href="<c:url value='/order-detail?id=${o.id}'/>" class="product-info-col">
                                                <img src="<c:url value='/${not empty o.firstProductImage ? o.firstProductImage : "images/no-image.png"}'/>"
                                                     alt="Sản phẩm" class="product-thumb">

                                                <div class="product-text">
                                                    <h4 class="product-name">
                                                            ${not empty o.firstProductName ? o.firstProductName : "Xem chi tiết đơn hàng..."}
                                                    </h4>
                                                    <div class="product-price-u">
                                                        <fmt:formatNumber value="${o.totalAmount}" type="currency"/>
                                                    </div>
                                                </div>
                                            </a>

                                                <%-- CỘT PHẢI: Tổng tiền & Nút --%>
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
                                <p>Không tìm thấy đơn hàng nào phù hợp.</p>
                                <a href="<c:url value='/home'/>" class="btn">Mua sắm ngay</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${empty sessionScope.user}">
        <div class="login-prompt-card">
            <p>Vui lòng đăng nhập để xem lịch sử mua hàng.</p>
            <a href="<c:url value='/login'/>" class="btn">Đăng nhập ngay</a>
        </div>
    </c:if>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />