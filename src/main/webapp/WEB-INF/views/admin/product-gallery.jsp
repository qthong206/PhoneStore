<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="gallery-container" style="padding: 20px;">

    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
        <h1>THƯ VIỆN ẢNH: <span style="color: #007bff;">${product.name}</span></h1>
        <a href="${pageContext.request.contextPath}/admin/product" class="btn btn-cancel">
            <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
        </a>
    </div>

    <div class="add-image-box" style="background: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 30px;">
        <h3><i class="fa-solid fa-plus"></i> Thêm ảnh phụ mới</h3>
        <form method="post" action="${pageContext.request.contextPath}/admin/product/gallery/add" style="display: flex; gap: 10px; margin-top: 10px;">
            <input type="hidden" name="productId" value="${product.id}">

            <input type="text" name="imageUrl" placeholder="Dán đường link ảnh vào đây (URL)..."
                   style="flex: 1; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>

            <button type="submit" class="btn btn-primary">Thêm ngay</button>
        </form>
    </div>

    <hr/>

    <h3>Danh sách ảnh hiện tại (${fn:length(images)})</h3>

    <div class="gallery-grid" style="display: flex; flex-wrap: wrap; gap: 20px; margin-top: 15px;">
        <c:if test="${empty images}">
            <p>Chưa có ảnh phụ nào.</p>
        </c:if>

        <c:forEach var="url" items="${images}">
            <div class="img-card" style="border: 1px solid #ddd; padding: 10px; border-radius: 8px; text-align: center; width: 200px; background: white; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
                <div style="height: 150px; display: flex; align-items: center; justify-content: center; overflow: hidden; margin-bottom: 10px;">
                    <img src="${url}" alt="Product Image" style="max-height: 100%; max-width: 100%;">
                </div>

                <a class="btn btn-sm btn-danger"
                   href="${pageContext.request.contextPath}/admin/product/gallery/delete?productId=${product.id}&url=${fn:escapeXml(url)}"
                   onclick="return confirm('Bạn chắc chắn muốn xóa ảnh này?')">
                    <i class="fa-solid fa-trash"></i> Xóa
                </a>
            </div>
        </c:forEach>
    </div>
</div>