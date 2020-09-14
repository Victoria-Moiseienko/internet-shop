<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<h1>Login page</h1>
<h4 style="color: red">${message}</h4>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label>Login</label><input type="text" name="login"></p>
    <label>Password</label><input type="password" name="pwd"></p>
    <button type="submit">Login</button>
</form>
</body>
</html>
