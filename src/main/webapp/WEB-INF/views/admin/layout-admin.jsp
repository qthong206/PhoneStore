<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>TRANG ADMIN</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value='/css/admin-base.css'/>">
    <c:if test="${not empty pageCss}">
        <link rel="stylesheet" href="<c:url value='/css/${pageCss}'/>">
    </c:if>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>
</head>
<body>
<%@include file="header-admin.jsp"%>

<div class="layout">
    <%-- TRẢ LẠI VỊ TRÍ CŨ: File này chứa MENU BÊN TRÁI --%>
    <%@include file="footer-admin.jsp"%>

    <main class="content">
        <jsp:include page="${contentPage}"/>
    </main>
</div>

</body>
</html>