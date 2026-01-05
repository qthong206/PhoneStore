<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="isEdit" value="${not empty brand}" />

<div class="product-form-wrapper" style="max-width: 600px; margin: 0 auto; padding-top: 20px;">
    <div style="margin-bottom: 25px; text-align: center;">
        <h2 class="page-title" style="color: var(--color-primary); font-weight: 700; text-transform: none;">
            ${isEdit ? 'Chỉnh sửa thương hiệu' : 'Thêm thương hiệu mới'}
        </h2>
        <p style="color: var(--text-light); font-size: 14px;">Thông tin hãng sản xuất và logo định danh</p>
    </div>

    <div class="glass-panel" style="padding: 30px;">
        <form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/brand/update' : '/admin/brand/insert'}">

            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${brand.id}" />
            </c:if>

            <div class="form-group">
                <label>Tên thương hiệu <span style="color:red">*</span></label>
                <input type="text" name="name" id="brandName" value="${isEdit ? brand.name : ''}" required
                       placeholder="Ví dụ: Apple, Samsung, Xiaomi..." onkeyup="generateSlug()" />
            </div>

            <div class="form-group">
                <label>Slug (URL thân thiện)</label>
                <input type="text" name="slug" id="brandSlug" value="${isEdit ? brand.slug : ''}"
                       placeholder="apple-store" />
                <small style="color: var(--text-light); font-size: 11px;">Để trống để hệ thống tự tạo từ tên</small>
            </div>

            <div class="form-group">
                <label>Đường dẫn Logo (URL)</label>
                <input type="text" name="logoUrl" id="logoUrlInput" value="${isEdit ? brand.logoUrl : ''}"
                       placeholder="https://example.com/logo.png" onchange="previewLogo()" />

                <div id="logoPreviewContainer" style="margin-top: 15px; text-align: center; ${isEdit ? '' : 'display:none;'}">
                    <p style="font-size: 12px; margin-bottom: 5px; color: var(--text-light);">Xem trước logo:</p>
                    <img id="logoPreview" src="${isEdit ? brand.logoUrl : ''}"
                         style="max-height: 80px; max-width: 100%; object-fit: contain; border: 1px solid #eee; padding: 10px; border-radius: 8px; background: #fff;">
                </div>
            </div>

            <div style="display: flex; gap: 12px; justify-content: center; margin-top: 30px;">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/brand">Hủy bỏ</a>
                <button class="btn btn-save" type="submit">
                    <i class="fa-solid fa-check"></i> ${isEdit ? 'Cập nhật ngay' : 'Tạo mới ngay'}
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    // Hàm tự động tạo Slug khi nhập tên (Tiện ích thêm)
    function generateSlug() {
        const name = document.getElementById('brandName').value;
        const slug = name.toLowerCase()
            .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
            .replace(/[^\w ]+/g, '')
            .replace(/ +/g, '-');
        document.getElementById('brandSlug').value = slug;
    }

    // Hàm xem trước Logo khi nhập Link
    function previewLogo() {
        const url = document.getElementById('logoUrlInput').value;
        const img = document.getElementById('logoPreview');
        const container = document.getElementById('logoPreviewContainer');

        if(url) {
            img.src = url;
            container.style.display = 'block';
        } else {
            container.style.display = 'none';
        }
    }
</script>