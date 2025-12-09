<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Admin - Brands</title>
    <style>
        body { font-family: Arial; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; }
        th { background: #f0f0f0; }
        .btn { padding: 5px 10px; border: 1px solid #333; text-decoration: none; }
    </style>
</head>
<body>

<h1>Brand Management</h1>

<a href="${pageContext.request.contextPath}/admin/brand/add" class="btn">+ Add Brand</a>
<br/><br/>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Slug</th>
        <th>Logo</th>
        <th>CategoryId</th>
        <th>Actions</th>
    </tr>

    <c:forEach var="b" items="${brands}">
        <tr>
            <td>${b.id}</td>
            <td>${b.name}</td>
            <td>${b.slug}</td>
            <td>${b.logoUrl}</td>
            <td>${b.categoryId}</td>
            <td>
                <a class="btn" href="${pageContext.request.contextPath}/admin/brand/edit?id=${b.id}">Edit</a>
                <a class="btn" href="${pageContext.request.contextPath}/admin/brand/delete?id=${b.id}"
                   onclick="return confirm('Delete this brand?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
