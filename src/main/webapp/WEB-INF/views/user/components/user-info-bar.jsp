<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="user-page-header">
    <div class="header-info-block user-details-block">
        <div class="avatar">
            <i class="fa-solid fa-user"></i>
        </div>
        <div class="info-text">
            <strong>${sessionScope.user.fullName}</strong>
            <p>${sessionScope.user.phoneNumber}</p>
            <span>Hạng: Thành viên mới</span>
        </div>
    </div>
    <div class="header-info-block">
        <div class="icon-wrapper">
            <i class="fa-solid fa-receipt"></i>
        </div>
        <div class="info-text">
            <%-- Sử dụng biến dynamic --%>
            <strong>${not empty totalOrders ? totalOrders : 0}</strong>
            <p>Tổng số đơn hàng đã mua</p>
        </div>
    </div>
    <div class="header-info-block">
        <div class="icon-wrapper">
            <i class="fa-solid fa-sack-dollar"></i>
        </div>
        <div class="info-text">
            <%-- Sử dụng biến dynamic --%>
            <strong><fmt:formatNumber value="${not empty totalSpent ? totalSpent : 0}" type="currency" currencySymbol="đ"/></strong>
            <p>Tổng tiền tích lũy</p>
        </div>
    </div>
</div>