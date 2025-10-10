<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/layout/header.jsp" />

<h2>Danh sách sản phẩm</h2>
<div class="container">
    <c:forEach var="p" items="${productList}">
        <div class="product">
            <img src="<c:url value='/${p.image}'/>" alt="${p.name}" width="200">
            <h3>${p.name}</h3>
            <p>${p.price} ₫</p>
        </div>
    </c:forEach>
</div>

<jsp:include page="/WEB-INF/layout/footer.jsp" />
