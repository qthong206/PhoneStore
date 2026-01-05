<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="admin-header">
    <h2>Chi tiết người dùng</h2>
</div>

<a class="btn btn-info"
   href="${pageContext.request.contextPath}/admin/user">
    ← Quay lại
</a>

<br><br>

<div class="table-container">
    <table>
        <tbody>
        <tr><th>ID</th><td>${user.id}</td></tr>
        <tr><th>Username</th><td>${user.username}</td></tr>
        <tr><th>Họ tên</th><td>${user.fullName}</td></tr>
        <tr><th>Email</th><td>${user.email}</td></tr>
        <tr><th>SĐT</th><td>${user.phoneNumber}</td></tr>
        <tr><th>Role</th><td>${user.role}</td></tr>
        <tr>
            <th>Trạng thái</th>
            <td>
            <span class="badge ${user.active ? 'badge-active' : 'badge-locked'}">
                ${user.active ? 'Hoạt động' : 'Đã khóa'}
            </span>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<br>

<h3>Danh sách địa chỉ</h3>

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>Người nhận</th>
            <th>SĐT</th>
            <th>Địa chỉ</th>
            <th>Loại</th>
            <th>Mặc định</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${addresses}" var="a">
            <tr>
                <td>${a.receiverName}</td>
                <td>${a.phoneNumber}</td>
                <td>${a.streetAddress}</td>
                <td>${a.addressType}</td>
                <td>${a.defaultAddress ? '✅' : ''}</td>
            </tr>
        </c:forEach>

        <c:if test="${empty addresses}">
            <tr>
                <td colspan="5" align="center">Người dùng chưa có địa chỉ</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
