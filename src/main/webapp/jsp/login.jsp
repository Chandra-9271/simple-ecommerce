<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-box {
            background: #fff;
            padding: 25px 30px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.15);
            width: 300px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 10px;
            color: #333;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 7px;
            margin-top: 4px;
            margin-bottom: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 7px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        p {
            text-align: center;
            font-size: 14px;
        }
        a {
            color: #4CAF50;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 8px;
        }
    </style>
</head>
<body>
<div class="login-box">
    <h2>Login</h2>
    <form method="post" action="login">
        <label>Username:
            <input type="text" name="username" required>
        </label>
        <label>Password:
            <input type="password" name="password" required>
        </label>
        <input type="submit" value="Login">
    </form>
    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <div class="error"><%= error %></div>
    <% } %>
    <p>Don't have an account? <a href="register">Register here</a></p>
</div>
</body>
</html>
