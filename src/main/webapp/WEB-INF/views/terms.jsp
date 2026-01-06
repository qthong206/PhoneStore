<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/layout/header.jsp" />
<link rel="stylesheet" href="<c:url value='/css/static-page.css'/>">

<main class="container">
    <div class="static-content-wrapper">
        <div class="static-header">
            <h1><i class="fa-solid fa-file-contract"></i> Điều Khoản Dịch Vụ</h1>
            <p>Vui lòng đọc kỹ các quy định trước khi thực hiện giao dịch tại hệ thống.</p>
        </div>
        <div class="static-body">
            <h3><i class="fa-solid fa-check-to-slot"></i> 1. Chấp thuận điều khoản</h3>
            <p>Bằng việc đăng ký tài khoản tại PhoneStore, bạn mặc nhiên đồng ý với các quy định về mua bán, đổi trả và thanh toán của chúng tôi.</p>

            <h3><i class="fa-solid fa-id-card"></i> 2. Trách nhiệm người dùng</h3>
            <p>Bạn phải cung cấp thông tin chính xác khi đặt hàng. Chúng tôi có quyền hủy đơn hàng nếu phát hiện thông tin liên hệ không có thực hoặc mang tính chất giả mạo.</p>

            <h3><i class="fa-solid fa-truck-fast"></i> 3. Quy định giao hàng</h3>
            <p>PhoneStore hỗ trợ giao hàng toàn quốc. Khách hàng được quyền kiểm tra sản phẩm (đồng kiểm) trước khi thanh toán. Mọi sự cố vỡ hỏng do vận chuyển sẽ được chúng tôi xử lý trong vòng 24h.</p>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/layout/footer.jsp" />