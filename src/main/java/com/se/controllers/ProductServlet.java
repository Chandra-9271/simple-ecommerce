package com.se.controllers;

import com.se.dao.ProductDAO;
import com.se.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = {"/products", "/products/add", "/products/update", "/products/delete"})
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        if ("/products".equals(action)) {
            List<Product> products = productDAO.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("jsp/products.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/products/add":
                handleAdd(request, response);
                break;
            case "/products/update":
                handleUpdate(request, response);
                break;
            case "/products/delete":
                handleDelete(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/products");
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String category = request.getParameter("category");

        Product product = new Product(0, name, description, price, category);
        productDAO.addProduct(product);

        response.sendRedirect(request.getContextPath() + "/products");
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String category = request.getParameter("category");

        Product product = new Product(id, name, description, price, category);
        productDAO.updateProduct(product);

        response.sendRedirect(request.getContextPath() + "/products");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);

        response.sendRedirect(request.getContextPath() + "/products");
    }
}
