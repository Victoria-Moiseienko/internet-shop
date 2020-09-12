<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<table>
    <tr>
        <th><a href="${pageContext.request.contextPath}/users/registration">Registration</a></th>
        <th><a href="${pageContext.request.contextPath}/users/all">User list</a></th>
        <th><a href="${pageContext.request.contextPath}/products/add">Add product</a></th>
        <th><a href="${pageContext.request.contextPath}/products/showproducts">Product list</a></th>
        <th><a href="${pageContext.request.contextPath}/products/admin">Product list Admin</a></th>
        <th><a href="${pageContext.request.contextPath}/carts">Shopping cart</a></th>
        <th><a href="${pageContext.request.contextPath}/orders/user">Orders</a></th>
        <th><a href="${pageContext.request.contextPath}/orders/admin">Orders Admin</a></th>
        <th><a href="${pageContext.request.contextPath}/insertdata">Insert Data</a></th>
    </tr>
</table>
<h1>all users page</h1>
<h4>${message}</h4>
<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th></th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/users/delete?userId=${user.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
