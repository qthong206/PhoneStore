<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Admin - Series</title>
  <style>
    body { font-family: Arial; }
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ccc; padding: 8px; }
    th { background: #f0f0f0; }
    .btn { padding: 5px 10px; border: 1px solid #333; }
  </style>
</head>
<body>

<h1>Manage Product Series</h1>

<a href="${pageContext.request.contextPath}/admin/series/add" class="btn">+ Add Series</a>
<br/><br/>

<table>
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Actions</th>
  </tr>

  <c:forEach var="s" items="${series}">
    <tr>
      <td>${s.id}</td>
      <td>${s.name}</td>
      <td>
        <a class="btn" href="${pageContext.request.contextPath}/admin/series/edit?id=${s.id}">Edit</a>
        <a class="btn" href="${pageContext.request.contextPath}/admin/series/delete?id=${s.id}"
           onclick="return confirm('Delete series?')">Delete</a>
      </td>
    </tr>
  </c:forEach>

</table>

</body>
</html>
