<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<aside class="sidebar">
    <div class="sidebar-l">
        <nav class="nav-bar">
            <div class="nav-group">
                <a class="a-item1"><span>QUẢN LÝ SẢN PHẨM</span></a>

                <a href="${pageContext.request.contextPath}/admin/product" class="a-tiem">
                    <i class="fa-solid fa-mobile-screen"></i> <span>Danh sách sản phẩm</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/product/add" class="a-tiem">
                    <i class="fa-solid fa-plus"></i> <span>Thêm sản phẩm</span>
                </a>

                <a href="${pageContext.request.contextPath}/admin/series" class="a-tiem">
                    <i class="fa-solid fa-layer-group"></i> <span>Dòng sản phẩm (Series)</span>
                </a>

                <a href="${pageContext.request.contextPath}/admin/brand" class="a-tiem">
                    <i class="fa-solid fa-copyright"></i> <span>Thương hiệu (Brand)</span>
                </a>

                <a href="${pageContext.request.contextPath}/admin/color" class="a-tiem">
                    <i class="fa-solid fa-palette"></i> <span>Màu sắc</span>
                </a>
            </div>

            <div class="nav-group">
                <a class="a-item1"><span>QUẢN LÝ KINH DOANH</span></a>

                <%-- Giả định bạn có OrderServlet --%>
                <a href="${pageContext.request.contextPath}/admin/orders" class="a-tiem">
                    <i class="fa-solid fa-file-invoice-dollar"></i> <span>Đơn hàng</span>
                </a>

                <%-- Giả định bạn có UserServlet --%>
                <a href="${pageContext.request.contextPath}/admin/user" class="a-tiem">
                    <i class="fa-solid fa-users"></i> <span>Khách hàng</span>
                </a>
            </div>
        </nav>
    </div>
</aside>