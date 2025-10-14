<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty pageTitle ? pageTitle : "PhoneStore"}</title>

    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>
</head>
<body>

<header>
    <div class="container header-container">
        <%-- KHỐI BÊN TRÁI: LOGO VÀ DANH MỤC --%>
        <div class="header-left">
            <div id="branding">
                <a href="<c:url value='/home'/>">
                    <img src="<c:url value='/images/logo.png'/>" alt="PhoneStore Logo" class="header-logo">
                </a>
            </div>
            <a href="#" class="category-button">
                <i class="fa-solid fa-bars category-icon"></i>
                <span>Danh mục</span>
            </a>
        </div>

        <%-- KHỐI Ở GIỮA: THANH TÌM KIẾM --%>
        <div class="search-bar">
            <input type="text" placeholder="Bạn cần tìm gì?">
            <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
        </div>

        <%-- KHỐI BÊN PHẢI: CÁC MỤC ĐIỀU HƯỚNG --%>
        <nav class="header-nav">
            <a href="#" class="nav-item">
                <i class="fa-solid fa-phone-volume nav-icon"></i>
                <span>Đặt hàng</span>
            </a>
            <a href="#" class="nav-item">
                <i class="fa-solid fa-box-archive nav-icon"></i>
                <span>Tra cứu đơn</span>
            </a>
            <a href="#" class="nav-item">
                <i class="fa-solid fa-gift nav-icon"></i>
                <span>Khuyến mãi</span>
            </a>
            <a href="<c:url value='/cart'/>" class="nav-item">
                <i class="fa-solid fa-cart-shopping nav-icon"></i>
                <span>Giỏ Hàng</span>
            </a>
            <a href="<c:url value='/user'/>" class="nav-item">
                <i class="fa-solid fa-user nav-icon"></i>
                <span>Tài Khoản</span>
            </a>
        </nav>
    </div>
</header>