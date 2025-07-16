<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%
    String username = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    if (username == null) response.sendRedirect("login");
%>
<html>
<head>
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .home-box {
            background: #fff;
            padding: 30px 40px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.15);
            text-align: center;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        a {
            display: block;
            margin: 10px 0;
            color: #4CAF50;
            text-decoration: none;
            font-size: 16px;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="home-box">
    <h2>Welcome, <%= username %>! (Role: <%= role %>)</h2>
    <a href="${pageContext.request.contextPath}/products">View Products</a>
    <a href="${pageContext.request.contextPath}/cart">View Cart</a>
    <a href="logout">Logout</a>
</div>
</body>
</html>
