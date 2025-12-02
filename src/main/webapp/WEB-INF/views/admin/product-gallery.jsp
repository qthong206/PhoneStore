<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Product Gallery</title>
    <style>
        body { font-family: Arial; }
        img { max-width: 150px; display:block; margin-bottom:5px; }
        .img-card { border:1px solid #ccc; padding:10px; margin:5px; display:inline-block; }
        .btn { padding:5px 10px; border:1px solid #333; text-decoration:none; }
    </style>
</head>
<body>

<h1>Gallery – ${product.name}</h1>

<a href="${pageContext.request.contextPath}/admin/product">&lt; Back to product list</a>
<br/><br/>

<h3>Add new image</h3>
<form method="post" action="${pageContext.request.contextPath}/admin/product/gallery/add">
    <input type="hidden" name="productId" value="${product.id}">
    <input type="text" name="imageUrl" placeholder="Image URL" style="width:300px;">
    <button type="submit" class="btn">Add</button>
</form>

<hr/>

<h3>Current images</h3>

<c:forEach var="url" items="${images}">
    <div class="img-card">
        <img src="${url}" alt="Image">
        <a class="btn"
           href="${pageContext.request.contextPath}/admin/product/gallery/delete?productId=${product.id}&url=${fn:escapeXml(url)}"
           onclick="return confirm('Delete this image?')">Delete</a>
    </div>
</c:forEach>

</body>
</html>

