<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="user-nav">
    <ul>
        <li><a href="<c:url value='/user'/>" class="${currentView == 'overview' ? 'active' : ''}">
            <i class="fa-solid fa-gauge"></i><span>Tổng quan</span>
        </a></li>

        <li><a href="<c:url value='/order'/>" class="${currentView == 'order' ? 'active' : ''}">
            <i class="fa-solid fa-clock-rotate-left"></i><span>Lịch sử mua hàng</span>
        </a></li>

        <li><a href="<c:url value='/warranty'/>" class="${currentView == 'warranty' ? 'active' : ''}">
            <i class="fa-solid fa-shield-halved"></i><span>Tra cứu bảo hành</span>
        </a></li>

        <li><a href="<c:url value='/rank'/>" class="${currentView == 'rank' ? 'active' : ''}">
            <i class="fa-solid fa-gem"></i><span>Hạng thành viên</span>
        </a></li>

        <li><a href="<c:url value='/account'/>" class="${currentView == 'account' ? 'active' : ''}">
            <i class="fa-solid fa-user-pen"></i><span>Thông tin tài khoản</span>
        </a></li>

        <li><a href="<c:url value='/policy'/>" class="${currentView == 'policy' ? 'active' : ''}">
            <i class="fa-solid fa-book"></i><span>Chính sách bảo hành</span>
        </a></li>

        <li><a href="<c:url value='/support'/>" class="${currentView == 'support' ? 'active' : ''}">
            <i class="fa-solid fa-headset"></i><span>Góp ý - Phản hồi - Hỗ trợ</span>
        </a></li>

        <li><a href="<c:url value='/terms'/>" class="${currentView == 'terms' ? 'active' : ''}">
            <i class="fa-solid fa-file-contract"></i><span>Điều khoản sử dụng</span>
        </a></li>

        <%-- ID để JS bắt sự kiện --%>
        <li>
            <a href="<c:url value='/logout'/>" class="logout-link" id="btn-logout-trigger">
                <i class="fa-solid fa-right-from-bracket"></i><span>Đăng xuất</span>
            </a>
        </li>
    </ul>
</nav>

<%-- HTML MODAL ĐĂNG XUẤT (Giao diện mới) --%>
<div class="modal-overlay" id="logoutConfirmModal">
    <div class="modal-content modal-logout">
        <div class="modal-header">
            <h3>Đăng xuất</h3>
        </div>

        <div class="modal-body">
            <p>Bạn có chắc chắn muốn đăng xuất?</p>
        </div>

        <div class="modal-footer">
            <div class="modal-footer-split">
                <a href="<c:url value='/logout'/>" class="btn-large btn-outline-secondary">
                    Đăng xuất
                </a>
                <button type="button" class="btn-large btn-primary close-logout-modal">
                    Ở lại trang
                </button>
            </div>
        </div>
    </div>
</div>