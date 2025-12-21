<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isEdit" value="${not empty item}" />

<div class="form-container">
    <h1>
        <c:choose>
            <c:when test="${isEdit}">CHỈNH SỬA MÀU SẮC</c:when>
            <c:otherwise>THÊM MÀU MỚI</c:otherwise>
        </c:choose>
    </h1>

    <form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/color/update' : '/admin/color/insert'}">

        <c:if test="${isEdit}">
            <input type="hidden" name="id" value="${item.id}">
        </c:if>

        <div class="form-row">
            <label>Tên màu (Name)</label>
            <input type="text" name="name" value="${isEdit ? item.name : ''}" required placeholder="Ví dụ: Titan Tự Nhiên">
        </div>

        <div class="form-row">
            <label>Mã màu (Hex Code)</label>
            <div style="display: flex; gap: 10px; align-items: center;">
                <input type="color" id="colorPicker" value="${isEdit ? item.hexCode : '#000000'}" style="height: 40px; width: 60px; cursor: pointer;">

                <input type="text" id="hexInput" name="hexCode" value="${isEdit ? item.hexCode : ''}" placeholder="#000000" required style="flex: 1;">
            </div>
        </div>

        <div class="form-actions">
            <button class="btn btn-save" type="submit">${isEdit ? 'Cập nhật' : 'Tạo mới'}</button>
            <a class="btn btn-cancel" href="${pageContext.request.contextPath}/admin/color">Hủy bỏ</a>
        </div>
    </form>
</div>

<script>
    const colorPicker = document.getElementById('colorPicker');
    const hexInput = document.getElementById('hexInput');

    colorPicker.addEventListener('input', (e) => {
        hexInput.value = e.target.value.toUpperCase();
    });

    hexInput.addEventListener('input', (e) => {
        colorPicker.value = e.target.value;
    });
</script>