<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 11/25/2025
  Time: 8:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TRANG ADMIN</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="">
</head>
<body>
<%@include file="header-admin.jsp"%>
<div class="layout">
    <%@include file="footer-admin.jsp"%>
    <main class="content">
        <jsp:include page="${contentPage}"/>
    </main>

</div>

</body>
</html>
