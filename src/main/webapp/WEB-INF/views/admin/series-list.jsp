<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px;">
    <div>
        <h2 class="page-title">Quản lý dòng sản phẩm</h2>
        <p style="color: var(--text-light); font-size: 13px; margin-top: 5px;">Quản lý các series theo hãng và năm ra mắt</p>
    </div>
    <a href="${pageContext.request.contextPath}/admin/series/add" class="btn btn-add">
        <i class="fa-solid fa-plus"></i> Thêm Series
    </a>
</div>

<div class="glass-panel">
    <div class="table-responsive">
        <table>
            <thead>
            <tr>
                <th style="width: 80px;">ID</th>
                <th>Tên Series</th>
                <th>Thương hiệu</th>
                <th style="text-align: center;">Năm ra mắt</th>
                <th style="width: 150px; text-align: center;">Hành động</th>
            </tr>
            </thead>
            <tbody>
            <%-- Biến 'series' được lấy từ req.setAttribute("series", list) trong Servlet --%>
            <c:forEach var="s" items="${series}">
                <tr>
                    <td style="font-weight: 700; color: var(--text-light);">#${s.id}</td>
                    <td style="font-weight: 600;">${s.name}</td>
                    <td>
                            <span class="badge" style="background: rgba(8, 83, 119, 0.1); color: var(--color-primary); border-radius: 6px; padding: 4px 12px;">
                                <%-- 'brandName' lấy từ câu lệnh JOIN trong DAO --%>
                                ${not empty s.brandName ? s.brandName : 'Chưa xác định'}
                            </span>
                    </td>
                    <td style="text-align: center; font-weight: 500;">
                            ${not empty s.releaseYear && s.releaseYear != 0 ? s.releaseYear : '---'}
                    </td>
                    <td style="text-align: center;">
                        <div style="display: inline-flex; gap: 8px;">
                            <a href="${pageContext.request.contextPath}/admin/series/edit?id=${s.id}" class="btn btn-sm btn-edit">
                                <i class="fa-solid fa-pen"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/series/delete?id=${s.id}"
                               class="btn btn-sm btn-delete"
                               onclick="return confirm('Xóa dòng sản phẩm này?')">
                                <i class="fa-solid fa-trash"></i>
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty series}">
                <tr>
                    <td colspan="5" style="text-align: center; padding: 40px; color: var(--text-light);">Chưa có dữ liệu series nào.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>