<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="vi_VN"/>

<div class="admin-header">
    <h1>QUẢN LÝ ĐƠN HÀNG</h1>
</div>

<form method="get" style="margin-bottom: 15px;">
    <select name="status" onchange="this.form.submit()">
        <option value="all" ${status=='all'?'selected':''}>Tất cả</option>
        <option value="pending" ${status=='pending'?'selected':''}>Chờ xử lý</option>
        <option value="shipped" ${status=='shipped'?'selected':''}>Đang giao</option>
        <option value="delivered" ${status=='delivered'?'selected':''}>Hoàn thành</option>
        <option value="cancelled" ${status=='cancelled'?'selected':''}>Đã hủy</option>
    </select>
</form>

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>User</th>
            <th>Người nhận</th>
            <th>Tổng tiền</th>
            <th>Trạng thái</th>
            <th>Ngày</th>
            <th>Hành động</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${orders}" var="o">
            <tr>
                <td>#${o.id}</td>
                <td>${o.username}</td>
                <td>${o.recipientName}</td>

                <td style="color:#d70018;font-weight:bold">
                    <fmt:formatNumber value="${o.totalAmount}" type="number"/> ₫
                </td>

                <td>
                <span class="badge"
                      style="background:
                          ${o.status=='pending'?'#ffc107':
                                  o.status=='shipped'?'#17a2b8':
                                          o.status=='delivered'?'#28a745':'#dc3545'};
                              color:white;padding:4px 8px;border-radius:4px;">
                        ${o.status}
                </span>
                </td>

                <td>${o.createdAt}</td>

                <td>
                    <a class="btn btn-sm btn-info"
                       href="${pageContext.request.contextPath}/admin/order-detail?id=${o.id}">
                        Xem
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
