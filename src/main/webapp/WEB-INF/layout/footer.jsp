<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<footer>
    <div class="container">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">

        <p>© PhoneStore - All rights reserved. <%= new java.util.Date().getYear() + 1900 %></p>
    </div>
</footer>
</body>
</html>