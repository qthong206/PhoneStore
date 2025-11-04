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

                <%-- MENU ĐIỀU HƯỚNG BÊN TRÁI (Đầy đủ) --%>
            <nav class="user-nav">
                <ul>
                    <li><a href="<c:url value='/user'/>"
                           class="${currentView == 'overview' ? 'active' : ''}">
                        <i class="fa-solid fa-gauge"></i><span>Tổng quan</span>
                    </a></li>

                    <li><a href="<c:url value='/order'/>"
                           class="${currentView == 'order' ? 'active' : ''}">
                        <i class="fa-solid fa-clock-rotate-left"></i><span>Lịch sử mua hàng</span>
                    </a></li>

                    <li><a href="<c:url value='/warranty'/>"
                           class="${currentView == 'warranty' ? 'active' : ''}">
                        <i class="fa-solid fa-shield-halved"></i><span>Tra cứu bảo hành</span>
                    </a></li>

                    <li><a href="<c:url value='/rank'/>"
                           class="${currentView == 'rank' ? 'active' : ''}">
                        <i class="fa-solid fa-gem"></i><span>Hạng thành viên</span>
                    </a></li>

                    <li><a href="<c:url value='/account'/>"
                           class="${currentView == 'account' ? 'active' : ''}">
                        <i class="fa-solid fa-user-pen"></i><span>Thông tin tài khoản</span>
                    </a></li>

                    <li><a href="<c:url value='/policy'/>"
                           class="${currentView == 'policy' ? 'active' : ''}">
                        <i class="fa-solid fa-book"></i><span>Chính sách bảo hành</span>
                    </a></li>

                    <li><a href="<c:url value='/support'/>"
                           class="${currentView == 'support' ? 'active' : ''}">
                        <i class="fa-solid fa-headset"></i><span>Góp ý - Phản hồi - Hỗ trợ</span>
                    </a></li>

                    <li><a href="<c:url value='/terms'/>"
                           class="${currentView == 'terms' ? 'active' : ''}">
                        <i class="fa-solid fa-file-contract"></i><span>Điều khoản sử dụng</span>
                    </a></li>

                    <li><a href="<c:url value='/logout'/>" class="logout-link"><i class="fa-solid fa-right-from-bracket"></i><span>Đăng xuất</span></a></li>
                </ul>
            </nav>

                <%-- NỘI DUNG CHÍNH CỦA TRANG HẠNG THÀNH VIÊN --%>
            <div class="user-content">

                <div class="content-block">
                    <h3>Ưu đãi của bạn</h3>
                    <div class="empty-state">
                        <i class="fa-solid fa-gift"></i>
                        <p>Bạn chưa có ưu đãi nào</p>
                    </div>
                </div>

                <div class="content-block">
                    <h3>Hạng thành viên</h3>

                    <div class="rank-progression-container">
                        <div class="rank-card current">
                            <span class="rank-badge">S-NULL</span>
                            <i class="fa-solid fa-user rank-icon"></i>
                            <p class="rank-user-name">${sessionScope.user.fullName}</p>
                            <span class="rank-meta">Đã mua <strong>0đ</strong> / 3.000.000đ</span>
                            <span class="rank-note">Cần chi tiêu thêm <strong>3.000.000đ</strong> để lên hạng S-NEW</span>
                        </div>

                        <div class="rank-card locked">
                            <span class="rank-badge" style="background-color: #fbd7b5; color: #e67e22;">S-NEW</span>
                            <i class="fa-solid fa-lock rank-icon"></i>
                            <p class="rank-user-name">Hạng S-NEW</p>
                            <span class="rank-meta">Chưa mở khóa hạng thành viên</span>
                        </div>

                        <div class="rank-card locked">
                            <span class="rank-badge" style="background-color: #f9e79f; color: #f1c40f;">S-MEM</span>
                            <i class="fa-solid fa-lock rank-icon"></i>
                            <p class="rank-user-name">Hạng S-MEM</p>
                            <span class="rank-meta">Chưa mở khóa hạng thành viên</span>
                        </div>
                    </div>

                    <div class="progress-bar-container">
                        <div class="progress-bar-track">
                            <div class="progress-bar-fill" style="width: 0%;"></div>
                        </div>
                        <div class="progress-steps">
                            <span class="step-dot complete"><i class="fa-solid fa-check"></i></span>
                            <span class="step-dot"></span>
                            <span class="step-dot"></span>
                        </div>
                    </div>
                </div>

                <div class="content-block">
                    <h3>Điều kiện thăng cấp</h3>
                    <ul class="condition-list">
                        <li>
                            <i class="fa-solid fa-gem"></i>
                            <span>Tổng số tiền mua hàng tích lũy trong năm nay và năm liền trước đạt từ 0 đến 3 triệu đồng.</span>
                        </li>
                    </ul>
                </div>

                <div class="content-block">
                    <h3>Ưu đãi mua hàng (S-NULL)</h3>
                    <ul class="condition-list">
                        <li>
                            <i class="fa-solid fa-tag"></i>
                            <span>Hiện chưa có ưu đãi mua hàng đặc biệt cho hạng thành viên S-NULL.</span>
                        </li>
                    </ul>
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

<jsp:include page="/WEB-INF/layout/footer.jsp" />