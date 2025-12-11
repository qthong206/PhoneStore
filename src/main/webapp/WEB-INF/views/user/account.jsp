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

        <%-- 1. USER INFO BAR (Chứa thống kê) --%>
        <%-- File này sẽ nhận biến totalOrders và totalSpent từ Servlet --%>
        <jsp:include page="/WEB-INF/views/user/components/user-info-bar.jsp" />

        <%-- 2. DASHBOARD BODY --%>
        <div class="dashboard-body">

                <%-- Sidebar --%>
            <jsp:include page="/WEB-INF/views/user/components/user-sidebar.jsp" />

                <%-- Content Chính --%>
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
                        <div class="info-column">
                            <div class="info-item"><span>Họ và tên:</span> <strong>${sessionScope.user.fullName}</strong></div>
                            <div class="info-item"><span>Giới tính:</span> <strong>Nam</strong></div>
                            <div class="info-item"><span>Ngày sinh:</span> <strong>01/01/2000</strong></div>
                        </div>
                        <div class="info-column">
                            <div class="info-item"><span>Số điện thoại:</span> <strong>${sessionScope.user.phoneNumber}</strong></div>
                            <div class="info-item"><span>Email:</span> <strong>${sessionScope.user.email}</strong></div>

                            <c:set var="defaultAddrStr" value="Chưa thiết lập"/>
                            <c:forEach var="addr" items="${addressList}">
                                <c:if test="${addr.defaultAddress}">
                                    <c:set var="defaultAddrStr" value="${addr.streetAddress}"/>
                                </c:if>
                            </c:forEach>
                            <div class="info-item"><span>Địa chỉ mặc định:</span> <strong>${defaultAddrStr}</strong></div>
                        </div>
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

                    <c:if test="${empty addressList}">
                        <div class="empty-state">
                            <p>Bạn chưa lưu địa chỉ nào.</p>
                        </div>
                    </c:if>

                    <c:forEach var="addr" items="${addressList}">
                        <div class="address-card ${addr.defaultAddress ? 'default-border' : ''}">
                            <div class="address-card-main">
                                <div class="address-badges">
                                    <span class="address-type-badge">${addr.addressType}</span>
                                    <c:if test="${addr.defaultAddress}">
                                        <span class="address-type-badge badge-default">Mặc định</span>
                                    </c:if>
                                </div>
                                <strong class="address-name">${addr.receiverName}</strong>
                                <span class="address-phone">${addr.phoneNumber}</span>
                                <p class="address-full">${addr.streetAddress}</p>
                            </div>
                            <div class="address-card-actions">
                                <c:if test="${!addr.defaultAddress}">
                                    <a href="<c:url value='/account?action=set-default&id=${addr.id}'/>" class="btn-action-default">Đặt mặc định</a>
                                </c:if>

                                <a href="#" class="openUpdateModalBtn btn-action-edit"
                                   data-id="${addr.id}"
                                   data-name="${addr.receiverName}"
                                   data-phone="${addr.phoneNumber}"
                                   data-street="${addr.streetAddress}"
                                   data-type="${addr.addressType}"
                                   data-default="${addr.defaultAddress}">Cập nhật</a>

                                <a href="<c:url value='/account?action=delete-address&id=${addr.id}'/>"
                                   class="btn-action-delete"
                                   onclick="return confirm('Bạn có chắc chắn muốn xóa địa chỉ này?')">Xoá</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                    <%-- TÀI KHOẢN LIÊN KẾT --%>
                <div class="content-block">
                    <h3>Tài khoản liên kết</h3>
                    <div class="linked-account-item">
                        <div class="linked-info">
                            <i class="fa-brands fa-google" style="color: #DB4437;"></i>
                            <strong>Google</strong>
                            <c:if test="${sessionScope.user.authProvider == 'google'}"><span>Đã liên kết</span></c:if>
                            <c:if test="${sessionScope.user.authProvider != 'google'}"><span style="color: #999;">Chưa liên kết</span></c:if>
                        </div>
                        <c:if test="${sessionScope.user.authProvider == 'google'}">
                            <span class="status-connected"><i class="fa-solid fa-check"></i> Đang dùng</span>
                        </c:if>
                    </div>
                    <div class="linked-account-item">
                        <div class="linked-info">
                            <i class="fa-brands fa-facebook" style="color: #1877F2;"></i>
                            <strong>Facebook</strong>
                            <c:if test="${sessionScope.user.authProvider == 'facebook'}"><span>Đã liên kết</span></c:if>
                            <c:if test="${sessionScope.user.authProvider != 'facebook'}"><span style="color: #999;">Chưa liên kết</span></c:if>
                        </div>
                        <c:if test="${sessionScope.user.authProvider == 'facebook'}">
                            <span class="status-connected"><i class="fa-solid fa-check"></i> Đang dùng</span>
                        </c:if>
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

<%-- MODAL 1: CẬP NHẬT THÔNG TIN --%>
<div class="modal-overlay" id="updateInfoModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Cập nhật thông tin cá nhân</h3>
            <button class="close-modal-btn">&times;</button>
        </div>
        <form action="<c:url value='/account'/>" method="POST">
            <input type="hidden" name="action" value="update-info">
            <div class="modal-body">
                <div class="form-group">
                    <label for="update-fullname">Họ và tên</label>
                    <input type="text" id="update-fullname" name="fullName" value="${sessionScope.user.fullName}" required>
                </div>
                <div class="form-group">
                    <label for="update-phone">Số điện thoại</label>
                    <c:choose>
                        <c:when test="${not empty sessionScope.user.phoneNumber}">
                            <input type="text" value="${sessionScope.user.phoneNumber}" class="input-locked" readonly title="Không thể thay đổi">
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="phoneNumber" placeholder="Nhập SĐT" required pattern="[0-9]{10,11}">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <label for="update-email">Email</label>
                    <c:choose>
                        <c:when test="${not empty sessionScope.user.email}">
                            <input type="email" value="${sessionScope.user.email}" class="input-locked" readonly title="Không thể thay đổi">
                        </c:when>
                        <c:otherwise>
                            <input type="email" name="email" placeholder="Nhập Email" required>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="modal-footer">
                <div class="modal-footer-split">
                    <button type="button" class="btn-outline close-modal-btn">Hủy</button>
                    <button type="submit" class="btn">Lưu thay đổi</button>
                </div>
            </div>
        </form>
    </div>
</div>

<%-- MODAL 2: THÊM ĐỊA CHỈ --%>
<div class="modal-overlay" id="addAddressModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Thêm địa chỉ mới</h3>
            <button class="close-modal-btn">&times;</button>
        </div>
        <form action="<c:url value='/account'/>" method="POST">
            <input type="hidden" name="action" value="add-address">
            <div class="modal-body">
                <div class="form-group">
                    <label>Tên người nhận</label>
                    <input type="text" name="receiverName" required>
                </div>
                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="text" name="phoneNumber" required pattern="[0-9]{10,11}">
                </div>
                <div class="form-group">
                    <label>Địa chỉ chi tiết</label>
                    <input type="text" name="streetAddress" required>
                </div>
                <div class="form-group">
                    <label>Loại địa chỉ</label>
                    <div class="form-radio-group">
                        <label class="radio-label"><input type="radio" name="addressType" value="Nhà riêng" checked> Nhà riêng</label>
                        <label class="radio-label" style="margin-left: 15px;"><input type="radio" name="addressType" value="Văn phòng"> Văn phòng</label>
                    </div>
                </div>
                <div class="form-group" style="margin-top: 10px;">
                    <label style="cursor: pointer; display: flex; align-items: center; gap: 8px;">
                        <input type="checkbox" name="isDefault" value="true"> Đặt làm địa chỉ mặc định
                    </label>
                </div>
            </div>
            <div class="modal-footer">
                <div class="modal-footer-split">
                    <button type="button" class="btn-outline close-modal-btn">Hủy</button>
                    <button type="submit" class="btn">Lưu địa chỉ</button>
                </div>
            </div>
        </form>
    </div>
</div>

<%-- MODAL 3: UPDATE ĐỊA CHỈ --%>
<div class="modal-overlay" id="updateAddressModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Cập nhật địa chỉ</h3>
            <button class="close-modal-btn">&times;</button>
        </div>
        <form action="<c:url value='/account'/>" method="POST">
            <input type="hidden" name="action" value="update-address">
            <input type="hidden" name="addressId" id="update-address-id">
            <div class="modal-body">
                <div class="form-group">
                    <label>Tên người nhận</label>
                    <input type="text" id="update-receiver" name="receiverName" required>
                </div>
                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="text" id="update-phone-addr" name="phoneNumber" required pattern="[0-9]{10,11}">
                </div>
                <div class="form-group">
                    <label>Địa chỉ chi tiết</label>
                    <input type="text" id="update-street" name="streetAddress" required>
                </div>
                <div class="form-group">
                    <label>Loại địa chỉ</label>
                    <div class="form-radio-group">
                        <label class="radio-label"><input type="radio" name="addressType" id="update-type-home" value="Nhà riêng"> Nhà riêng</label>
                        <label class="radio-label" style="margin-left: 15px;"><input type="radio" name="addressType" id="update-type-office" value="Văn phòng"> Văn phòng</label>
                    </div>
                </div>
                <div class="form-group" style="margin-top: 10px;">
                    <label style="cursor: pointer; display: flex; align-items: center; gap: 8px;">
                        <input type="checkbox" name="isDefault" id="update-default" value="true"> Đặt làm địa chỉ mặc định
                    </label>
                </div>
            </div>
            <div class="modal-footer">
                <div class="modal-footer-split">
                    <button type="button" class="btn-outline close-modal-btn">Hủy</button>
                    <button type="submit" class="btn">Lưu thay đổi</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="<c:url value='/js/account.js'/>"></script>
<jsp:include page="/WEB-INF/layout/footer.jsp" />