<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="vi_VN"/>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<%-- Load CSS --%>
<link rel="stylesheet" href="<c:url value='/css/user-layout.css'/>">
<link rel="stylesheet" href="<c:url value='/css/rank.css'/>">

<main class="container user-dashboard">
    <c:if test="${not empty sessionScope.user}">

        <%-- 1. User Info Bar --%>
        <jsp:include page="/WEB-INF/views/user/components/user-info-bar.jsp" />

        <div class="dashboard-body">

                <%-- 2. Sidebar --%>
            <jsp:include page="/WEB-INF/views/user/components/user-sidebar.jsp" />

                <%-- 3. Nội dung chính: Hạng thành viên --%>
            <div class="user-content">

                <div class="content-block">
                    <h3>Hạng thành viên hiện tại</h3>

                        <%-- KHỐI TIẾN TRÌNH HẠNG --%>
                    <div class="rank-progression-container">

                            <%-- Thẻ 1: S-NULL (Hiện tại) --%>
                        <div class="rank-card current">
                            <span class="rank-badge">S-NULL</span>
                            <div style="text-align: center;">
                                <i class="fa-solid fa-user rank-icon"></i>
                            </div>
                            <p class="rank-user-name">${sessionScope.user.fullName}</p>
                            <span class="rank-meta">
                                Đã chi tiêu: <strong><fmt:formatNumber value="${totalSpent}" type="currency"/></strong>
                            </span>
                            <span class="rank-note">Hạng khởi điểm</span>
                        </div>

                            <%-- Thẻ 2: S-NEW (Mục tiêu tiếp theo) --%>
                        <div class="rank-card locked">
                            <span class="rank-badge" style="background-color: #fbd7b5; color: #e67e22;">S-NEW</span>
                            <div style="text-align: center;">
                                <i class="fa-solid fa-lock rank-icon"></i>
                            </div>
                            <p class="rank-user-name">Thành viên Bạc</p>
                            <span class="rank-meta">Yêu cầu: 3.000.000đ</span>
                            <span class="rank-note">
                                Cần thêm
                                <strong>
                                    <fmt:formatNumber value="${3000000 - totalSpent > 0 ? 3000000 - totalSpent : 0}" type="currency"/>
                                </strong>
                            </span>
                        </div>

                            <%-- Thẻ 3: S-MEM (Cao cấp) --%>
                        <div class="rank-card locked">
                            <span class="rank-badge" style="background-color: #f9e79f; color: #f1c40f;">S-MEM</span>
                            <div style="text-align: center;">
                                <i class="fa-solid fa-lock rank-icon"></i>
                            </div>
                            <p class="rank-user-name">Thành viên Vàng</p>
                            <span class="rank-meta">Yêu cầu: 10.000.000đ</span>
                        </div>
                    </div>

                        <%-- THANH PROGRESS BAR --%>
                    <div class="progress-bar-container">
                        <div class="progress-bar-track">
                                <%-- Tính % tiến trình (Giả sử max mốc là 10tr) --%>
                            <c:set var="progressPercent" value="${(totalSpent / 10000000) * 100}" />
                            <c:if test="${progressPercent > 100}"><c:set var="progressPercent" value="100"/></c:if>

                            <div class="progress-bar-fill" style="width: ${progressPercent}%;"></div>
                        </div>
                        <div class="progress-steps">
                            <span class="step-dot complete"><i class="fa-solid fa-check"></i></span>
                            <span class="step-dot ${totalSpent >= 3000000 ? 'complete' : ''}"></span>
                            <span class="step-dot ${totalSpent >= 10000000 ? 'complete' : ''}"></span>
                        </div>
                    </div>
                </div>

                <div class="content-block">
                    <h3>Ưu đãi của bạn (S-NULL)</h3>
                    <c:choose>
                        <c:when test="${totalSpent < 3000000}">
                            <div class="empty-state">
                                <i class="fa-solid fa-gift"></i>
                                <p>Hiện chưa có ưu đãi đặc biệt cho hạng thành viên mới.</p>
                                <a href="<c:url value='/home'/>" class="btn-link">Mua sắm để thăng hạng</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <ul class="condition-list">
                                <li>
                                    <i class="fa-solid fa-tag"></i>
                                    <span>Giảm giá <strong>2%</strong> cho mọi đơn hàng phụ kiện.</span>
                                </li>
                                <li>
                                    <i class="fa-solid fa-truck-fast"></i>
                                    <span>Miễn phí vận chuyển cho đơn hàng từ 500k.</span>
                                </li>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="content-block">
                    <h3>Điều kiện thăng cấp</h3>
                    <ul class="condition-list">
                        <li>
                            <i class="fa-solid fa-gem"></i>
                            <span><strong>S-NEW (Bạc):</strong> Tổng chi tiêu tích lũy đạt 3.000.000đ.</span>
                        </li>
                        <li>
                            <i class="fa-solid fa-crown"></i>
                            <span><strong>S-MEM (Vàng):</strong> Tổng chi tiêu tích lũy đạt 10.000.000đ.</span>
                        </li>
                    </ul>
                </div>

            </div>
        </div>
    </c:if>

    <c:if test="${empty sessionScope.user}">
        <div class="login-prompt-card">
            <p>Vui lòng đăng nhập để xem hạng thành viên.</p>
            <a href="<c:url value='/login'/>" class="btn">Đi đến trang đăng nhập</a>
        </div>
    </c:if>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />