<%--
  Created by IntelliJ IDEA.
  User: victo
  Date: 09.09.2020
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Hello. Please, provide your users details</h1>
<h4 style="color: red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/users/registration">
    <p>Login:<input type="text" name="login"></p>
    <p>Password:<input type="password" name="pwd"></p>
    <p>Confirm password:<input type="password" name="pwd-repeat"></p>
    <button type="submit">Register</button>
</form>
</body>
</html>
