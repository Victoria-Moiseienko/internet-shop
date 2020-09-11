<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your orders</title>
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
<h1>user orders page</h1>
<c:forEach var="order" items="${orders}">
<table border="1">
    <tr>
        <td>
            <c:out value="Order num  ${order.id}"/>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/orders/details?orderId=${order.id}">details</a>
        </td>
    </tr>
</table>
</c:forEach>
</body>
</html>
