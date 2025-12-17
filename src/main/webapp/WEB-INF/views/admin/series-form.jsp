<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="isEdit" value="${not empty item}" />

<h1>
    <c:choose>
        <c:when test="${isEdit}">Edit Series</c:when>
        <c:otherwise>Add Series</c:otherwise>
    </c:choose>
</h1>

<a href="${pageContext.request.contextPath}/admin/series">&lt; Back</a>
<br/><br/>

<form method="post" action="${pageContext.request.contextPath}${isEdit ? '/admin/series/update' : '/admin/series/insert'}">

    <c:if test="${isEdit}">
        <input type="hidden" name="id" value="${item.id}">
    </c:if>

    <div class="form-row">
        <label>Name</label>
        <input type="text" name="name" value="${isEdit ? item.name : ''}" required>
    </div>

    <button class="btn" type="submit">${isEdit ? 'Update' : 'Create'}</button>

</form>

