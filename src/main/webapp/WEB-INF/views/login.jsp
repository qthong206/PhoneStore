<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="pageTitle" value="Đăng Nhập" scope="request"/>
<jsp:include page="/WEB-INF/layout/header.jsp" />

<link rel="stylesheet" href="<c:url value='/css/login.css'/>">

<main class="container">
    <div class="login-container">
        <form action="<c:url value='/login'/>" method="post" class="login-form">
            <h2>Đăng Nhập</h2>
            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>
            <div class="form-group">
                <label for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="btn">Đăng Nhập</button>
            <div class="form-footer">
                <a href="#">Quên mật khẩu?</a>
                <span>|</span>
                <a href="#">Tạo tài khoản mới</a>
            </div>
        </form>
    </div>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />