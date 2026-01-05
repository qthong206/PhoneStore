<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="vi_VN"/>

<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px;">
    <div>
        <h2 class="page-title">Chi tiết đơn hàng #${order.id}</h2>
        <p style="color: var(--text-light); font-size: 13px; margin-top: 5px;">Quản lý vận chuyển và trạng thái thanh toán</p>
    </div>
    <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-secondary">
        <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
    </a>
</div>

<div style="display: grid; grid-template-columns: 2fr 1fr; gap: 25px; margin-bottom: 25px;">

    <div class="glass-panel" style="margin-bottom: 0;">
        <h3 style="margin-bottom: 20px; color: var(--color-primary); font-size: 18px; border-bottom: 1px solid #eee; padding-bottom: 10px;">
            <i class="fa-solid fa-user-tag"></i> Thông tin giao hàng
        </h3>
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px;">
            <p><strong>Khách hàng:</strong> ${order.username}</p>
            <p><strong>Ngày đặt:</strong> ${order.createdAt}</p>
            <p><strong>Người nhận:</strong> ${order.recipientName}</p>
            <p><strong>Số điện thoại:</strong> ${order.recipientPhone}</p>
            <p style="grid-column: span 2;"><strong>Địa chỉ:</strong> ${order.shippingAddress}</p>
            <p><strong>Phương thức:</strong> <span class="badge status-active">${order.paymentMethod}</span></p>
            <p>
                <strong>Trạng thái:</strong>
                <span class="badge" style="background: ${order.status=='delivered' ? 'var(--grad-success)' : 'var(--grad-primary)'}; color:white; border:none;">
                    ${order.status}
                </span>
            </p>
        </div>
    </div>

    <div class="glass-panel" style="margin-bottom: 0; background: rgba(255, 255, 255, 0.6);">
        <h3 style="margin-bottom: 20px; color: var(--color-primary); font-size: 18px;">
            <i class="fa-solid fa-truck-ramp-box"></i> Xử lý đơn hàng
        </h3>
        <form method="post" action="${pageContext.request.contextPath}/admin/order-detail">
            <input type="hidden" name="orderId" value="${order.id}"/>

            <label style="font-size: 12px; color: var(--text-light); text-transform: uppercase; font-weight: 700;">Thay đổi trạng thái:</label>
            <select name="status" style="width:100%; padding:12px; border-radius: 8px; margin: 10px 0 20px 0; border: 1px solid #ddd;">
                <option value="pending" ${order.status=='pending'?'selected':''}>Chờ xử lý</option>
                <option value="confirmed" ${order.status=='confirmed'?'selected':''}>Đã xác nhận</option>
                <option value="shipping" ${order.status=='shipping'?'selected':''}>Đang giao hàng</option>
                <option value="delivered" ${order.status=='delivered'?'selected':''}>Hoàn thành</option>
                <option value="cancelled" ${order.status=='cancelled'?'selected':''}>Hủy đơn hàng</option>
            </select>

            <button class="btn btn-save" style="width:100%; height: 45px; font-size: 14px;">
                <i class="fa-solid fa-floppy-disk"></i> Lưu cập nhật
            </button>
        </form>
    </div>
</div>

<div class="glass-panel">
    <h3 style="margin-bottom: 20px; color: var(--color-primary); font-size: 18px;">
        <i class="fa-solid fa-box-open"></i> Sản phẩm trong đơn
    </h3>
    <div class="table-responsive">
        <table>
            <thead>
            <tr style="background: #f8f9fa;">
                <th>Sản phẩm</th>
                <th style="text-align: center;">Số lượng</th>
                <th style="text-align: right;">Đơn giá</th>
                <th style="text-align: right;">Thành tiền</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${details}" var="d">
                <tr>
                    <td>
                        <div style="font-weight: 600; color: var(--color-primary-dark);">${d.productName}</div>
                        <small style="color: var(--text-light);">Mã SP: #PS-${d.productId}</small>
                    </td>
                    <td style="text-align: center; font-weight: 700;">x${d.quantity}</td>
                    <td style="text-align: right;">
                        <fmt:formatNumber value="${d.priceAtPurchase}" type="number"/> ₫
                    </td>
                    <td style="text-align: right; color:#d70018; font-weight:700;">
                        <fmt:formatNumber value="${d.totalMoney}" type="number"/> ₫
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr style="background: #fffdfd;">
                <td colspan="3" style="text-align: right; font-weight: 700; font-size: 16px; padding: 20px;">TỔNG CỘNG THANH TOÁN:</td>
                <td style="text-align: right; color:#d70018; font-weight:800; font-size: 20px; padding: 20px;">
                    <fmt:formatNumber value="${order.totalAmount}" type="number"/> ₫
                </td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>