<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<%-- Tải file CSS mới --%>
<link rel="stylesheet" href="<c:url value='/css/orderSuccess.css'/>">

<main class="container">
    <div class="success-box">
        <div class="success-icon">
            <i class="fa-solid fa-check"></i>
        </div>
        <h2>Đặt hàng thành công!</h2>
        <p>Cảm ơn bạn đã tin tưởng và mua sắm tại PhoneStore.</p>
        <p>Mã đơn hàng của bạn là: <strong>#${orderId}</strong></p>

        <div class="success-actions">
            <a href="<c:url value='/home'/>" class="btn btn-secondary">Về trang chủ</a>
            <%-- TODO: Tạo trang Lịch sử đơn hàng --%>
            <a href="<c:url value='/order'/>" class="btn btn-primary">Xem chi tiết đơn hàng</a>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />