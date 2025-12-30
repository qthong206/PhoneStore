<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<link rel="stylesheet" href="<c:url value='/css/orderSuccess.css'/>">

<main class="container">
    <div class="success-wrapper">
        <div class="success-box">
            <div class="icon-container">
                <div class="success-icon">
                    <i class="fa-solid fa-check"></i>
                </div>
                <div class="icon-ring"></div>
            </div>

            <h2>Đặt hàng thành công!</h2>
            <p class="sub-text">Cảm ơn bạn đã tin tưởng và mua sắm tại PhoneStore.</p>

            <div class="order-info">
                <span>Mã đơn hàng:</span>
                <span class="order-id">#${orderId}</span>
            </div>

            <p class="email-note">Thông tin chi tiết đơn hàng đã được gửi tới email của bạn.</p>

            <div class="success-actions">
                <a href="<c:url value='/home'/>" class="btn btn-secondary">
                    <i class="fa-solid fa-house"></i> Về trang chủ
                </a>
                <a href="<c:url value='/order'/>" class="btn btn-primary">
                    Xem đơn hàng <i class="fa-solid fa-arrow-right"></i>
                </a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />