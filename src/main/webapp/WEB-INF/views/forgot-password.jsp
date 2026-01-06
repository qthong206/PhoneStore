<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/layout/header.jsp" />

<main class="container">
    <div class="register-container" style="max-width: 450px; margin: 50px auto;">
        <div class="register-header">
            <i class="fa-solid fa- fingerprints register-icon"></i>
            <h2>Quên mật khẩu?</h2>
            <p style="font-size: 0.9rem; color: #666;">Nhập email để nhận mật khẩu mới</p>
        </div>

        <form action="forgot-password" method="post" id="forgot-form">
            <c:if test="${not empty errorMessage}">
                <div class="register-error-box"><span>${errorMessage}</span></div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="register-error-box" style="background:#d4edda; color:#155724; border-color:#c3e6cb;">
                    <span>${successMessage}</span>
                </div>
            </c:if>

            <div class="form-group">
                <label>Email đăng ký</label>
                <input type="email" name="email" class="form-control" placeholder="abc@gmail.com" required>
            </div>

            <div class="register-actions" style="border:none; padding:0;">
                <a href="login" class="back-to-login"><i class="fa-solid fa-arrow-left"></i> Quay lại</a>
                <button type="submit" class="btn btn-register">Gửi mật khẩu</button>
            </div>
        </form>
    </div>
</main>
<jsp:include page="/WEB-INF/layout/footer.jsp" />