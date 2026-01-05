<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%-- [FIX] THÊM DÒNG NÀY ĐỂ HIỂU THẺ C:URL --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<aside class="admin-sidebar">
    <div style="text-align: center; margin-bottom: 30px; padding-bottom: 20px; border-bottom: 1px solid rgba(255,255,255,0.3);">
        <a href="${pageContext.request.contextPath}/home" title="Về trang chủ bán hàng">
            <%-- Bây giờ thẻ c:url sẽ hoạt động đúng --%>
            <img src="<c:url value='/images/logo.png'/>" alt="Logo" style="height: 50px; width: auto;">
        </a>
    </div>

    <nav class="nav-bar">
        <div class="nav-group">
            <div class="nav-label">QUẢN LÝ SẢN PHẨM</div>
            <a href="${pageContext.request.contextPath}/admin/product" class="nav-link">
                <div class="icon-box"><i class="fa-solid fa-mobile-screen"></i></div>
                <span>Danh sách sản phẩm</span>
            </a>
            <a href="${pageContext.request.contextPath}/admin/product/add" class="nav-link">
                <div class="icon-box"><i class="fa-solid fa-plus"></i></div>
                <span>Thêm sản phẩm</span>
            </a>
            <a href="${pageContext.request.contextPath}/admin/series" class="nav-link">
                <div class="icon-box"><i class="fa-solid fa-layer-group"></i></div>
                <span>Dòng sản phẩm</span>
            </a>
            <a href="${pageContext.request.contextPath}/admin/brand" class="nav-link">
                <div class="icon-box"><i class="fa-solid fa-copyright"></i></div>
                <span>Thương hiệu</span>
            </a>
            <a href="${pageContext.request.contextPath}/admin/color" class="nav-link">
                <div class="icon-box"><i class="fa-solid fa-palette"></i></div>
                <span>Màu sắc</span>
            </a>
        </div>

        <div class="nav-group">
            <div class="nav-label">KINH DOANH</div>
            <a href="${pageContext.request.contextPath}/admin/orders" class="nav-link">
                <div class="icon-box"><i class="fa-solid fa-file-invoice-dollar"></i></div>
                <span>Đơn hàng</span>
            </a>
            <a href="${pageContext.request.contextPath}/admin/user" class="nav-link">
                <div class="icon-box"><i class="fa-solid fa-users"></i></div>
                <span>Khách hàng</span>
            </a>
        </div>
    </nav>
</aside>

<style>
    /* CSS nội bộ cho sidebar giữ nguyên như cũ */
    .nav-group { margin-bottom: 25px; }
    .nav-label { font-size: 11px; font-weight: 800; color: #636e72; text-transform: uppercase; letter-spacing: 1px; margin-bottom: 10px; padding-left: 10px; }
    .nav-link { display: flex; align-items: center; padding: 12px 15px; color: var(--text-dark); text-decoration: none; border-radius: 12px; margin-bottom: 5px; transition: all 0.3s ease; font-weight: 500; font-size: 14px; }
    .nav-link:hover { background: rgba(255, 255, 255, 0.6); color: #6c5ce7; transform: translateX(5px); box-shadow: 0 4px 10px rgba(0,0,0,0.05); }
    .icon-box { width: 30px; display: flex; justify-content: center; margin-right: 5px; font-size: 16px; }
</style>