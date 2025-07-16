package com.se.controllers;

import com.se.dao.CartDAO;
import com.se.dao.OrderDAO;
import com.se.dao.ProductDAO;
import com.se.models.Cart;
import com.se.models.CartItem;
import com.se.models.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/orders", "/orders/place"})
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private CartDAO cartDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        cartDAO = new CartDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("userId");
        String role = (String) request.getSession().getAttribute("role");

        List<Order> orders;
        if ("ADMIN".equals(role)) {
            // Admin: get all orders
            orders = orderDAO.getAllOrders();
        } else {
            // Customer: get own orders
            orders = orderDAO.getOrdersByUserId(userId);
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("jsp/orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/orders/place":
                handlePlaceOrder(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/orders");
        }
    }
    
    private void handlePlaceOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            int userId = (Integer) session.getAttribute("userId");

            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                orderDAO.placeOrder(cart);   // now pass cart
                cartDAO.clearCart(cart.getId()); // optional: clear cart after order
            }
        }
        response.sendRedirect(request.getContextPath() + "/orders"); // show order history or confirmation
    }

}

