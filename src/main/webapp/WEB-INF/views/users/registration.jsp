<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<table>
    <tr>
        <th><a href="${pageContext.request.contextPath}/login">Login</a></th>
    </tr>
</table>
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
