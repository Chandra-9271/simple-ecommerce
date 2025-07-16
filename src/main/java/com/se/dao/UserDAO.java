package com.se.dao;

import com.se.models.User;
import com.se.utils.DBConnection;
import java.sql.*;

public class UserDAO {

    // Register user
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // In real apps, hash password!
            ps.setString(3, user.getRole());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Authenticate (login)
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password); // Compare plaintext; use hashing in production

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get user by username
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

