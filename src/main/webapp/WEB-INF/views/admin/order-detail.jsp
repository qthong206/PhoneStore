<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="vi_VN"/>

<div class="admin-header">
    <h1>CHI TIẾT ĐƠN HÀNG #${order.id}</h1>
    <a href="${pageContext.request.contextPath}/admin/orders"
       class="btn btn-secondary">
        ← Quay lại
    </a>
</div>

<div style="display:flex; gap:30px; margin-bottom:30px;">

    <!-- Thông tin đơn -->
    <div style="flex:1">
        <h3>Thông tin người nhận</h3>
        <p><strong>Khách hàng:</strong> ${order.username}</p>
        <p><strong>Người nhận:</strong> ${order.recipientName}</p>
        <p><strong>SĐT:</strong> ${order.recipientPhone}</p>
        <p><strong>Địa chỉ:</strong> ${order.shippingAddress}</p>
        <p><strong>Thanh toán:</strong> ${order.paymentMethod}</p>
        <p>
            <strong>Trạng thái:</strong>
            <span class="badge"
                  style="background:
                  ${order.status=='pending'?'#ffc107':
                          order.status=='shipped'?'#17a2b8':
                                  order.status=='delivered'?'#28a745':'#dc3545'};
                          color:white;padding:4px 8px;border-radius:4px;">
                ${order.status}
            </span>
        </p>
        <p><strong>Ngày tạo:</strong> ${order.createdAt}</p>
    </div>

    <!-- Cập nhật trạng thái -->
    <div style="width:300px">
        <h3>Cập nhật trạng thái</h3>

        <form method="post"
              action="${pageContext.request.contextPath}/admin/order-detail">

            <input type="hidden" name="orderId" value="${order.id}"/>

            <select name="status" style="width:100%; padding:6px">
                <option value="pending"
                ${order.status=='pending'?'selected':''}>
                    Chờ xử lý
                </option>
                <option value="shipped"
                ${order.status=='shipped'?'selected':''}>
                    Đang giao
                </option>
                <option value="delivered"
                ${order.status=='delivered'?'selected':''}>
                    Hoàn thành
                </option>
                <option value="cancelled"
                ${order.status=='cancelled'?'selected':''}>
                    Đã hủy
                </option>
            </select>

            <button class="btn btn-primary"
                    style="margin-top:10px;width:100%">
                Cập nhật
            </button>
        </form>
    </div>
</div>

<!-- Danh sách sản phẩm -->
<h3>Sản phẩm trong đơn</h3>

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>Sản phẩm</th>
            <th>SL</th>
            <th>Giá</th>
            <th>Tổng</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${details}" var="d">
            <tr>
                <td>
                    <strong>${d.productName}</strong>
                </td>
                <td>${d.quantity}</td>
                <td>
                    <fmt:formatNumber value="${d.priceAtPurchase}" type="number"/> ₫
                </td>
                <td style="color:#d70018;font-weight:bold">
                    <fmt:formatNumber value="${d.totalMoney}" type="number"/> ₫
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
