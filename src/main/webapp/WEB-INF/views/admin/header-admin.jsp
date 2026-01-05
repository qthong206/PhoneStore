<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<header class="admin-header">
    <div class="header-left">
        <h2 class="page-title">Quản Trị Hệ Thống</h2>
    </div>

    <div class="header-right">
        <c:if test="${not empty sessionScope.user}">
            <div class="user-info">
                <img src="https://ui-avatars.com/api/?name=${sessionScope.user.fullName}&background=random"
                     class="user-avatar" alt="Avatar">

                <div class="user-details">
                    <span class="user-name">${sessionScope.user.fullName}</span>
                    <span class="user-role">Administrator</span>
                </div>

                <a href="${pageContext.request.contextPath}/logout"
                   class="btn-logout-circle"
                   title="Đăng xuất">
                    <i class="fa-solid fa-power-off"></i>
                </a>
            </div>
        </c:if>
    </div>
</header>