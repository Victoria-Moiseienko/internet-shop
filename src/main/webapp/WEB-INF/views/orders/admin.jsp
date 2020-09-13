<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders for admin</title>
</head>
<body>
<table>
    <tr>
        <th><a href="${pageContext.request.contextPath}/users/registration">Registration</a></th>
        <th><a href="${pageContext.request.contextPath}/users/all">User list</a></th>
        <th><a href="${pageContext.request.contextPath}/products/add">Add product</a></th>
        <th><a href="${pageContext.request.contextPath}/products/all">Product list</a></th>
        <th><a href="${pageContext.request.contextPath}/products/admin/all">Product list Admin</a></th>
        <th><a href="${pageContext.request.contextPath}/carts">Shopping cart</a></th>
        <th><a href="${pageContext.request.contextPath}/orders/user">Orders</a></th>
        <th><a href="${pageContext.request.contextPath}/orders/admin">Orders Admin</a></th>
        <th><a href="${pageContext.request.contextPath}/insertdata">Insert Data</a></th>
    </tr>
</table>
<h1>Orders for admin</h1>
<c:forEach var="order" items="${orders}">
    <table border="1">
        <tr>
            <td>
                <c:out value="Order num  ${order.id}"/>
            </td>
            <td>
                <c:out value="User id  ${order.userId}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/details?orderId=${order.id}"> details </a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/delete?orderId=${order.id}"> delete </a>
            </td>
        </tr>
    </table>
</c:forEach>
</body>
</html>
