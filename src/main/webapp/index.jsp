<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Chuyển hướng trình duyệt từ Context Path gốc sang HomeServlet
    response.sendRedirect(request.getContextPath() + "/home");
%>