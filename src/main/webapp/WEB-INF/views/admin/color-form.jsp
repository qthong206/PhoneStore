<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="isEdit" value="${not empty item}" />

<div class="product-form-wrapper" style="max-width: 600px; margin: 40px auto;">
    <div style="margin-bottom: 25px; text-align: center;">
        <h2 class="page-title" style="text-transform: none;">
            ${isEdit ? 'Chỉnh sửa màu sắc' : 'Thêm màu mới cho hệ thống'}
        </h2>
        <p style="color: var(--text-light); font-size: 14px;">Xác định tên màu và mã Hex để hiển thị lên giao diện bán hàng</p>
    </div>

    <div class="glass-panel" style="padding: 30px;">
        <form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/color/update' : '/admin/color/insert'}">

            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${item.id}">
            </c:if>

            <div class="form-group" style="margin-bottom: 20px;">
                <label style="font-weight: 600; margin-bottom: 10px; display: block;">Tên màu sắc (Name) <span style="color:red">*</span></label>
                <input type="text" name="name" value="${isEdit ? item.name : ''}"
                       required placeholder="Ví dụ: Titan Tự Nhiên, Trắng Starlight..."
                       style="width: 100%; padding: 12px; border-radius: 8px; border: 1px solid rgba(0,0,0,0.1);" />
            </div>

            <div class="form-group">
                <label style="font-weight: 600; margin-bottom: 10px; display: block;">Mã màu (Hex Code) <span style="color:red">*</span></label>
                <div style="display: flex; gap: 15px; align-items: center;">
                    <div style="position: relative; width: 60px; height: 50px; overflow: hidden; border-radius: 8px; border: 1px solid #ddd;">
                        <input type="color" id="colorPicker" value="${isEdit ? item.hexCode : '#000000'}"
                               style="position: absolute; top: -5px; left: -5px; width: 70px; height: 60px; cursor: pointer; border: none;" />
                    </div>
                    <input type="text" id="hexInput" name="hexCode" value="${isEdit ? item.hexCode : '#000000'}"
                           placeholder="#000000" required pattern="^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"
                           style="flex: 1; padding: 12px; border-radius: 8px; border: 1px solid rgba(0,0,0,0.1); font-family: monospace;" />
                </div>
                <small style="color: var(--text-light); margin-top: 8px; display: block;">Chọn màu bằng bảng màu hoặc nhập mã Hex (ví dụ: #FFFFFF)</small>
            </div>

            <div style="display: flex; gap: 12px; justify-content: center; margin-top: 35px;">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/color" style="min-width: 120px;">
                    Hủy bỏ
                </a>
                <button class="btn btn-save" type="submit" style="min-width: 150px;">
                    <i class="fa-solid fa-check"></i> ${isEdit ? 'Lưu thay đổi' : 'Thêm màu'}
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    const colorPicker = document.getElementById('colorPicker');
    const hexInput = document.getElementById('hexInput');

    // Cập nhật text khi chọn màu từ bảng
    colorPicker.addEventListener('input', (e) => {
        hexInput.value = e.target.value.toUpperCase();
    });

    // Cập nhật bảng màu khi gõ mã Hex
    hexInput.addEventListener('input', (e) => {
        let val = e.target.value;
        if (val.startsWith('#') && (val.length === 4 || val.length === 7)) {
            colorPicker.value = val;
        }
    });
</script>