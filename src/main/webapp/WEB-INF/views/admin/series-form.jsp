<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isEdit" value="${not empty item}" />

<div class="form-container">
    <h1>
        <c:choose>
            <c:when test="${isEdit}">CHỈNH SỬA SERIES</c:when>
            <c:otherwise>THÊM SERIES MỚI</c:otherwise>
        </c:choose>
    </h1>

    <form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/series/update' : '/admin/series/insert'}">

        <c:if test="${isEdit}">
            <input type="hidden" name="id" value="${item.id}">
        </c:if>

        <div class="form-row">
            <label>Tên Series</label>
            <input type="text" name="name" value="${isEdit ? item.name : ''}" required placeholder="Ví dụ: iPhone 15 Series">
        </div>

        <div class="form-actions">
            <button class="btn btn-save" type="submit">${isEdit ? 'Cập nhật' : 'Tạo mới'}</button>
            <a class="btn btn-cancel" href="${pageContext.request.contextPath}/admin/series">Hủy bỏ</a>
        </div>
    </form>
</div>