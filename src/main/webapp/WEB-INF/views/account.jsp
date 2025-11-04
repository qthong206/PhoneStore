<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />
<link rel="stylesheet" href="<c:url value='/css/user.css'/>">

<main class="container user-dashboard">

    <%-- Chỉ hiển thị dashboard nếu người dùng ĐÃ đăng nhập --%>
    <c:if test="${not empty sessionScope.user}">

        <%-- THANH THÔNG TIN TRÊN CÙNG --%>
        <div class="user-page-header">
            <div class="header-info-block user-details-block">
                <div class="avatar">
                    <i class="fa-solid fa-user"></i>
                </div>
                <div class="info-text">
                    <strong>${sessionScope.user.fullName}</strong>
                    <p>${sessionScope.user.phoneNumber}</p>
                    <span>Hạng: Thành viên mới</span>
                </div>
            </div>
            <div class="header-info-block">
                <div class="icon-wrapper">
                    <i class="fa-solid fa-receipt"></i>
                </div>
                <div class="info-text">
                    <strong>0</strong>
                    <p>Tổng số đơn hàng đã mua</p>
                </div>
            </div>
            <div class="header-info-block">
                <div class="icon-wrapper">
                    <i class="fa-solid fa-sack-dollar"></i>
                </div>
                <div class="info-text">
                    <strong>0đ</strong>
                    <p>Tổng tiền tích lũy</p>
                </div>
            </div>
        </div>

        <%-- PHẦN THÂN: LAYOUT 2 CỘT --%>
        <div class="dashboard-body">

                <%-- MENU ĐIỀU HƯỚNG BÊN TRÁI (Đã cập nhật đầy đủ) --%>
            <nav class="user-nav">
                <ul>
                    <li><a href="<c:url value='/user'/>" class="${currentView == 'overview' ? 'active' : ''}"><i class="fa-solid fa-gauge"></i><span>Tổng quan</span></a></li>
                    <li><a href="<c:url value='/order'/>" class="${currentView == 'order' ? 'active' : ''}"><i class="fa-solid fa-clock-rotate-left"></i><span>Lịch sử mua hàng</span></a></li>
                    <li><a href="<c:url value='/warranty'/>" class="${currentView == 'warranty' ? 'active' : ''}"><i class="fa-solid fa-shield-halved"></i><span>Tra cứu bảo hành</span></a></li>
                    <li><a href="<c:url value='/rank'/>" class="${currentView == 'rank' ? 'active' : ''}"><i class="fa-solid fa-gem"></i><span>Hạng thành viên</span></a></li>
                    <li><a href="<c:url value='/account'/>" class="${currentView == 'account' ? 'active' : ''}"><i class="fa-solid fa-user-pen"></i><span>Thông tin tài khoản</span></a></li>
                    <li><a href="<c:url value='/policy'/>" class="${currentView == 'policy' ? 'active' : ''}"><i class="fa-solid fa-book"></i><span>Chính sách bảo hành</span></a></li>
                    <li><a href="<c:url value='/support'/>" class="${currentView == 'support' ? 'active' : ''}"><i class="fa-solid fa-headset"></i><span>Góp ý - Phản hồi - Hỗ trợ</span></a></li>
                    <li><a href="<c:url value='/terms'/>" class="${currentView == 'terms' ? 'active' : ''}"><i class="fa-solid fa-file-contract"></i><span>Điều khoản sử dụng</span></a></li>
                    <li><a href="<c:url value='/logout'/>" class="logout-link"><i class="fa-solid fa-right-from-bracket"></i><span>Đăng xuất</span></a></li>
                </ul>
            </nav>

                <%-- NỘI DUNG CHÍNH CỦA TRANG "THÔNG TIN TÀI KHOẢN" --%>
            <div class="user-content">

                <div class="content-block">
                    <div class="content-block-header">
                        <h3>Thông tin cá nhân</h3>
                            <%-- SỬA LẠI LINK NÀY: Thêm id để JS bắt sự kiện --%>
                        <a href="javascript:void(0);" id="openUpdateInfoModalBtn" class="btn-link"><i class="fa-solid fa-pen"></i> Cập nhật</a>
                    </div>
                    <div class="info-grid">
                        <div class="info-item">
                            <span>Họ và tên:</span>
                            <strong>${sessionScope.user.fullName}</strong>
                        </div>
                        <div class="info-item">
                            <span>Số điện thoại:</span>
                            <strong>${sessionScope.user.phoneNumber}</strong>
                        </div>
                        <div class="info-item">
                            <span>Giới tính:</span>
                            <strong>-</strong>
                        </div>
                        <div class="info-item">
                            <span>Email:</span>
                            <strong>${sessionScope.user.email}</strong>
                        </div>
                        <div class="info-item">
                            <span>Ngày sinh:</span>
                            <strong>-</strong>
                        </div>
                        <div class="info-item">
                            <span>Địa chỉ mặc định:</span>
                            <strong>-</strong>
                        </div>
                    </div>
                </div>

                <div class="content-block">
                    <div class="content-block-header">
                        <h3>Sổ địa chỉ</h3>
                        <a href="javascript:void(0);" id="openAddModalBtn" class="btn-link"><i class="fa-solid fa-plus"></i> Thêm địa chỉ</a>
                    </div>

                    <div class="address-card">
                        <div class="address-card-main">
                            <span class="address-type-badge">Nhà</span>
                            <strong class="address-name">${sessionScope.user.fullName}</strong>
                            <span class="address-phone">${sessionScope.user.phoneNumber}</span>
                            <p class="address-full">1, Thị trấn Núi Sập, Huyện Thoại Sơn, An Giang</p>
                        </div>
                        <div class="address-card-actions">
                            <a href="javascript:void(0);">Xoá</a>
                            <a href="javascript:void(0);" class="openUpdateModalBtn">Cập nhật</a>
                        </div>
                    </div>
                </div>

                <div class="content-block">
                    <div class="content-block-header">
                        <h3>Mật khẩu</h3>
                        <a href="#" class="btn-link"><i class="fa-solid fa-pen-to-square"></i> Thay đổi mật khẩu</a>
                    </div>
                    <div class="info-item">
                        <span>Cập nhật lần cuối lúc:</span>
                        <strong>14/10/2025 20:20</strong>
                    </div>
                </div>

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

    <%-- Hiển thị nếu người dùng CHƯA đăng nhập --%>
    <c:if test="${empty sessionScope.user}">
        <div class="login-prompt-card">
            <p>Vui lòng đăng nhập để xem thông tin tài khoản.</p>
            <a href="<c:url value='/login'/>" class="btn">Đi đến trang đăng nhập</a>
        </div>
    </c:if>
</main>

<%-- =================================================================== --%>
<%-- MODAL (POPUP) CẬP NHẬT THÔNG TIN (MỚI)                            --%>
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
                    <input type="text" id="update-fullname" value="${sessionScope.user.fullName}">
                </div>
                <div class="form-group">
                    <label for="update-gender">Giới tính</label>
                    <select id="update-gender" class="form-control-select">
                        <option value="">Chọn giới tính</option>
                        <option value="male">Nam</option>
                        <option value="female">Nữ</option>
                        <option value="other">Khác</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="update-dob">Ngày sinh</label>
                    <div class="form-group-icon">
                        <input type="text" id="update-dob" value="02/06/2004" placeholder="DD/MM/YYYY">
                        <i class="fa-solid fa-calendar-days"></i>
                    </div>
                </div>
                <div class="form-group">
                    <label for="update-phone">Số điện thoại</label>
                    <input type="text" id="update-phone" value="${sessionScope.user.phoneNumber}" readonly disabled>
                </div>
                <div class="form-group">
                    <label for="update-email">Email</label>
                    <input type="text" id="update-email" value="${sessionScope.user.email}" readonly disabled>
                </div>
                <div class="form-group">
                    <label for="update-default-address">Địa chỉ mặc định *</label>
                    <select id="update-default-address" class="form-control-select">
                        <option value="">Chọn địa chỉ mặc định</option>
                        <option value="1" selected>t: 1, Thị trấn Núi Sập, Huyện Thoại Sơn, An Giang</option>
                    </select>
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
<%-- MODAL (POPUP) THÊM ĐỊA CHỈ (ĐÃ CÓ SẴN)                               --%>
<%-- =================================================================== --%>
<div class="modal-overlay" id="addAddressModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Thêm địa chỉ</h3>
            <button class="close-modal-btn">&times;</button>
        </div>
        <form action="<c:url value='/add-address'/>" method="POST">
            <div class="modal-body">
                <h4>Địa chỉ nhận hàng</h4>
                <div class="form-group">
                    <label for="add-province">Tỉnh/Thành phố</label>
                    <select id="add-province" class="form-control-select">
                        <option value="">Chọn Tỉnh/Thành phố</option>
                        <option value="ag">An Giang</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="add-district">Quận/Huyện</label>
                    <select id="add-district" class="form-control-select">
                        <option value="">Chọn Quận/Huyện</option>
                        <option value="ts">Huyện Thoại Sơn</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="add-ward">Phường/Xã</label>
                    <select id="add-ward" class="form-control-select">
                        <option value="">Chọn Phường/Xã</option>
                        <option value="ns">Thị trấn Núi Sập</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="add-street">Địa chỉ nhà</label>
                    <input type="text" id="add-street" placeholder="Nhập địa chỉ nhà">
                </div>
                <div class="form-group">
                    <label for="add-nickname">Đặt tên gợi nhớ</label>
                    <input type="text" id="add-nickname" placeholder="Đặt tên gợi nhớ">
                </div>
                <div class="form-group">
                    <label>Loại địa chỉ</label>
                    <div class="form-radio-group">
                        <button type="button" class="form-radio-btn active">Nhà</button>
                        <button type="button" class="form-radio-btn">Văn phòng</button>
                    </div>
                </div>
                <div class="form-group form-group-toggle">
                    <label for="add-default">Đặt làm địa chỉ mặc định</label>
                    <label class="form-toggle">
                        <input type="checkbox" id="add-default">
                        <span class="slider"></span>
                    </label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-full">Thêm địa chỉ</button>
            </div>
        </form>
    </div>
</div>

<%-- =================================================================== --%>
<%-- MODAL (POPUP) CẬP NHẬT ĐỊA CHỈ (ĐÃ CÓ SẴN)                           --%>
<%-- =================================================================== --%>
<div class="modal-overlay" id="updateAddressModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Cập nhật địa chỉ</h3>
            <button class="close-modal-btn">&times;</button>
        </div>
        <form action="<c:url value='/update-address'/>" method="POST">
            <div class="modal-body">
                <h4>Địa chỉ nhận hàng</h4>
                <div class="form-group">
                    <label for="update-province">Tỉnh/Thành phố</label>
                    <select id="update-province" class="form-control-select">
                        <option value="ag" selected>An Giang</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="update-district">Quận/Huyện</label>
                    <select id="update-district" class="form-control-select">
                        <option value="ts" selected>Huyện Thoại Sơn</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="update-ward">Phường/Xã</label>
                    <select id="update-ward" class="form-control-select">
                        <option value="ns" selected>Thị trấn Núi Sập</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="update-street">Địa chỉ nhà</label>
                    <input type="text" id="update-street" value="1">
                </div>
                <div class="form-group">
                    <label for="update-nickname">Đặt tên gợi nhớ</label>
                    <input type="text" id="update-nickname" value="t">
                </div>
                <div class="form-group">
                    <label>Loại địa chỉ</label>
                    <div class="form-radio-group">
                        <button type="button" class="form-radio-btn active">Nhà</button>
                        <button type="button" class="form-radio-btn">Văn phòng</button>
                    </div>
                </div>
                <div class="form-group form-group-toggle">
                    <label for="update-default">Đặt làm địa chỉ mặc định</label>
                    <label class="form-toggle">
                        <input type="checkbox" id="update-default">
                        <span class="slider"></span>
                    </label>
                </div>
            </div>
            <div class="modal-footer modal-footer-split">
                <button type="button" class="btn btn-outline-danger">Xoá địa chỉ</button>
                <button type="submit" class="btn">Cập nhật địa chỉ</button>
            </div>
        </form>
    </div>
</div>


<jsp:include page="/WEB-INF/layout/footer.jsp" />

<%-- =================================================================== --%>
<%-- SCRIPT ĐIỀU KHIỂN CẢ 3 MODAL                                       --%>
<%-- =================================================================== --%>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Lấy các modal
        const addModal = document.getElementById('addAddressModal');
        const updateModal = document.getElementById('updateAddressModal');
        const updateInfoModal = document.getElementById('updateInfoModal'); // Modal mới

        // Lấy các nút MỞ modal
        const openAddBtn = document.getElementById('openAddModalBtn');
        const openUpdateBtns = document.querySelectorAll('.openUpdateModalBtn');
        const openUpdateInfoBtn = document.getElementById('openUpdateInfoModalBtn'); // Nút mới

        // Lấy tất cả các nút ĐÓNG modal
        const closeModalBtns = document.querySelectorAll('.close-modal-btn');

        // Hàm hiển thị modal
        function showModal(modal) {
            if(modal) modal.style.display = 'flex';
        }

        // Hàm ẩn modal
        function hideModal(modal) {
            if(modal) modal.style.display = 'none';
        }

        // Gán sự kiện cho nút "Thêm địa chỉ"
        if(openAddBtn) {
            openAddBtn.addEventListener('click', () => showModal(addModal));
        }

        // Gán sự kiện cho các nút "Cập nhật" (địa chỉ)
        openUpdateBtns.forEach(btn => {
            btn.addEventListener('click', () => showModal(updateModal));
        });

        // Gán sự kiện cho nút "Cập nhật" (thông tin)
        if(openUpdateInfoBtn) {
            openUpdateInfoBtn.addEventListener('click', () => showModal(updateInfoModal));
        }

        // Gán sự kiện cho các nút "X" (nút đóng)
        closeModalBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                hideModal(addModal);
                hideModal(updateModal);
                hideModal(updateInfoModal); // Thêm modal mới
            });
        });

        // Đóng modal khi click ra ngoài vùng (click vào overlay)
        window.addEventListener('click', function(event) {
            if (event.target === addModal) hideModal(addModal);
            if (event.target === updateModal) hideModal(updateModal);
            if (event.target === updateInfoModal) hideModal(updateInfoModal); // Thêm modal mới
        });
    });
</script>