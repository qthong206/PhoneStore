<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />
<link rel="stylesheet" href="<c:url value='/css/login.css'/>">

<main class="container">
    <div class="login-container">
        <form action="<c:url value='/login'/>" method="post" class="login-form">
            <h2>Đăng nhập</h2>

            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>

            <div class="form-group">
                <label for="username">Số điện thoại</label>
                <%-- Giữ name="username" để servlet cũ của bạn vẫn hoạt động --%>
                <input type="text" id="username" name="username" placeholder="Nhập số điện thoại của bạn" required>
            </div>

            <div class="form-group">
                <label for="password">Mật khẩu</label>
                <div class="password-wrapper">
                    <input type="password" id="password" name="password" placeholder="Nhập mật khẩu của bạn" required>
                    <%-- Icon con mắt --%>
                    <i class="fa-solid fa-eye-slash" id="togglePassword"></i>
                </div>
            </div>

            <button type="submit" class="btn btn-login">Đăng nhập</button>

            <div class="forgot-password">
                <a href="#">Quên mật khẩu?</a>
            </div>

            <div class="divider">
                <span>Hoặc đăng nhập bằng</span>
            </div>

            <div class="social-login">
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile&redirect_uri=http://localhost:8080/PhoneStore_war_exploded/login-google&response_type=code&client_id=59981091402-upstrb9sq0tm8umk795aonl5ha1pivat.apps.googleusercontent.com&approval_prompt=force" class="social-btn google">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/c/c1/Google_%22G%22_logo.svg" alt="Google logo">
                    Google
                </a>
                <a href="https://www.facebook.com/v19.0/dialog/oauth?client_id=844269028190324&redirect_uri=http://localhost:8080/PhoneStore_war_exploded/login-facebook&scope=public_profile" class="social-btn facebook">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/b/b8/2021_Facebook_icon.svg" alt="Facebook logo">
                    Facebook
                </a>
            </div>

            <div class="register-link">
                Bạn chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký ngay</a>
            </div>
        </form>
    </div>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />

<%-- Script để xử lý bật/tắt hiển thị mật khẩu --%>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const togglePassword = document.getElementById('togglePassword');
        const password = document.getElementById('password');

        if (togglePassword) {
            togglePassword.addEventListener('click', function () {
                // Chuyển đổi type của input
                const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
                password.setAttribute('type', type);

                // Chuyển đổi icon
                this.classList.toggle('fa-eye-slash');
                this.classList.toggle('fa-eye');
            });
        }
    });
</script>