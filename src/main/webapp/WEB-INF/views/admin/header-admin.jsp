<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">
    <div class="header-l">

        <div class="header-left" >
            <img src="<c:url value='/images/logo.png'/>" alt="PhoneStore Logo" class="header-logo">
        </div>
        <div class="header-left">
            <a href="${pageContext.request.contextPath}/home">
                <i class="fa-solid fa-house"></i>
                <span>Trang Chủ</span>
            </a>
        </div>
    </div>

</header>

