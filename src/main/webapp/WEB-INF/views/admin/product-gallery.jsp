<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<h1>THÊM ẢNH SẢN PHẨM ${product.name}</h1>


<br/><br/>

<h3>Add new image</h3>
<form method="post" action="${pageContext.request.contextPath}/admin/product/gallery/add">
    <div class="form-row">
    <input type="hidden" name="productId" value="${product.id}">
    <input type="text" name="imageUrl" placeholder="Image URL" style="width:300px;">
    <button type="submit" class="btn">Add</button>
    </div>
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


