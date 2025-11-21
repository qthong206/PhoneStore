<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="vi_VN"/>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<%-- Load CSS --%>
<link rel="stylesheet" href="<c:url value='/css/user-layout.css'/>">
<link rel="stylesheet" href="<c:url value='/css/account.css'/>">

<main class="container user-dashboard">
    <c:if test="${not empty sessionScope.user}">

        <%-- 1. User Info Bar --%>
        <jsp:include page="/WEB-INF/views/user/components/user-info-bar.jsp" />

        <div class="dashboard-body">
                <%-- 2. Sidebar --%>
            <jsp:include page="/WEB-INF/views/user/components/user-sidebar.jsp" />

                <%-- 3. Nội dung chính --%>
            <div class="user-content">

                    <%-- KHỐI 1: THÔNG TIN CÁ NHÂN --%>
                <div class="content-block">
                    <div class="content-block-header">
                        <h3>Thông tin cá nhân</h3>
                        <a href="#" id="openUpdateInfoModalBtn" class="btn-link">
                            <i class="fa-solid fa-pen"></i> Cập nhật
                        </a>
                    </div>
                    <div class="info-grid">
                        <div class="info-item"><span>Họ và tên:</span> <strong>${sessionScope.user.fullName}</strong></div>
                        <div class="info-item"><span>Số điện thoại:</span> <strong>${sessionScope.user.phoneNumber}</strong></div>
                        <div class="info-item"><span>Giới tính:</span> <strong>-</strong></div>
                        <div class="info-item"><span>Email:</span> <strong>${sessionScope.user.email}</strong></div>
                        <div class="info-item"><span>Ngày sinh:</span> <strong>-</strong></div>
                        <div class="info-item"><span>Địa chỉ mặc định:</span> <strong>-</strong></div>
                    </div>
                </div>

                    <%-- KHỐI 2: SỔ ĐỊA CHỈ --%>
                <div class="content-block">
                    <div class="content-block-header">
                        <h3>Sổ địa chỉ</h3>
                        <a href="#" id="openAddModalBtn" class="btn-link">
                            <i class="fa-solid fa-plus"></i> Thêm địa chỉ
                        </a>
                    </div>

                        <%-- (Ví dụ thẻ địa chỉ tĩnh - sau này dùng c:forEach) --%>
                    <div class="address-card">
                        <div class="address-card-main">
                            <span class="address-type-badge">Nhà</span>
                            <strong class="address-name">${sessionScope.user.fullName}</strong>
                            <span class="address-phone">${sessionScope.user.phoneNumber}</span>
                            <p class="address-full">1, Thị trấn Núi Sập, Huyện Thoại Sơn, An Giang</p>
                        </div>
                        <div class="address-card-actions">
                            <a href="#">Xoá</a>
                            <a href="#" class="openUpdateModalBtn">Cập nhật</a>
                        </div>
                    </div>
                </div>

                    <%-- KHỐI 3: TÀI KHOẢN LIÊN KẾT --%>
                <div class="content-block">
                    <h3>Tài khoản liên kết</h3>
                    <div class="linked-account-item">
                        <div class="linked-info">
                            <i class="fa-brands fa-google"></i>
                            <strong>Google</strong>
                            <span>Đã liên kết</span>
                        </div>
                        <a href="#" class="btn-link-danger"><i class="fa-solid fa-link-slash"></i> Huỷ liên kết</a>
                    </div>
                    <div class="linked-account-item">
                        <div class="linked-info">
                            <i class="fa-brands fa-facebook"></i>
                            <strong>Facebook</strong>
                        </div>
                        <a href="#" class="btn-link"><i class="fa-solid fa-link"></i> Liên kết</a>
                    </div>
                </div>

            </div>
        </div>
    </c:if>

    <c:if test="${empty sessionScope.user}">
        <div class="login-prompt-card">
            <p>Vui lòng đăng nhập để xem thông tin tài khoản.</p>
            <a href="<c:url value='/login'/>" class="btn">Đi đến trang đăng nhập</a>
        </div>
    </c:if>
</main>

<%-- =================================================================== --%>
<%-- MODAL 1: CẬP NHẬT THÔNG TIN CÁ NHÂN                                --%>
<%-- =================================================================== --%>
<div class="modal-overlay" id="updateInfoModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Cập nhật thông tin cá nhân</h3>
            <button class="close-modal-btn">&times;</button>
        </div>
        <form action="<c:url value='/update-account'/>" method="POST">
            <div class="modal-body">
                <div class="form-group">
                    <label for="update-fullname">Họ và tên</label>
                    <input type="text" id="update-fullname" name="fullName" value="${sessionScope.user.fullName}">
                </div>
                <div class="form-group">
                    <label for="update-gender">Giới tính</label>
                    <select id="update-gender" name="gender" class="form-control-select">
                        <option value="">Chọn giới tính</option>
                        <option value="male">Nam</option>
                        <option value="female">Nữ</option>
                        <option value="other">Khác</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="update-phone">Số điện thoại</label>
                    <input type="text" id="update-phone" value="${sessionScope.user.phoneNumber}" readonly disabled>
                </div>
                <div class="form-group">
                    <label for="update-email">Email</label>
                    <input type="text" id="update-email" value="${sessionScope.user.email}" readonly disabled>
                </div>
            </div>
            <div class="modal-footer modal-footer-split">
                <button type="button" class="btn btn-outline">Thiết lập lại</button>
                <button type="submit" class="btn">Cập nhật thông tin</button>
            </div>
        </form>
    </div>
</div>

<%-- =================================================================== --%>
<%-- MODAL 2: THÊM ĐỊA CHỈ                                              --%>
<%-- =================================================================== --%>
<div class="modal-overlay" id="addAddressModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Thêm địa chỉ</h3>
            <button class="close-modal-btn">&times;</button>
        </div>
        <form action="<c:url value='/add-address'/>" method="POST">
            <div class="modal-body">
                <div class="form-group">
                    <label for="add-street">Địa chỉ nhà</label>
                    <input type="text" id="add-street" placeholder="Nhập địa chỉ nhà">
                </div>
                <div class="form-group">
                    <label>Loại địa chỉ</label>
                    <div class="form-radio-group">
                        <button type="button" class="form-radio-btn active">Nhà</button>
                        <button type="button" class="form-radio-btn">Văn phòng</button>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-full">Thêm địa chỉ</button>
            </div>
        </form>
    </div>
</div>

<%-- =================================================================== --%>
<%-- MODAL 3: CẬP NHẬT ĐỊA CHỈ                                          --%>
<%-- =================================================================== --%>
<div class="modal-overlay" id="updateAddressModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Cập nhật địa chỉ</h3>
            <button class="close-modal-btn">&times;</button>
        </div>
        <form action="<c:url value='/update-address'/>" method="POST">
            <div class="modal-body">
                <div class="form-group">
                    <label for="update-street">Địa chỉ nhà</label>
                    <input type="text" id="update-street" value="1, Thị trấn Núi Sập...">
                </div>
            </div>
            <div class="modal-footer modal-footer-split">
                <button type="button" class="btn btn-outline-danger">Xoá</button>
                <button type="submit" class="btn">Lưu thay đổi</button>
            </div>
        </form>
    </div>
</div>

<%-- Load JS xử lý Popup --%>
<script src="<c:url value='/js/account.js'/>"></script>

<jsp:include page="/WEB-INF/layout/footer.jsp" />