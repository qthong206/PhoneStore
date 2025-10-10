<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty param.pageTitle ? param.pageTitle : "PhoneStore"}</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<header>
    <div class="container">
        <div id="branding">
            <h1><a href="<%= request.getContextPath() %>/home">PhoneStore</a></h1>
        </div>
        <nav>
            <ul>
                <li><a href="<%= request.getContextPath() %>/home">Trang Chủ</a></li>
                <li><a href="<%= request.getContextPath() %>/cart">Giỏ Hàng</a></li>
            </ul>
        </nav>
    </div>
</header>
