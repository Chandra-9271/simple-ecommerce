package com.se.controllers;

import com.se.dao.CartDAO;
import com.se.dao.ProductDAO;
import com.se.models.Cart;
import com.se.models.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/remove"})
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO;

    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        Cart cart = cartDAO.getCartByUserId(userId);
        if (cart == null) {
            cart = cartDAO.createCart(userId);
        }

        if (cart != null) {
            request.setAttribute("items", cart.getItems());
        } else {
            request.setAttribute("items", new ArrayList<>());
        }

        request.getRequestDispatcher("jsp/cart.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/cart/add":
                handleAddToCart(request, response);
                break;
            case "/cart/update":
                handleUpdateCartItem(request, response);
                break;
            case "/cart/remove":
                handleRemoveFromCart(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    
    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int userId = (Integer) request.getSession().getAttribute("userId");
        
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Cart cart = cartDAO.getCartByUserId(userId);
        if (cart == null) {
            cart = cartDAO.createCart(userId);
        }
        if (cart != null) {
            cartDAO.addItem(cart.getId(), productId, quantity);
        }
        response.sendRedirect(request.getContextPath() + "/cart");

    }
    
    private void handleUpdateCartItem(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        // call DAO to update quantity
        cartDAO.updateItem(cartItemId, productId, quantity);

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

        // call DAO to delete item
        cartDAO.removeItem(cartItemId);

        response.sendRedirect(request.getContextPath() + "/cart");
    }


}

