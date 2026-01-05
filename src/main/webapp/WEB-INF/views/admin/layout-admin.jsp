<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản Trị Hệ Thống</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/css/admin-base.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>
</head>
<body>

<div class="admin-wrapper">
    <%-- 1. SIDEBAR (MENU TRÁI) --%>
    <jsp:include page="sidebar-admin.jsp" />

    <%-- 2. KHU VỰC CHÍNH (BÊN PHẢI) --%>
    <main class="admin-main">

        <%-- Header nổi --%>
        <jsp:include page="header-admin.jsp" />

        <%--
           [ĐÃ SỬA] Nội dung thay đổi từng trang.
           QUAN TRỌNG: Không dùng class 'glass-panel' ở đây nữa.
           Để các trang con (như product-list.jsp) tự quyết định việc đóng khung kính.
        --%>
        <div class="admin-content-wrapper" style="min-height: 80vh;">
            <jsp:include page="${contentPage}"/>
        </div>

    </main>
</div>

</body>
</html>