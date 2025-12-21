<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">
    <div class="header-left">
        <a href="${pageContext.request.contextPath}/home" title="Về trang giao diện người dùng">
            <img src="<c:url value='/images/logo.png'/>" alt="PhoneStore Logo" class="header-logo">
        </a>
    </div>

    <div class="header-right">
        <c:if test="${not empty sessionScope.user}">
            <span class="user-greeting">
                Xin chào, <strong>${sessionScope.user.fullName}</strong>
            </span>
            <a href="${pageContext.request.contextPath}/logout" class="btn-logout">
                <i class="fa-solid fa-right-from-bracket"></i> Đăng xuất
            </a>
        </c:if>
    </div>
</header>