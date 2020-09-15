<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<table>
    <tr>
        <th><a href="${pageContext.request.contextPath}/users/registration">Registration</a></th>
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
