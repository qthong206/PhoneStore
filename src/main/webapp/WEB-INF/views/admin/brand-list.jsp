<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="admin-header">
    <h1>QUẢN LÝ THƯƠNG HIỆU</h1>
    <a href="${pageContext.request.contextPath}/admin/brand/add" class="btn btn-primary">
        <i class="fa-solid fa-plus"></i> Thêm thương hiệu
    </a>
</div>

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Tên thương hiệu</th>
            <th>Slug</th>
            <th>Logo</th>
            <th>Danh mục (Category)</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${brands}">
            <tr>
                <td>${b.id}</td>
                <td><strong>${b.name}</strong></td>
                <td>${b.slug}</td>
                <td>
                    <c:if test="${not empty b.logoUrl}">
                        <img src="${b.logoUrl}" alt="${b.name}" style="height: 30px; object-fit: contain;">
                    </c:if>
                </td>
                <td>${b.categoryId}</td>
                <td>
                    <a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/admin/brand/edit?id=${b.id}">Sửa</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/admin/brand/delete?id=${b.id}"
                       onclick="return confirm('Bạn có chắc muốn xóa thương hiệu này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>