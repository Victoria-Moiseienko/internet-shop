<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
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
<h5 style="color: grey">${message}</h5>
<h1>Please, provide new product details</h1>
<form method="post" action="${pageContext.request.contextPath}/products/add">
    Product Name:<input type="text" name="productName">
    Price:<input type="number" name="price">
    <button type="submit">Add</button>
</form>
</body>
</html>