<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Product Form</title>
    <style>
        body { font-family: Arial; }
        .form-row { margin-bottom: 10px; }
        label { font-weight: bold; display:block; }
        input, textarea { width: 100%; padding: 5px; }
        .btn { padding: 5px 10px; border: 1px solid #333; }
    </style>
</head>

<body>

<c:set var="isEdit" value="${not empty product}" />

<h1>
    <c:choose>
        <c:when test="${isEdit}">Edit Product</c:when>
        <c:otherwise>Add Product</c:otherwise>
    </c:choose>
</h1>

<a href="${pageContext.request.contextPath}/admin/product">&lt; Back</a>
<br/><br/>

<form method="post" action="${isEdit ? pageContext.request.contextPath+'/admin/product/update' : pageContext.request.contextPath+'/admin/product/insert'}">

    <c:if test="${isEdit}">
        <input type="hidden" name="id" value="${product.id}" />
    </c:if>

    <div class="form-row">
        <label>Name</label>
        <input type="text" name="name" value="${isEdit ? product.name : ''}" required />
    </div>

    <div class="form-row">
        <label>Description</label>
        <textarea name="description">${isEdit ? product.description : ''}</textarea>
    </div>

    <div class="form-row">
        <label>Price</label>
        <input type="number" step="0.01" name="price" value="${isEdit ? product.price : ''}" required />
    </div>

    <div class="form-row">
        <label>Sale Price</label>
        <input type="number" step="0.01" name="salePrice" value="${isEdit ? product.salePrice : ''}" />
    </div>

    <div class="form-row">
        <label>Thumbnail URL</label>
        <input type="text" name="thumbnailUrl" value="${isEdit ? product.thumbnailUrl : ''}" />
    </div>

    <div class="form-row">
        <label>Brand ID</label>
        <input type="number" name="brandId" value="${isEdit ? product.brand.id : ''}" required />
    </div>

    <div class="form-row">
        <label>Series ID</label>
        <input type="number" name="seriesId" value="${isEdit ? product.seriesId : ''}" />
    </div>

    <div class="form-row">
        <label>Model</label>
        <input type="text" name="model" value="${isEdit ? product.model : ''}" />
    </div>

    <div class="form-row">
        <label>Storage</label>
        <input type="text" name="storage" value="${isEdit ? product.storage : ''}" />
    </div>

    <div class="form-row">
        <label>Status</label>
        <select name="status">
            <option value="1" ${isEdit && product.status == 1 ? 'selected' : ''}>Active</option>
            <option value="0" ${isEdit && product.status == 0 ? 'selected' : ''}>Hidden</option>
        </select>
    </div>

    <button class="btn" type="submit">${isEdit ? "Update" : "Create"}</button>

</form>

</body>
</html>
