package com.se.dao;

import com.se.models.Cart;
import com.se.models.CartItem;
import com.se.models.Order;
import com.se.models.OrderItem;
import com.se.utils.DBConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

	public void placeOrder(Cart cart) {
	    String insertOrderSql = "INSERT INTO orders (user_id, total_amount) VALUES (?, ?)";
	    String insertItemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
	    try (Connection conn = DBConnection.getConnection()) {
	        conn.setAutoCommit(false); // start transaction

	        // compute total:
	        BigDecimal total = BigDecimal.ZERO;
	        for (CartItem item : cart.getItems()) {
	            total = total.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
	        }

	        // insert order:
	        int orderId = -1;
	        try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
	            orderStmt.setInt(1, cart.getUserId());
	            orderStmt.setBigDecimal(2, total);
	            orderStmt.executeUpdate();
	            try (ResultSet rs = orderStmt.getGeneratedKeys()) {
	                if (rs.next()) {
	                    orderId = rs.getInt(1);
	                }
	            }
	        }

	        // insert items:
	        try (PreparedStatement itemStmt = conn.prepareStatement(insertItemSql)) {
	            for (CartItem item : cart.getItems()) {
	                itemStmt.setInt(1, orderId);
	                itemStmt.setInt(2, item.getProductId());
	                itemStmt.setInt(3, item.getQuantity());
	                itemStmt.setBigDecimal(4, item.getProduct().getPrice());
	                itemStmt.addBatch();
	            }
	            itemStmt.executeBatch();
	        }

	        conn.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    private BigDecimal calculateCartTotal(Cart cart) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            total = total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return total;
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id=? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("created_at"),
                    rs.getBigDecimal("total_amount")
                );
                // ✅ set items:
                order.setItems(getItemsByOrderId(order.getId()));

                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, user_id, total_amount, created_at FROM orders ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("created_at"),
                    rs.getBigDecimal("total_amount")
                );
                // ✅ set items:
                order.setItems(getItemsByOrderId(order.getId()));

                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    private List<OrderItem> getItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT id, product_id, quantity, price FROM order_items WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int quantity = rs.getInt("quantity");
                    BigDecimal price = rs.getBigDecimal("price");

                    // get product name from products table:
                    String productName = getProductNameById(rs.getInt("product_id"));
                    OrderItem item = new OrderItem(id, productName, quantity, price);
                    items.add(item);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return items;
    }
    
    private String getProductNameById(int productId) {
        String name = "";
        String sql = "SELECT name FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return name;
    }

}
