package com.se.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce";
    private static final String USER = "root"; // change to your DB username
    private static final String PASSWORD = "chandra"; // change to your DB password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

