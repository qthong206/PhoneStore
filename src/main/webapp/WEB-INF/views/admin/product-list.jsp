<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<h1>QUẢN LÍ SẢN PHẨM </h1>


<br/><br/>

<c:forEach var="entry" items="${productMap}">
    <h3>Hãng: ${entry.key.name}</h3>

    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Model</th>
            <th>Storage</th>
            <th>Price</th>
            <th>Sale Price</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>

        <c:forEach var="p" items="${entry.value}">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>${p.model}</td>
                <td>${p.storage}</td>
                <td>${p.price}</td>
                <td>${p.salePrice}</td>

                <td>
                    <c:choose>
                        <c:when test="${p.status == 1}">
                            <span class="status-active">Active</span>
                        </c:when>
                        <c:otherwise>
                            <span class="status-hidden">Hidden</span>
                        </c:otherwise>
                    </c:choose>
                </td>

                <td>
                    <a class="btn" href="${pageContext.request.contextPath}/admin/product/edit?id=${p.id}">Edit</a>
                    <a class="btn" href="${pageContext.request.contextPath}/admin/product/delete?id=${p.id}" onclick="return confirm('Delete product?')">Delete</a>
                    <a class="btn" href="${pageContext.request.contextPath}/admin/product/toggle?id=${p.id}">Toggle</a>
                </td>
            </tr>
        </c:forEach>

    </table>
    <br/>
</c:forEach>

