<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%@ page import="com.se.models.Product, java.util.List"%>
<%
String role = (String) session.getAttribute("role");
List<Product> products = (List<Product>) request.getAttribute("products");

%>
<html>
<head>
<title>Products</title>
<style>
body {
    font-family: Arial, sans-serif;
    background: #f5f5f5;
    color: #333;
    padding: 20px;
}

h2, h3 {
    color: #2c3e50;
}

.product-container {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
}

.product-card {
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 15px;
    width: 220px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.product-card h3 {
    margin: 0 0 10px;
    font-size: 18px;
}

.product-card p {
    margin: 4px 0;
    font-size: 14px;
}

input[type="text"], input[type="number"] {
    padding: 5px;
    margin: 3px 0;
    width: 100%;
    box-sizing: border-box;
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

a {
    color: #4CAF50;
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
}
</style>

</head>
<body>
<h2>Product List</h2>

<div class="product-container">
<% for (Product p : products) { %>
    <div class="product-card">
        <h3><%= p.getName() %></h3>
        <p><%= p.getDescription() %></p>
        <p><strong>Price:</strong> ₹<%= p.getPrice() %></p>
        <p><strong>Category:</strong> <%= p.getCategory() %></p>

        <% if ("ADMIN".equals(role)) { %>
            <form method="post" action="<%= request.getContextPath() %>/products/delete" style="display:inline;">
                <input type="hidden" name="id" value="<%= p.getId() %>">
                <input type="submit" value="Delete">
            </form>

            <form method="post" action="<%= request.getContextPath() %>/products/update" style="display:inline;">
                <input type="hidden" name="id" value="<%= p.getId() %>">
                <input type="text" name="name" value="<%= p.getName() %>" required>
                <input type="text" name="description" value="<%= p.getDescription() %>">
                <input type="number" step="1" name="price" value="<%= p.getPrice() %>" required>
                <input type="text" name="category" value="<%= p.getCategory() %>">
                <input type="submit" value="Update">
            </form>
        <% } else { %>
            <form method="post" action="<%= request.getContextPath() %>/cart/add">
                <input type="hidden" name="productId" value="<%= p.getId() %>">
                <input type="number" name="quantity" value="1" min="1" style="width:50px;">
                <input type="submit" value="Add to Cart">
            </form>
        <% } %>
    </div>
<% } %>
</div>

<% if ("ADMIN".equals(role)) { %>
<h3>Add Product</h3>
<form method="post" action="${pageContext.request.contextPath}/products/add">
    <input type="text" name="name" placeholder="Name" required>
    <input type="text" name="description" placeholder="Description">
    <input type="number" step="0.01" name="price" placeholder="Price" required>
    <input type="text" name="category" placeholder="Category">
    <input type="submit" value="Add">
</form>
<% } %>

<p>
    <a href="cart">Go to Cart</a> | 
    <a href="logout">Logout</a>
</p>
</body>

</html>
