package com.se.dao;

import com.se.models.Cart;
import com.se.models.CartItem;
import com.se.models.Product;
import com.se.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // Create new cart for user
	public Cart createCart(int userId) {
	    String sql = "INSERT INTO cart (user_id) VALUES (?)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        stmt.setInt(1, userId);
	        stmt.executeUpdate();
	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                return new Cart(rs.getInt(1), userId);
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return null;
	}


    // Get user's cart by userId
	public Cart getCartByUserId(int userId) {
	    Cart cart = null;
	    String sql = "SELECT id FROM cart WHERE user_id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                int cartId = rs.getInt("id");
	                cart = new Cart(cartId, userId);
	                // NEW: load items from cart_items table:
	                List<CartItem> items = getItemsByCartId(cartId);
	                cart.setItems(items);
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return cart;
	}



    // Add item to cart
    public void addItem(int cartId, int productId, int quantity) {
        String sql = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)" 
        		+ "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update quantity of item
    public void updateItem(int cartItemId, int productId,int quantity) {
        String sql = "UPDATE cart_items SET quantity=? WHERE id=? and product_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            ps.setInt(3, productId);
            
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove item from cart
    public void removeItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartItemId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all items in cart
    public List<CartItem> getItemsByCartId(int cartId) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT ci.id, ci.product_id, ci.quantity, " +
                     "p.name, p.price, p.description, p.category " +
                     "FROM cart_items ci JOIN products p ON ci.product_id = p.id " +
                     "WHERE ci.cart_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int itemId = rs.getInt("id");
                    int productId = rs.getInt("product_id");
                    int quantity = rs.getInt("quantity");

                    // Create product object:
                    Product product = new Product(
                        productId,
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getString("category")
                    );

                    // Create CartItem & set product:
                    CartItem item = new CartItem(itemId, productId, quantity);
                    item.setProduct(product);

                    items.add(item);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return items;
    }

    
    public void clearCart(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }


}

