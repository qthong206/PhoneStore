<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%-- HEADER TRANG --%>
<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px;">
    <div>
        <h2 class="page-title">Danh sách sản phẩm</h2>
        <p style="color: var(--text-light); font-size: 13px; margin-top: 5px;">
            Quản lý tồn kho và trạng thái hiển thị theo từng thương hiệu
        </p>
    </div>
    <a href="${pageContext.request.contextPath}/admin/product/add" class="btn btn-add">
        <i class="fa-solid fa-plus"></i> Thêm mới
    </a>
</div>

<%-- NỘI DUNG --%>
<c:forEach var="entry" items="${productMap}">
    <div class="glass-panel">
            <%-- Tiêu đề Hãng sử dụng biến màu Base --%>
        <div class="brand-group-header">
            <span class="brand-label">BRAND</span>
            <h4 class="brand-name">${entry.key.name}</h4>
        </div>

        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th style="width: 60px;">ID</th>
                    <th style="width: 80px;">Ảnh</th>
                    <th>Tên sản phẩm</th>
                    <th>Loại</th>
                    <th>Giá bán</th>
                    <th style="text-align: center;">Tồn</th>
                    <th style="text-align: center;">Trạng thái</th>
                    <th style="width: 150px; text-align: center;">Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${entry.value}">
                    <tr>
                        <td class="text-bold-light">#${p.id}</td>
                        <td>
                            <img src="<c:url value='/${p.thumbnailUrl}'/>" alt="Img">
                        </td>
                        <td>
                            <div class="product-name-main">${p.name}</div>
                            <div class="product-info-sub">Model: ${p.model} | ${p.storage}</div>
                        </td>
                        <td>
                            <span class="category-text">
                                <c:choose>
                                    <c:when test="${p.categoryId == 1}">Điện thoại</c:when>
                                    <c:when test="${p.categoryId == 2}">Laptop</c:when>
                                    <c:when test="${p.categoryId == 3}">Phụ kiện</c:when>
                                    <c:otherwise>Khác</c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <td>
                            <div class="price-sale">
                                <fmt:formatNumber value="${p.salePrice}" type="currency" currencySymbol="₫"/>
                            </div>
                            <div class="price-old">
                                <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫"/>
                            </div>
                        </td>
                        <td style="text-align: center;">
                            <span class="stock-status ${p.stockQuantity <= 5 ? 'stock-low' : ''}">
                                    ${p.stockQuantity}
                            </span>
                        </td>
                        <td style="text-align: center;">
                            <span class="badge ${p.status == 1 ? 'status-active' : 'status-hidden'}">
                                    ${p.status == 1 ? 'Active' : 'Hidden'}
                            </span>
                        </td>
                        <td style="text-align: center;">
                            <div style="display: inline-flex; gap: 5px;">
                                <a href="${pageContext.request.contextPath}/admin/product/edit?id=${p.id}" class="btn btn-sm btn-edit" title="Sửa">
                                    <i class="fa-solid fa-pen"></i>
                                </a>
                                <a href="${pageContext.request.contextPath}/admin/product/toggle?id=${p.id}"
                                   class="btn btn-sm ${p.status == 1 ? 'btn-toggle-on' : 'btn-toggle-off'}"
                                   title="Bật/Tắt hiển thị">
                                    <i class="fa-solid ${p.status == 1 ? 'fa-eye' : 'fa-eye-slash'}"></i>
                                </a>
                                <a href="${pageContext.request.contextPath}/admin/product/delete?id=${p.id}"
                                   class="btn btn-sm btn-delete"
                                   onclick="return confirm('Xóa sản phẩm này?')">
                                    <i class="fa-solid fa-trash"></i>
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:forEach>