<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px;">
    <div>
        <h2 class="page-title">Quản lý danh mục màu sắc</h2>
        <p style="color: var(--text-light); font-size: 13px; margin-top: 5px;">Thiết lập các bảng màu dùng chung cho toàn bộ sản phẩm</p>
    </div>
    <a href="${pageContext.request.contextPath}/admin/color/add" class="btn btn-add">
        <i class="fa-solid fa-plus"></i> Thêm màu mới
    </a>
</div>

<div class="glass-panel">
    <div class="table-responsive">
        <table>
            <thead>
            <tr>
                <th style="width: 80px;">ID</th>
                <th>Tên màu sắc</th>
                <th>Mã Hex Code</th>
                <th style="text-align: center;">Mẫu màu</th>
                <th style="width: 150px; text-align: center;">Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="c" items="${colors}">
                <tr>
                    <td style="font-weight: 700; color: var(--text-light);">#${c.id}</td>
                    <td style="font-weight: 600; color: var(--text-dark);">${c.name}</td>
                    <td style="font-family: monospace; letter-spacing: 1px;">${c.hexCode}</td>
                    <td style="text-align: center;">
                        <div style="width: 32px; height: 32px; background-color: ${c.hexCode};
                                border: 2px solid #fff; border-radius: 50%; display: inline-block;
                                box-shadow: 0 4px 8px rgba(0,0,0,0.15);"></div>
                    </td>
                    <td style="text-align: center;">
                        <div style="display: inline-flex; gap: 8px;">
                            <a href="${pageContext.request.contextPath}/admin/color/edit?id=${c.id}"
                               class="btn btn-sm btn-edit" title="Chỉnh sửa">
                                <i class="fa-solid fa-pen"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/color/delete?id=${c.id}"
                               class="btn btn-sm btn-delete"
                               onclick="return confirm('Xóa màu này có thể ảnh hưởng đến các biến thể sản phẩm đang sử dụng. Bạn chắc chứ?')"
                               title="Xóa">
                                <i class="fa-solid fa-trash"></i>
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty colors}">
                <tr>
                    <td colspan="5" style="text-align: center; padding: 30px; color: var(--text-light);">
                        Chưa có bảng màu nào được định nghĩa.
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>