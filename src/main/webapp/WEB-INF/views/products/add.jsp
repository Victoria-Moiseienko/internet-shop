<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
<table>
    <tr>
        <th><a href="${pageContext.request.contextPath}/"> Home Page </a></th>
        <th><a href="${pageContext.request.contextPath}/login"> Login </a></th>
        <th><a href="${pageContext.request.contextPath}/users/registration"> Registration </a></th>
        <th><a href="${pageContext.request.contextPath}/users"> User list </a></th>
        <th><a href="${pageContext.request.contextPath}/products/add"> Add product </a></th>
        <th><a href="${pageContext.request.contextPath}/products"> Product list </a></th>
        <th><a href="${pageContext.request.contextPath}/products/manage"> Manage Products </a></th>
        <th><a href="${pageContext.request.contextPath}/shopping-cart/info"> Shopping cart </a></th>
        <th><a href="${pageContext.request.contextPath}/user/orders"> Orders </a></th>
        <th><a href="${pageContext.request.contextPath}/orders"> Orders Admin </a></th>
        <th><a href="${pageContext.request.contextPath}/inject"> Inject </a></th>
        <th><a href="${pageContext.request.contextPath}/logout"> Logout </a></th>
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
