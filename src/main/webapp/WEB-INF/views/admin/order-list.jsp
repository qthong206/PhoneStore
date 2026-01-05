<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="vi_VN"/>

<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px;">
    <div>
        <h2 class="page-title">Quản lý đơn hàng</h2>
        <p style="color: var(--text-light); font-size: 13px; margin-top: 5px;">Theo dõi và xử lý đơn hàng từ khách hàng</p>
    </div>
</div>

<%-- THANH CÔNG CỤ / BỘ LỌC --%>
<div class="glass-panel" style="padding: 15px 25px; margin-bottom: 20px;">
    <form method="get" style="display: flex; align-items: center; gap: 15px;">
        <label style="font-weight: 600; color: var(--color-primary-dark); margin-bottom: 0;">Trạng thái:</label>
        <select name="status" onchange="this.form.submit()"
                style="width: 200px; padding: 8px 15px; border-radius: 8px; border: 1px solid rgba(0,0,0,0.1); background: white;">
            <option value="all" ${status=='all'?'selected':''}>Tất cả đơn hàng</option>
            <option value="pending" ${status=='pending'?'selected':''}>🕒 Chờ xử lý</option>
            <option value="confirmed" ${status=='confirmed'?'selected':''}>✅ Đã xác nhận</option>
            <option value="shipping" ${status=='shipping'?'selected':''}>🚚 Đang giao hàng</option>
            <option value="delivered" ${status=='delivered'?'selected':''}>🏁 Đã hoàn thành</option>
            <option value="cancelled" ${status=='cancelled'?'selected':''}>❌ Đã hủy</option>
        </select>
    </form>
</div>

<div class="glass-panel">
    <div class="table-responsive">
        <table>
            <thead>
            <tr>
                <th style="width: 80px;">Mã Đơn</th>
                <th>Khách hàng</th>
                <th>Người nhận</th>
                <th>Tổng tiền</th>
                <th style="text-align: center;">Trạng thái</th>
                <th>Ngày đặt</th>
                <th style="width: 100px; text-align: center;">Chi tiết</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="o">
                <tr>
                    <td style="font-weight: 700; color: var(--text-light);">#${o.id}</td>
                    <td>
                        <div style="font-weight: 600;">${o.username}</div>
                    </td>
                    <td>
                        <div style="font-size: 13px;">${o.recipientName}</div>
                    </td>
                    <td style="color: #d70018; font-weight: 700; font-size: 15px;">
                        <fmt:formatNumber value="${o.totalAmount}" type="number"/> ₫
                    </td>
                    <td style="text-align: center;">
                            <%-- Áp dụng các class Gradient từ file CSS của bạn --%>
                        <span class="badge"
                              style="background:
                                  ${o.status=='pending' ? 'var(--grad-order-pending)' :
                                          o.status=='confirmed' ? 'var(--grad-order-confirmed)' :
                                                  o.status=='shipping' ? 'var(--grad-order-shipping)' :
                                                          o.status=='delivered' ? 'var(--grad-order-delivered)' : 'var(--grad-order-cancelled)'};
                                      color: white; border: none; min-width: 110px;">
                                <c:choose>
                                    <c:when test="${o.status=='pending'}">Chờ xử lý</c:when>
                                    <c:when test="${o.status=='confirmed'}">Đã xác nhận</c:when>
                                    <c:when test="${o.status=='shipping'}">Đang giao</c:when>
                                    <c:when test="${o.status=='delivered'}">Hoàn thành</c:when>
                                    <c:otherwise>Đã hủy</c:otherwise>
                                </c:choose>
                            </span>
                    </td>
                    <td style="color: var(--text-light); font-size: 13px;">${o.createdAt}</td>
                    <td style="text-align: center;">
                        <a href="${pageContext.request.contextPath}/admin/order-detail?id=${o.id}"
                           class="btn btn-sm btn-edit" title="Xem chi tiết">
                            <i class="fa-solid fa-eye" style="margin:0"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>