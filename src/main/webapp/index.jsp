<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>

<br>
<hr>
<br>

<form action="ch01/FirstServlet" method="get">
    <button type="submit">FirstServlet 실행 (age 등록)</button>
</form>

<br>
<hr>
<br>

<form action="ch01/config/age" method="get">
    <button type="submit">ServletConfigTest 실행 (age 불러오기)</button>
</form>

<br>
<hr>
<br>

<form action="ch02/method" method="get">
    <label>GET 요청 (datasKey): </label>
    <input type="text" name="datasKey" placeholder="예: name, age, address">
    <button type="submit">GET 요청 보내기</button>
</form>

<br>
<hr>
<br>

<form action="ch02/users" method="post">
    <label>POST user</label><br>
    <input type="text" name="username" placeholder="username"><br>
    <input type="password" name="password" placeholder="password"><br>
    <input type="text" name="name" placeholder="name"><br>
    <input type="email" name="email" placeholder="email"><br>
    <button type="submit">POST 요청 보내기</button>
</form>

<br>
<hr>
<br>

</body>
</html>