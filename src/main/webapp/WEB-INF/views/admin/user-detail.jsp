<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%-- Thiết lập định dạng tiền tệ Việt Nam --%>
<fmt:setLocale value="vi_VN"/>

<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px;">
    <div>
        <h2 class="page-title">Hồ sơ khách hàng #${user.id}</h2>
        <p style="color: var(--text-light); font-size: 13px; margin-top: 5px;">Thông tin chi tiết, lịch sử mua hàng và địa chỉ nhận hàng</p>
    </div>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/user">
        <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
    </a>
</div>

<div style="display: grid; grid-template-columns: 350px 1fr; gap: 25px; align-items: start;">

    <%-- CỘT TRÁI: THÔNG TIN TÀI KHOẢN & THỐNG KÊ --%>
    <div class="glass-panel" style="text-align: center; padding: 40px 25px; position: sticky; top: 20px;">
        <div style="width: 100px; height: 100px; background: var(--grad-primary); color: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 40px; margin: 0 auto 20px auto; box-shadow: 0 10px 20px rgba(0,0,0,0.1);">
            ${user.fullName.substring(0,1).toUpperCase()}
        </div>
        <h3 style="color: var(--color-primary-dark); margin-bottom: 5px;">${user.fullName}</h3>
        <p style="color: var(--text-light); margin-bottom: 20px;">@${user.username}</p>

        <span class="badge ${user.active ? 'status-active' : 'status-hidden'}" style="margin-bottom: 25px; min-width: 120px;">
            ${user.active ? 'Đang hoạt động' : 'Tài khoản bị khóa'}
        </span>

        <%-- KHỐI THỐNG KÊ NHANH --%>
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-bottom: 25px; padding: 15px 0; border-top: 1px solid #eee; border-bottom: 1px solid #eee;">
            <div style="border-right: 1px solid #eee;">
                <div style="font-size: 18px; font-weight: 700; color: var(--color-primary);">${orderCount}</div>
                <div style="font-size: 10px; text-transform: uppercase; color: var(--text-light); letter-spacing: 0.5px;">Đơn hàng</div>
            </div>
            <div>
                <div style="font-size: 18px; font-weight: 700; color: #d70018;">
                    <fmt:formatNumber value="${totalSpent}" type="number"/>₫
                </div>
                <div style="font-size: 10px; text-transform: uppercase; color: var(--text-light); letter-spacing: 0.5px;">Tổng chi tiêu</div>
            </div>
        </div>

        <div style="text-align: left;">
            <div style="margin-bottom: 15px;">
                <label style="display: block; font-size: 11px; text-transform: uppercase; font-weight: 700; color: #999; margin-bottom: 4px;">Email</label>
                <div style="font-weight: 500; word-break: break-all;">${user.email}</div>
            </div>
            <div style="margin-bottom: 15px;">
                <label style="display: block; font-size: 11px; text-transform: uppercase; font-weight: 700; color: #999; margin-bottom: 4px;">Số điện thoại</label>
                <div style="font-weight: 500;">${not empty user.phoneNumber ? user.phoneNumber : 'Chưa cập nhật'}</div>
            </div>
            <div>
                <label style="display: block; font-size: 11px; text-transform: uppercase; font-weight: 700; color: #999; margin-bottom: 4px;">Vai trò hệ thống</label>
                <div style="font-weight: 500;"><i class="fa-solid fa-shield-halved" style="color: var(--color-primary);"></i> ${user.role}</div>
            </div>
        </div>
    </div>

    <%-- CỘT PHẢI: ĐỊA CHỈ & ĐƠN HÀNG --%>
    <div style="display: flex; flex-direction: column; gap: 25px;">

        <%-- BẢNG ĐỊA CHỈ --%>
        <div class="glass-panel">
            <h3 style="margin-bottom: 20px; color: var(--color-primary); font-size: 18px; display: flex; align-items: center; gap: 10px;">
                <i class="fa-solid fa-map-location-dot"></i> Sổ địa chỉ nhận hàng
            </h3>

            <div class="table-responsive">
                <table>
                    <thead>
                    <tr style="background: #f8f9fa;">
                        <th>Người nhận</th>
                        <th>Liên hệ</th>
                        <th>Địa chỉ chi tiết</th>
                        <th style="text-align: center;">Mặc định</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${addresses}" var="a">
                        <tr>
                            <td style="font-weight: 600;">${a.receiverName}</td>
                            <td>${a.phoneNumber}</td>
                            <td style="font-size: 13px; color: var(--text-light);">${a.streetAddress}</td>
                            <td style="text-align: center;">
                                <c:choose>
                                    <c:when test="${a.defaultAddress}">
                                        <i class="fa-solid fa-circle-check" style="color: var(--grad-success); font-size: 18px;"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #ccc; font-size: 12px;">---</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty addresses}">
                        <tr>
                            <td colspan="4" style="text-align: center; padding: 30px; color: var(--text-light);">
                                Khách hàng này chưa thêm địa chỉ nhận hàng nào.
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <%-- BẢNG LỊCH SỬ ĐƠN HÀNG --%>
        <div class="glass-panel">
            <h3 style="margin-bottom: 20px; color: var(--color-primary); font-size: 18px; display: flex; align-items: center; gap: 10px;">
                <i class="fa-solid fa-clock-rotate-left"></i> Lịch sử mua hàng
            </h3>

            <div class="table-responsive">
                <table>
                    <thead>
                    <tr style="background: #f8f9fa;">
                        <th style="width: 100px;">Mã Đơn</th>
                        <th>Ngày đặt</th>
                        <th style="text-align: right;">Tổng tiền</th>
                        <th style="text-align: center;">Trạng thái</th>
                        <th style="text-align: center;">Xem</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userOrders}" var="o">
                        <tr>
                            <td style="font-weight: 700;">#${o.id}</td>
                            <td style="font-size: 13px; color: var(--text-light);">${o.createdAt}</td>
                            <td style="text-align: right; font-weight: 700; color: #d70018;">
                                <fmt:formatNumber value="${o.totalAmount}" type="number"/>₫
                            </td>
                            <td style="text-align: center;">
                                    <span class="badge"
                                          style="background:
                                              ${o.status=='pending' ? 'var(--grad-order-pending)' :
                                                      o.status=='confirmed' ? 'var(--grad-order-confirmed)' :
                                                              o.status=='shipping' ? 'var(--grad-order-shipping)' :
                                                                      o.status=='delivered' ? 'var(--grad-order-delivered)' : 'var(--grad-order-cancelled)'};
                                                  color: white; border: none; font-size: 10px; min-width: 95px;">
                                        <c:choose>
                                            <c:when test="${o.status=='pending'}">Chờ xử lý</c:when>
                                            <c:when test="${o.status=='confirmed'}">Xác nhận</c:when>
                                            <c:when test="${o.status=='shipping'}">Đang giao</c:when>
                                            <c:when test="${o.status=='delivered'}">Hoàn thành</c:when>
                                            <c:otherwise>Đã hủy</c:otherwise>
                                        </c:choose>
                                    </span>
                            </td>
                            <td style="text-align: center;">
                                <a href="${pageContext.request.contextPath}/admin/order-detail?id=${o.id}"
                                   class="btn btn-sm btn-edit">
                                    <i class="fa-solid fa-eye" style="margin:0"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty userOrders}">
                        <tr>
                            <td colspan="5" style="text-align: center; padding: 40px; color: var(--text-light);">
                                Chưa có dữ liệu đơn hàng nào của khách hàng này.
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>