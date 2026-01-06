<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/layout/header.jsp" />
<link rel="stylesheet" href="<c:url value='/css/static-page.css'/>">

<main class="container">
    <div class="static-content-wrapper">
        <div class="static-header">
            <h1><i class="fa-solid fa-shield-halved"></i> Chính Sách Bảo Mật</h1>
            <p>PhoneStore cam kết bảo vệ tuyệt đối thông tin cá nhân của khách hàng.</p>
        </div>
        <div class="static-body">
            <h3><i class="fa-solid fa-user-check"></i> 1. Thu thập thông tin</h3>
            <p>Chúng tôi thu thập thông tin khi bạn đăng ký tài khoản, đặt hàng hoặc đăng nhập qua Google/Facebook. Thông tin bao gồm: Họ tên, Email, Số điện thoại và Địa chỉ giao hàng.</p>

            <h3><i class="fa-solid fa-lock"></i> 2. Mục đích sử dụng</h3>
            <p>Thông tin của khách hàng chỉ được sử dụng trong phạm vi nội bộ nhằm:</p>
            <ul>
                <li>Xử lý đơn hàng và giao hàng tận nơi.</li>
                <li>Gửi thông báo cập nhật trạng thái đơn hàng qua Email.</li>
                <li>Hỗ trợ kỹ thuật và giải quyết khiếu nại nhanh chóng.</li>
                <li>Gửi mã khuyến mãi (nếu bạn có đăng ký nhận newsletter).</li>
            </ul>

            <h3><i class="fa-solid fa-server"></i> 3. Bảo mật dữ liệu</h3>
            <p>Mật khẩu của bạn được mã hóa bằng công nghệ <b>BCrypt</b> một chiều. Chúng tôi cam kết không chia sẻ dữ liệu cho bất kỳ bên thứ ba nào, ngoại trừ các đơn vị vận chuyển đối tác.</p>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/layout/footer.jsp" />