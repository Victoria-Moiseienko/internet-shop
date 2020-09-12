<%--
  Created by IntelliJ IDEA.
  User: victo
  Date: 11.09.2020
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Insert Data</title>
</head>
<body>
<table>
    <tr>
        <th><a href="${pageContext.request.contextPath}/users/registration">Registration</a></th>
        <th><a href="${pageContext.request.contextPath}/users/all">User list</a></th>
        <th><a href="${pageContext.request.contextPath}/products/add">Add product</a></th>
        <th><a href="${pageContext.request.contextPath}/products/showproducts">Product list</a></th>
        <th><a href="${pageContext.request.contextPath}/carts">Shopping cart</a></th>
    </tr>
</table>

<form method="post" action="${pageContext.request.contextPath}/insertdata">
    <button type="submit">Insert Data</button>
</form>
</body>
</html>
