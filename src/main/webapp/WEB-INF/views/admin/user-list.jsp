<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px;">
    <div>
        <h2 class="page-title">Quản lý tài khoản người dùng</h2>
        <p style="color: var(--text-light); font-size: 13px; margin-top: 5px;">Quản lý thông tin khách hàng và phân quyền hệ thống</p>
    </div>
</div>

<%-- THANH TÌM KIẾM --%>
<div class="glass-panel" style="padding: 15px 25px; margin-bottom: 20px;">
    <form method="get" action="${pageContext.request.contextPath}/admin/user"
          style="display: flex; align-items: center; gap: 15px;">
        <div style="flex: 1; position: relative;">
            <i class="fa-solid fa-magnifying-glass" style="position: absolute; left: 15px; top: 12px; color: var(--text-light);"></i>
            <input type="text" name="keyword" value="${keyword}"
                   placeholder="Tìm theo username, email hoặc họ tên..."
                   style="width: 100%; padding: 10px 15px 10px 40px; border-radius: 8px; border: 1px solid rgba(0,0,0,0.1); background: white;">
        </div>
        <button type="submit" class="btn btn-add" style="min-width: 100px;">
            Tìm kiếm
        </button>
        <c:if test="${not empty keyword}">
            <a href="${pageContext.request.contextPath}/admin/user" class="btn btn-secondary">Xóa lọc</a>
        </c:if>
    </form>
</div>

<div class="glass-panel">
    <div class="table-responsive">
        <table>
            <thead>
            <tr>
                <th style="width: 60px;">ID</th>
                <th>Người dùng</th>
                <th>Liên hệ</th>
                <th style="text-align: center;">Quyền hạn</th>
                <th style="text-align: center;">Trạng thái</th>
                <th style="width: 180px; text-align: center;">Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="u">
                <tr>
                    <td style="font-weight: 700; color: var(--text-light);">#${u.id}</td>
                    <td>
                        <div style="font-weight: 600; color: var(--color-primary-dark);">${u.fullName}</div>
                        <small style="color: var(--text-light);">@${u.username}</small>
                    </td>
                    <td>
                        <div style="font-size: 13px;"><i class="fa-regular fa-envelope"></i> ${u.email}</div>
                        <div style="font-size: 13px;"><i class="fa-solid fa-phone"></i> ${u.phoneNumber}</div>
                    </td>
                    <td style="text-align: center;">
                            <span class="badge" style="background: ${u.role == 'ADMIN' ? 'var(--grad-info)' : '#e9ecef'}; color: ${u.role == 'ADMIN' ? 'white' : '#495057'}; border: none;">
                                    ${u.role}
                            </span>
                    </td>
                    <td style="text-align: center;">
                            <span class="badge ${u.active ? 'status-active' : 'status-hidden'}" style="min-width: 90px;">
                                    ${u.active ? 'Hoạt động' : 'Đã khóa'}
                            </span>
                    </td>
                    <td style="text-align: center;">
                        <div style="display: inline-flex; gap: 8px;">
                            <a class="btn btn-sm btn-edit" title="Xem chi tiết"
                               href="${pageContext.request.contextPath}/admin/user/detail?id=${u.id}">
                                <i class="fa-solid fa-eye" style="margin:0"></i>
                            </a>

                            <form method="post" action="${pageContext.request.contextPath}/admin/user" style="margin:0;">
                                <input type="hidden" name="id" value="${u.id}">
                                <input type="hidden" name="active" value="${!u.active}">
                                <button class="btn btn-sm ${u.active ? 'btn-delete' : 'btn-toggle-on'}"
                                        type="submit" title="${u.active ? 'Khóa tài khoản' : 'Mở tài khoản'}">
                                    <i class="fa-solid ${u.active ? 'fa-user-slash' : 'fa-user-check'}" style="margin:0"></i>
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty users}">
                <tr>
                    <td colspan="6" style="text-align: center; padding: 40px; color: var(--text-light);">
                        <i class="fa-solid fa-user-xmark" style="font-size: 24px; display: block; margin-bottom: 10px;"></i>
                        Không tìm thấy người dùng nào phù hợp.
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>