<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isEdit" value="${not empty brand}" />

<div class="form-container">
    <h1>
        <c:choose>
            <c:when test="${isEdit}">CHỈNH SỬA THƯƠNG HIỆU</c:when>
            <c:otherwise>THÊM THƯƠNG HIỆU MỚI</c:otherwise>
        </c:choose>
    </h1>

    <form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/brand/update' : '/admin/brand/insert'}">
        <c:if test="${isEdit}">
            <input type="hidden" name="id" value="${brand.id}" />
        </c:if>

        <div class="form-row">
            <label>Tên thương hiệu (Name)</label>
            <input type="text" name="name" value="${isEdit ? brand.name : ''}" required placeholder="Ví dụ: Apple, Samsung...">
        </div>

        <div class="form-row">
            <label>Slug (URL thân thiện)</label>
            <input type="text" name="slug" value="${isEdit ? brand.slug : ''}" placeholder="vi-du-apple">
        </div>

        <div class="form-row">
            <label>Logo URL</label>
            <input type="text" name="logoUrl" value="${isEdit ? brand.logoUrl : ''}" placeholder="https://...">
        </div>

        <div class="form-row">
            <label>Danh mục cha</label>
            <select name="categoryId">
                <c:forEach var="c" items="${categories}">
                    <option value="${c.id}" ${isEdit && brand.categoryId == c.id ? 'selected' : ''}>
                            ${c.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-actions">
            <button class="btn btn-save" type="submit">${isEdit ? 'Cập nhật' : 'Tạo mới'}</button>
            <a class="btn btn-cancel" href="${pageContext.request.contextPath}/admin/brand">Hủy bỏ</a>
        </div>
    </form>
</div>