<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<%-- Load CSS: user-layout (khung), order (tab), và warranty (nút hỗ trợ) --%>
<link rel="stylesheet" href="<c:url value='/css/user-layout.css'/>">
<link rel="stylesheet" href="<c:url value='/css/order.css'/>">
<link rel="stylesheet" href="<c:url value='/css/warranty.css'/>"> <%-- THÊM DÒNG NÀY --%>

<main class="container user-dashboard">

    <c:if test="${not empty sessionScope.user}">

        <jsp:include page="/WEB-INF/views/user/components/user-info-bar.jsp" />

        <div class="dashboard-body">

            <jsp:include page="/WEB-INF/views/user/components/user-sidebar.jsp" />

            <div class="user-content">
                <div class="content-block">
                    <h3>Tra cứu bảo hành</h3>

                    <nav class="tab-nav">
                            <%-- (Giữ nguyên phần Tab Nav của bạn) --%>
                        <ul>
                            <li><a href="<c:url value='/warranty?status=all'/>" class="${currentTab == 'all' ? 'active' : ''}">Tất cả</a></li>
                                <%-- ... các tab khác ... --%>
                            <li><a href="<c:url value='/warranty?status=returned'/>" class="${currentTab == 'returned' ? 'active' : ''}">Đã trả máy</a></li>
                        </ul>
                    </nav>

                        <%-- Nội dung danh sách trống --%>
                    <div class="empty-state">
                        <i class="fa-solid fa-screwdriver-wrench"></i>
                        <p>Bạn chưa có yêu cầu bảo hành nào.</p>

                            <%-- SỬA CLASS Ở ĐÂY: Đổi btn-link thành btn-support --%>
                        <a href="<c:url value='/support'/>" class="btn-support">
                            Yêu cầu hỗ trợ ngay
                        </a>
                    </div>
                </div>
            </div>
        </div>

    </c:if>

    <%-- (Phần kiểm tra chưa đăng nhập giữ nguyên) --%>
    <c:if test="${empty sessionScope.user}">
        <div class="login-prompt-card">
            <p>Vui lòng đăng nhập để xem thông tin bảo hành.</p>
            <a href="<c:url value='/login'/>" class="btn">Đi đến trang đăng nhập</a>
        </div>
    </c:if>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />