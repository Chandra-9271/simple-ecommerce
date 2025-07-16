<%@ page import="java.util.List" %>
<%@ page import="com.se.models.Order" %>
<%@ page import="com.se.models.OrderItem" %>
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>
<html>
<head>
    <title>Your Orders</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f2f2;
            margin: 0;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .order-card {
        width:500px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.15);
            padding: 20px auto;
            padding: 20px;
            margin-bottom: 20px;
        }
        .order-header {
            font-weight: bold;
            margin-bottom: 10px;
            color: #4CAF50;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 6px 10px;
            text-align: left;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        a {
            display: inline-block;
            margin: 10px 5px;
            color: #4CAF50;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h2>Your Orders</h2>
<% if (orders != null && !orders.isEmpty()) { 
       for (Order o : orders) { %>
    <div class="order-card">
        <div class="order-header">
            Order ID: <%= o.getId() %> | Date: <%= o.getCreatedAt() %> | Total: <%= o.getTotalAmount() %>
        </div>
        <table>
            <tr>
                <th>Product Name</th><th>Price</th><th>Quantity</th>
            </tr>
<%
            List<OrderItem> items = o.getItems();
            if (items != null) {
                for (OrderItem item : items) {
%>
            <tr>
                <td><%= item.getProductName() %></td>
                <td><%= item.getPrice() %></td>
                <td><%= item.getQuantity() %></td>
            </tr>
<%          }
            } 
%>
        </table>
    </div>
<%   }
   } else { %>
    <p style="text-align:center; color:#888;">You have no orders yet.</p>
<% } %>
    <p style="text-align:center;">
        <a href="<%= request.getContextPath() %>/products">Back to Products</a> | 
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </p>
</body>
</html>
