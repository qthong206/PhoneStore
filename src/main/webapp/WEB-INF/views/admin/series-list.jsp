<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="admin-header">
    <h1>QUẢN LÝ DÒNG SẢN PHẨM (SERIES)</h1>
    <a href="${pageContext.request.contextPath}/admin/series/add" class="btn btn-primary">
        <i class="fa-solid fa-plus"></i> Thêm Series
    </a>
</div>

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Tên Series</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${series}">
            <tr>
                <td>${s.id}</td>
                <td>${s.name}</td>
                <td>
                    <a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/admin/series/edit?id=${s.id}">Sửa</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/admin/series/delete?id=${s.id}"
                       onclick="return confirm('Bạn có chắc muốn xóa dòng sản phẩm này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>