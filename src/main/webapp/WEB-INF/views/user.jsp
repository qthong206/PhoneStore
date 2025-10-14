<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />
<link rel="stylesheet" href="<c:url value='/css/user.css'/>">

<main class="container">
    <div class="user-profile-container">
        <div class="user-profile-card">
            <h2>Thông tin tài khoản</h2>

            <%-- Kiểm tra nếu người dùng đã đăng nhập --%>
            <c:if test="${not empty sessionScope.user}">
                <div class="profile-info">
                    <p><strong>Họ và tên:</strong> ${sessionScope.user.fullName}</p>
                    <p><strong>Tên đăng nhập:</strong> ${sessionScope.user.username}</p>
                    <p><strong>Email:</strong> ${sessionScope.user.email}</p>
                </div>
                <div class="profile-actions">
                    <a href="#" class="btn">Chỉnh sửa thông tin</a>
                    <a href="#" class="btn">Lịch sử đơn hàng</a>
                    <a href="<c:url value='/logout'/>" class="btn btn-logout">Đăng xuất</a>
                </div>
            </c:if>

            <%-- Hiển thị nếu người dùng chưa đăng nhập --%>
            <c:if test="${empty sessionScope.user}">
                <p class="login-prompt">Vui lòng đăng nhập để xem thông tin tài khoản.</p>
                <a href="<c:url value='/login'/>" class="btn">Đi đến trang đăng nhập</a>
            </c:if>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />