<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.se.models.CartItem" %>
<%@ page import="com.se.models.Product" %>
<%
    List<CartItem> items = (List<CartItem>) request.getAttribute("items");
%>
<html>
<head>
    <title>Your Cart</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            color: #333;
            padding: 20px;
        }

        h2 {
            color: #2c3e50;
        }

        .cart-container {
            display: flex;
            flex-wrap: wrap;
            gap: 16px;
        }

        .cart-card {
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            width: 250px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .cart-card h3 {
            margin: 0 0 10px;
            font-size: 18px;
        }

        .cart-card p {
            margin: 4px 0;
            font-size: 14px;
        }

        input[type="number"] {
            width: 60px;
            padding: 4px;
            margin: 3px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            margin-top: 5px;
            padding: 6px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        form {
            margin: 2px 0;
        }

        a {
            color: #4CAF50;
            text-decoration: none;
            margin-right: 10px;
        }

        a:hover {
            text-decoration: underline;
        }

        .actions {
            display: flex;
            flex-direction: column;
        }
    </style>
</head>
<body>
<h2>Your Cart</h2>

<div class="cart-container">
<% if (items != null && !items.isEmpty()) {
    for (CartItem item : items) {
        Product product = item.getProduct();
%>
    <div class="cart-card">
        <h3><%= product.getName() %></h3>
        <p><strong>Price:</strong> ₹<%= product.getPrice() %></p>
        <p><strong>Quantity:</strong> <%= item.getQuantity() %></p>

        <div class="actions">
            <form method="post" action="<%= request.getContextPath() %>/cart/update">
                <input type="hidden" name="cartItemId" value="<%= item.getId() %>">
                <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                <input type="number" name="quantity" min="1" value="<%= item.getQuantity() %>">
                <input type="submit" value="Update">
            </form>

            <form method="post" action="<%= request.getContextPath() %>/cart/remove">
                <input type="hidden" name="cartItemId" value="<%= item.getId() %>">
                <input type="submit" value="Remove">
            </form>
        </div>
    </div>
<%  }
} else { %>
    <p>Your cart is empty.</p>
<% } %>
</div>

<form method="post" action="<%= request.getContextPath() %>/orders/place" style="margin-top: 15px;">
    <input type="submit" value="Checkout / Place Order">
</form>

<p>
    <a href="<%= request.getContextPath() %>/products">Back to Products</a>
    <a href="<%= request.getContextPath() %>/logout">Logout</a>
</p>
</body>
</html>
