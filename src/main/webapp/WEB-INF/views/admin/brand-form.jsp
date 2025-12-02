<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Brand Form</title>
    <style>
        body { font-family: Arial; }
        .form-row { margin-bottom: 10px; }
        label { font-weight: bold; display:block; }
        input, select { width: 100%; padding: 5px; }
        .btn { padding: 5px 10px; border: 1px solid #333; }
    </style>
</head>
<body>

<c:set var="isEdit" value="${not empty brand}" />

<h1>
    <c:choose>
        <c:when test="${isEdit}">Edit Brand</c:when>
        <c:otherwise>Add Brand</c:otherwise>
    </c:choose>
</h1>

<a href="${pageContext.request.contextPath}/admin/brand">&lt; Back</a>
<br/><br/>

<form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/brand/update' : '/admin/brand/insert'}">
    <c:if test="${isEdit}">
        <input type="hidden" name="id" value="${brand.id}" />
    </c:if>

    <div class="form-row">
        <label>Name</label>
        <input type="text" name="name" value="${isEdit ? brand.name : ''}" required>
    </div>

    <div class="form-row">
        <label>Slug</label>
        <input type="text" name="slug" value="${isEdit ? brand.slug : ''}">
    </div>

    <div class="form-row">
        <label>Logo URL</label>
        <input type="text" name="logoUrl" value="${isEdit ? brand.logoUrl : ''}">
    </div>

    <div class="form-row">
        <label>Category</label>
        <select name="categoryId">
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}" ${isEdit && brand.categoryId == c.id ? 'selected' : ''}>
                        ${c.name}
                </option>
            </c:forEach>
        </select>
    </div>

    <button class="btn" type="submit">
        <c:out value="${isEdit ? 'Update' : 'Create'}"/>
    </button>
</form>

</body>
</html>
