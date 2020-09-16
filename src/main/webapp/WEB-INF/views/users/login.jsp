<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login page</title>
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
<h1>Login page</h1>
<h4 style="color: red">${message}</h4>

<form action="${pageContext.request.contextPath}/login" method="post">
    <label>Login</label><input type="text" name="login"></p>
    <label>Password</label><input type="password" name="pwd"></p>
    <button type="submit">Login</button>
</form>
</body>
</html>
