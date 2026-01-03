<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="admin-header">
    <h2>Quản lý tài khoản người dùng</h2>
</div>

<form method="get"
      action="${pageContext.request.contextPath}/admin/user"
      class="search-form">
    <input type="text" name="keyword"
           placeholder="Tìm username / email / tên"
           value="${keyword}">
    <button type="submit" class="btn btn-info">Tìm</button>
</form>

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Họ tên</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Role</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${users}" var="u">
            <tr>
                <td>${u.id}</td>
                <td>${u.username}</td>
                <td>${u.fullName}</td>
                <td>${u.email}</td>
                <td>${u.phoneNumber}</td>
                <td>${u.role}</td>

                <td>
                <span class="badge ${u.active ? 'badge-active' : 'badge-locked'}">
                        ${u.active ? 'Hoạt động' : 'Đã khóa'}
                </span>
                </td>

                <td>
                    <a class="btn btn-info"
                       href="${pageContext.request.contextPath}/admin/user/detail?id=${u.id}">
                        Chi tiết
                    </a>

                    <form method="post"
                          action="${pageContext.request.contextPath}/admin/user"
                          style="display:inline;">
                        <input type="hidden" name="id" value="${u.id}">
                        <input type="hidden" name="active" value="${!u.active}">
                        <button class="btn ${u.active ? 'btn-danger' : 'btn-success'}"
                                type="submit">
                                ${u.active ? 'Khóa' : 'Mở'}
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty users}">
            <tr>
                <td colspan="8" align="center">Không có người dùng</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
