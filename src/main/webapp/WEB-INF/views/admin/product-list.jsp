<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Set locale để format số tiền đúng kiểu Việt Nam --%>
<fmt:setLocale value="vi_VN"/>

<div class="admin-header">
    <h1>QUẢN LÝ SẢN PHẨM</h1>
    <a href="${pageContext.request.contextPath}/admin/product/add" class="btn btn-primary">
        <i class="fa-solid fa-plus"></i> Thêm sản phẩm mới
    </a>
</div>

<br/>

<c:forEach var="entry" items="${productMap}">
    <div class="brand-section" style="margin-bottom: 30px;">
        <h3>Hãng: ${entry.key.name}</h3>

        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên sản phẩm</th>
                    <th>Model / Storage</th>
                    <th>Giá Gốc</th>
                    <th>Giá Sau Giảm</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${entry.value}">
                    <tr>
                        <td>${p.id}</td>
                        <td><strong>${p.name}</strong></td>
                        <td>${p.model} <br/> <small>${p.storage}</small></td>

                        <td style="text-decoration: line-through; color: #888;">
                            <fmt:formatNumber value="${p.price}" type="number"/> ₫
                        </td>

                        <td style="color: #d70018; font-weight: bold;">
                            <fmt:formatNumber value="${p.salePrice}" type="number"/> ₫
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${p.status == 1}">
                                    <span class="badge badge-success" style="background: green; color: white; padding: 4px 8px; border-radius: 4px;">Active</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-danger" style="background: red; color: white; padding: 4px 8px; border-radius: 4px;">Hidden</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/admin/product/edit?id=${p.id}">Edit</a>

                            <a class="btn btn-sm btn-info" href="${pageContext.request.contextPath}/admin/product/gallery?productId=${p.id}" style="background-color: #17a2b8; color: white;">Ảnh</a>

                            <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/admin/product/toggle?id=${p.id}" style="background-color: #6c757d; color: white;">Toggle</a>

                            <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/admin/product/delete?id=${p.id}" onclick="return confirm('Bạn có chắc muốn xóa không?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:forEach>