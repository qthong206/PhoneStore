<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px;">
    <div>
        <h2 class="page-title">Quản lý thương hiệu</h2>
        <p style="color: var(--text-light); font-size: 13px; margin-top: 5px;">Danh sách các hãng sản xuất thiết bị trong hệ thống</p>
    </div>
    <a href="${pageContext.request.contextPath}/admin/brand/add" class="btn btn-add">
        <i class="fa-solid fa-plus"></i> Thêm thương hiệu
    </a>
</div>

<div class="glass-panel">
    <div class="table-responsive">
        <table>
            <thead>
            <tr>
                <th style="width: 80px;">ID</th>
                <th style="width: 100px;">Logo</th>
                <th>Tên thương hiệu</th>
                <th>Đường dẫn (Slug)</th>
                <th style="width: 150px; text-align: center;">Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="b" items="${brands}">
                <tr>
                    <td style="font-weight: 700; color: var(--text-light);">#${b.id}</td>
                    <td>
                        <div style="width: 50px; height: 50px; background: #fff; border: 1px solid #eee; border-radius: 8px; display: flex; align-items: center; justify-content: center; padding: 5px;">
                            <c:choose>
                                <c:when test="${not empty b.logoUrl}">
                                    <img src="${b.logoUrl}" alt="${b.name}" style="max-width: 100%; max-height: 100%; object-fit: contain;">
                                </c:when>
                                <c:otherwise>
                                    <i class="fa-solid fa-image" style="color: #ddd;"></i>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                    <td><strong style="color: var(--color-primary-dark); font-size: 15px;">${b.name}</strong></td>
                    <td style="font-family: monospace; color: var(--text-light);">${b.slug}</td>
                    <td style="text-align: center;">
                        <div style="display: inline-flex; gap: 8px;">
                            <a href="${pageContext.request.contextPath}/admin/brand/edit?id=${b.id}" class="btn btn-sm btn-edit">
                                <i class="fa-solid fa-pen"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/brand/delete?id=${b.id}"
                               class="btn btn-sm btn-delete"
                               onclick="return confirm('Xóa thương hiệu này sẽ ảnh hưởng đến các sản phẩm và dòng máy liên quan. Bạn chắc chứ?')">
                                <i class="fa-solid fa-trash"></i>
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>