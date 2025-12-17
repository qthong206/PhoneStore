<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<h1>Manage Colors</h1>

<a class="btn" href="${pageContext.request.contextPath}/admin/color/add">+ Add Color</a>
<br/><br/>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Hex</th>
        <th>Preview</th>
        <th>Actions</th>
    </tr>

    <c:forEach var="c" items="${colors}">
        <tr>
            <td>${c.id}</td>
            <td>${c.name}</td>
            <td>${c.hexCode}</td>

            <td>
                <div class="color-box" style="background:${c.hexCode};"></div>
            </td>

            <td>
                <a class="btn" href="${pageContext.request.contextPath}/admin/color/edit?id=${c.id}">Edit</a>
                <a class="btn" onclick="return confirm('Delete color?')"
                   href="${pageContext.request.contextPath}/admin/color/delete?id=${c.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>


