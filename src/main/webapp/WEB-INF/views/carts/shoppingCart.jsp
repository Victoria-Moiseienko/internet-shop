<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
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
<h1>Shopping Cart</h1>
<h4 style="color: red">${message}</h4>
<table border="1">
    <tr>
        <th>id</th>
        <th>Name</th>
        <th>Price</th>
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
                <a href="${pageContext.request.contextPath}/carts/product/delete?productId=${product.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<form method="post" action="${pageContext.request.contextPath}/orders/create">
    <button type="submit">Confirm order</button>
</form>
</body>
</html>
