<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="admin-header">
    <h1>QUẢN LÝ MÀU SẮC</h1>
    <a href="${pageContext.request.contextPath}/admin/color/add" class="btn btn-primary">
        <i class="fa-solid fa-plus"></i> Thêm màu mới
    </a>
</div>

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Tên màu</th>
            <th>Mã Hex</th>
            <th>Hiển thị</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${colors}">
            <tr>
                <td>${c.id}</td>
                <td>${c.name}</td>
                <td>${c.hexCode}</td>
                <td>
                    <div style="width: 30px; height: 30px; background-color: ${c.hexCode}; border: 1px solid #ccc; border-radius: 4px;"></div>
                </td>
                <td>
                    <a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/admin/color/edit?id=${c.id}">Sửa</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/admin/color/delete?id=${c.id}"
                       onclick="return confirm('Bạn có chắc muốn xóa màu này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>