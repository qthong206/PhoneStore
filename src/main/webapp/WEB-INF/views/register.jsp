<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="pageTitle" value="Đăng ký tài khoản" scope="request"/>
<c:set var="pageCss" value="register.css" scope="request"/>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<main class="container">

    <div class="register-container">
        <div class="register-header">
            <i class="fa-solid fa-user-plus register-icon"></i>
            <h2>Đăng ký thành viên</h2>
        </div>

        <%-- (Mạng xã hội giữ nguyên) --%>
        <div class="social-login">
            <a href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile&redirect_uri=http://localhost:8080/PhoneStore_war_exploded/login-google&response_type=code&client_id=59981091402-upstrb9sq0tm8umk795aonl5ha1pivat.apps.googleusercontent.com&approval_prompt=force" class="social-btn google">
                <img src="https://upload.wikimedia.org/wikipedia/commons/c/c1/Google_%22G%22_logo.svg" alt="Google logo">
                Google
            </a>
            <a href="https://www.facebook.com/v19.0/dialog/oauth?client_id=844269028190324&redirect_uri=http://localhost:8080/PhoneStore_war_exploded/login-facebook&scope=email,public_profile" class="social-btn facebook">
                <img src="https://upload.wikimedia.org/wikipedia/commons/b/b8/2021_Facebook_icon.svg" alt="Facebook logo">
                Facebook
            </a>
        </div>

        <div class="form-separator"><span>Hoặc điền thông tin sau</span></div>
        <%-- FORM ĐĂNG KÝ CHÍNH --%>
        <form action="<c:url value='/register'/>" method="post" id="register-form">

            <%-- =================================== --%>
            <%-- KHỐI HIỂN THỊ LỖI (MỚI) --%>
            <%-- =================================== --%>
            <c:if test="${not empty errorMessage}">
                <div class="register-error-box">
                    <i class="fa-solid fa-triangle-exclamation"></i>
                    <span>${errorMessage}</span>
                </div>
            </c:if>

            <%-- THÔNG TIN CÁ NHÂN (THÊM "value") --%>
            <h3>Thông tin cá nhân</h3>
            <div class="form-grid">
                <div class="form-group">
                    <label for="fullName">Họ và tên</label>
                    <input type="text" id="fullName" name="fullName" class="form-control" placeholder="Nhập họ và tên" required
                           value="${oldFullName}">
                </div>
                <div class="form-group">
                    <label for="dob">Ngày sinh</label>
                    <input type="text" id="dob" name="dob" class="form-control" placeholder="dd/mm/yyyy" onfocus="(this.type='date')" onblur="(this.type='text')">
                </div>
                <div class="form-group">
                    <label for="phone">Số điện thoại</label>
                    <input type="tel" id="phone" name="phone" class="form-control" placeholder="Nhập số điện thoại" required
                           value="${oldPhone}">
                </div>
                <div class="form-group">
                    <label for="email">Email <span class="label-note">(Không bắt buộc)</span></label>
                    <input type="email" id="email" name="email" class="form-control" placeholder="Nhập email của bạn"
                           value="${oldEmail}">
                </div>
            </div>

            <%-- (Mật khẩu và các phần còn lại giữ nguyên) --%>
            <h3>Tạo mật khẩu</h3>
            <div class="form-grid">
                <div class="form-group password-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Nhập mật khẩu của bạn" required>
                    <button type="button" class="btn-toggle-password"><i class="fa-solid fa-eye"></i></button>
                </div>
                <div class="form-group password-group">
                    <label for="confirmPassword">Nhập lại mật khẩu</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="Nhập lại mật khẩu của bạn" required>
                    <button type="button" class="btn-toggle-password"><i class="fa-solid fa-eye"></i></button>
                </div>
            </div>
            <p class="password-note">
                <i class="fa-solid fa-info-circle"></i> Mật khẩu tối thiểu 6 ký tự, có ít nhất 1 chữ số và 1 chữ cái.
            </p>
            <div class="form-group checkbox-group">
                <input type="checkbox" id="newsletter" name="newsletter">
                <label for="newsletter">Đăng ký nhận tin khuyến mãi từ PhoneStore</label>
            </div>
            <p classs="terms-note">
                Bằng việc Đăng ký, bạn đã đọc và đồng ý với
                <a href="#">Điều khoản sử dụng</a> và
                <a href="#">Chính sách bảo mật</a> của PhoneStore.
            </p>
            <div class="register-actions">
                <a href="<c:url value='/login'/>" class="back-to-login">
                    <i class="fa-solid fa-chevron-left"></i> Quay lại đăng nhập
                </a>
                <button type="submit" class="btn btn-register">Hoàn tất đăng ký</button>
            </div>

        </form>
    </div>
</main>

<script src="<c:url value='/js/register.js'/>"></script>
<jsp:include page="/WEB-INF/layout/footer.jsp" />