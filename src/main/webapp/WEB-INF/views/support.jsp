<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/layout/header.jsp" />
<link rel="stylesheet" href="<c:url value='/css/static-page.css'/>">

<main class="container">
    <div class="static-content-wrapper">
        <div class="static-header">
            <h1><i class="fa-solid fa-headset"></i> Trung Tâm Hỗ Trợ</h1>
            <p>Chúng tôi luôn lắng nghe và giải đáp mọi thắc mắc của bạn.</p>
        </div>
        <div class="static-body">
            <div class="faq-item">
                <div class="faq-question">Tôi quên mật khẩu thì phải làm sao?</div>
                <div class="faq-answer">Bạn vui lòng truy cập trang Đăng nhập -> Chọn "Quên mật khẩu". Hệ thống sẽ gửi một mật khẩu mới về Email đăng ký của bạn.</div>
            </div>

            <div class="faq-item">
                <div class="faq-question">Thời gian bảo hành sản phẩm là bao lâu?</div>
                <div class="faq-answer">Tất cả điện thoại tại PhoneStore đều được bảo hành chính hãng 12 tháng và hỗ trợ 1 đổi 1 trong vòng 30 ngày nếu có lỗi từ nhà sản xuất.</div>
            </div>

            <div class="faq-item">
                <div class="faq-question">Làm thế nào để hủy đơn hàng?</div>
                <div class="faq-answer">Bạn có thể tự hủy đơn hàng trong mục "Lịch sử đơn hàng" nếu đơn hàng đang ở trạng thái "Chờ xác nhận". Đối với các trạng thái khác, vui lòng gọi Hotline: 1900-6729.</div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/layout/footer.jsp" />