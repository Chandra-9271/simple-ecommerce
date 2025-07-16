package com.se.controllers;

import com.se.dao.UserDAO;
import com.se.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// Map multiple URLs: /login, /register, /logout
@WebServlet(urlPatterns = {"/login", "/register", "/logout"})
public class AuthServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(); // initialize DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/login":
                request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
                break;
            case "/register":
                request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
                break;
            case "/logout":
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect("login");
                break;
            default:
                response.sendRedirect("login");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/register":
                handleRegister(request, response);
                break;
            default:
                response.sendRedirect("login");
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.login(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            // Redirect to home page
            response.sendRedirect("jsp/home.jsp");
        } else {
            request.setAttribute("error", "Invalid username or password!");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role"); // "CUSTOMER" or "ADMIN"

        // Check if user exists
        if (userDAO.getUserByUsername(username) != null) {
            request.setAttribute("error", "Username already exists!");
            request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
            return;
        }

        User newUser = new User(0, username, password, role);
        boolean success = userDAO.registerUser(newUser);

        if (success) {
            // After register, redirect to login
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Registration failed!");
            request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
        }
    }
}
