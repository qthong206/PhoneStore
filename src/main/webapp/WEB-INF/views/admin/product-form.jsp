<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isEdit" value="${not empty product}" />

<div class="product-form-container">
    <h1>
        <c:choose>
            <c:when test="${isEdit}">CHỈNH SỬA SẢN PHẨM</c:when>
            <c:otherwise>THÊM SẢN PHẨM MỚI</c:otherwise>
        </c:choose>
    </h1>

    <br/><br/>

    <form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/product/update' : '/admin/product/insert'}">

        <c:if test="${isEdit}">
            <input type="hidden" name="id" value="${product.id}" />
        </c:if>

        <div class="form-row">
            <label>Tên sản phẩm (Name)</label>
            <input type="text" name="name" value="${isEdit ? product.name : ''}" required placeholder="Ví dụ: iPhone 15 Pro Max"/>
        </div>

        <div class="form-row">
            <label>Mô tả (Description)</label>
            <textarea name="description" rows="4">${isEdit ? product.description : ''}</textarea>
        </div>

        <div class="form-group-inline">
            <div class="form-row half-width">
                <label>Giá gốc (Price)</label>
                <input type="number" step="0.01" name="price" value="${isEdit ? product.price : ''}" required />
            </div>

            <div class="form-row half-width">
                <label>Giá khuyến mãi (Sale Price)</label>
                <input type="number" step="0.01" name="salePrice" value="${isEdit ? product.salePrice : ''}" />
            </div>
        </div>

        <div class="form-row">
            <label>Link ảnh đại diện (Thumbnail URL)</label>
            <input type="text" name="thumbnailUrl" value="${isEdit ? product.thumbnailUrl : ''}" placeholder="images/products/..." />
        </div>

        <div class="form-row">
            <label>Thương hiệu (Brand)</label>
            <select name="brandId" required>
                <option value="">-- Chọn thương hiệu --</option>
                <c:forEach var="b" items="${brands}">
                    <option value="${b.id}" ${isEdit && product.brand != null && product.brand.id == b.id ? 'selected' : ''}>
                            ${b.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-row">
            <label>Dòng sản phẩm (Series)</label>
            <select name="seriesId" required>
                <option value="">-- Chọn dòng sản phẩm --</option>
                <c:forEach var="s" items="${series}">
                    <option value="${s.id}" ${isEdit && product.seriesId == s.id ? 'selected' : ''}>
                            ${s.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group-inline">
            <div class="form-row half-width">
                <label>Model (Ví dụ: Pro Max)</label>
                <input type="text" name="model" value="${isEdit ? product.model : ''}" />
            </div>

            <div class="form-row half-width">
                <label>Dung lượng (Storage)</label>
                <input type="text" name="storage" value="${isEdit ? product.storage : ''}" placeholder="128GB" />
            </div>
        </div>

        <div class="form-row">
            <label>Trạng thái (Status)</label>
            <select name="status">
                <option value="1" ${isEdit && product.status == 1 ? 'selected' : ''}>Active (Hiện)</option>
                <option value="0" ${isEdit && product.status == 0 ? 'selected' : ''}>Hidden (Ẩn)</option>
            </select>
        </div>

        <div class="form-actions">
            <button class="btn btn-save" type="submit">${isEdit ? "Cập nhật" : "Tạo mới"}</button>
            <a class="btn btn-cancel" href="${pageContext.request.contextPath}/admin/product">Hủy bỏ</a>
        </div>

    </form>
</div>