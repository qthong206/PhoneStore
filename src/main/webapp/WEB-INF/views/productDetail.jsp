<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="pageTitle" value="${product.name}" scope="request"/>
<jsp:include page="/WEB-INF/layout/header.jsp" />

<link rel="stylesheet" href="<c:url value='/css/productDetail.css'/>">

<main class="container">
    <div class="product-detail-container">
        <div class="product-image">
            <img src="<c:url value='/${product.image}'/>" alt="${product.name}">
        </div>
        <div class="product-info">
            <h1>${product.name}</h1>
            <p class="price"><fmt:formatNumber value="${product.price}" type="number" pattern="#,##0"/> ₫</p>
            <p class="description">${product.description}</p>

            <form action="<c:url value='/cart'/>" method="post" class="add-to-cart-form">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="${product.id}">
                <div class="quantity-selector">
                    <label for="quantity">Số lượng:</label>
                    <input type="number" id="quantity" name="quantity" value="1" min="1" max="10">
                </div>
                <button type="submit" class="btn">Thêm vào giỏ</button>
            </form>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />