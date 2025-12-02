<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Color Form</title>
    <style>
        body { font-family: Arial; }
        .form-row { margin-bottom: 10px; }
        label { display:block; font-weight:bold; }
        input { width:100%; padding:5px; }
        .btn { padding:5px 10px; border:1px solid #333; }
    </style>
</head>
<body>

<c:set var="isEdit" value="${not empty item}" />

<h1>
    <c:choose>
        <c:when test="${isEdit}">Edit Color</c:when>
        <c:otherwise>Add Color</c:otherwise>
    </c:choose>
</h1>

<a href="${pageContext.request.contextPath}/admin/color">&lt; Back</a>

<form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/color/update' : '/admin/color/insert'}">

    <c:if test="${isEdit}">
        <input type="hidden" name="id" value="${item.id}">
    </c:if>

    <div class="form-row">
        <label>Name</label>
        <input type="text" name="name" value="${isEdit ? item.name : ''}" required>
    </div>

    <div class="form-row">
        <label>Hex Code</label>
        <input type="text" name="hexCode" value="${isEdit ? item.hexCode : ''}" placeholder="#FFFFFF" required>
    </div>

    <button class="btn" type="submit">${isEdit ? 'Update' : 'Create'}</button>

</form>
</body>
</html>
