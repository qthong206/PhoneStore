<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />
<link rel="stylesheet" href="<c:url value='/css/user.css'/>">

<main class="container user-dashboard">

    <%-- Chỉ hiển thị dashboard nếu người dùng ĐÃ đăng nhập --%>
    <c:if test="${not empty sessionScope.user}">

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

        <div class="dashboard-body">
                <%-- KHỐI NAV HOÀN CHỈNH - DÙNG CHO MỌI TRANG DASHBOARD --%>
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
            <div class="user-content">
                <div class="content-block">
                    <h3>Tra cứu bảo hành</h3>

                        <%-- Thanh tab mới cho trang bảo hành, dựa theo hình ảnh bạn gửi --%>
                    <nav class="tab-nav">
                        <ul>
                            <li><a href="<c:url value='/warranty?status=all'/>"
                                   class="${currentTab == 'all' ? 'active' : ''}">Tất cả</a></li>

                            <li><a href="<c:url value='/warranty?status=received'/>"
                                   class="${currentTab == 'received' ? 'active' : ''}">Đã tiếp nhận</a></li>

                            <li><a href="<c:url value='/warranty?status=dispatching'/>"
                                   class="${currentTab == 'dispatching' ? 'active' : ''}">Đang điều phối</a></li>

                            <li><a href="<c:url value='/warranty?status=repairing'/>"
                                   class="${currentTab == 'repairing' ? 'active' : ''}">Đang Sửa</a></li>

                            <li><a href="<c:url value='/warranty?status=repaired'/>"
                                   class="${currentTab == 'repaired' ? 'active' : ''}">Đã sửa xong</a></li>

                            <li><a href="<c:url value='/warranty?status=returned'/>"
                                   class="${currentTab == 'returned' ? 'active' : ''}">Đã trả máy</a></li>
                        </ul>
                    </nav>

                        <%-- Trạng thái trống (hiển thị mặc định) --%>
                    <div class="empty-state">
                            <%-- Thay đổi icon cho phù hợp với "bảo hành" --%>
                        <i class="fa-solid fa-screwdriver-wrench"></i>
                        <p>Bạn chưa có đơn bảo hành nào.</p>
                        <a href="<c:url value='/home'/>" class="btn">Trang chủ</a>
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

<jsp:include page="/WEB-INF/layout/footer.jsp" />