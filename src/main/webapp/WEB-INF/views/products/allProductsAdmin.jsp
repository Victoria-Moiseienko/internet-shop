<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products for Admin</title>
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
<h1>all products page for admin</h1>
<table border="1">
    <tr>
        <th>id</th>
        <th>Name</th>
        <th>Price</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/products/delete?productId=${product.id}"> delete </a>
            </td>

        </tr>
    </c:forEach>
</table>
<h4>${message}</h4>

<form method="get" action="${pageContext.request.contextPath}/products/add">
    <button type="submit"> Add product </button>
</form>
</body>
</html>
