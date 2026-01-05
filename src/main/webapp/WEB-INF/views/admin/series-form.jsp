<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="isEdit" value="${not empty item}" />

<div class="product-form-wrapper" style="max-width: 600px; margin: 0 auto; padding-top: 20px;">
    <div style="margin-bottom: 25px; text-align: center;">
        <%-- Bỏ uppercase nếu bạn muốn tiêu đề nhẹ nhàng hơn --%>
        <h2 class="page-title" style="color: var(--color-primary); font-weight: 700;">
            ${isEdit ? 'Chỉnh sửa dòng sản phẩm' : 'Thêm dòng sản phẩm mới'}
        </h2>
    </div>

    <div class="glass-panel" style="padding: 30px;">
        <form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/series/update' : '/admin/series/insert'}">
            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${item.id}" />
            </c:if>

            <div class="form-group">
                <label>Thương hiệu <span style="color:red">*</span></label>
                <select name="brandId" required>
                    <option value="">-- Chọn thương hiệu --</option>
                    <c:forEach var="b" items="${brands}">
                        <option value="${b.id}" ${isEdit && item.brandId == b.id ? 'selected' : ''}>${b.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>Tên Series <span style="color:red">*</span></label>
                <input type="text" name="name" value="${isEdit ? item.name : ''}" required placeholder="Ví dụ: iPhone 16 Series" />
            </div>

            <div class="form-group">
                <label>Năm ra mắt</label>
                <input type="number" name="releaseYear" value="${isEdit && item.releaseYear != 0 ? item.releaseYear : ''}" placeholder="Ví dụ: 2024" />
            </div>

            <div style="display: flex; gap: 10px; justify-content: center; margin-top: 30px;">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/series">Hủy</a>
                <button class="btn btn-save" type="submit">
                    <i class="fa-solid fa-check"></i> ${isEdit ? 'Cập nhật ngay' : 'Tạo mới ngay'}
                </button>
            </div>
        </form>
    </div>
</div>