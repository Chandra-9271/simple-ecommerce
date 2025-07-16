package com.se.dao;

import com.se.models.Product;
import com.se.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getString("category")
                );
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, category) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setString(4, product.getCategory());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id=?";
        Product product = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getString("category")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name=?, description=?, price=?, category=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setString(4, product.getCategory());
            ps.setInt(5, product.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

