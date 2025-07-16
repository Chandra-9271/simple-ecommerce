package com.se.models;

import java.math.BigDecimal;

public class CartItem {
	private int id;
	private int cartId;
	private int productId;
	private int quantity;
	private BigDecimal price;
	private Product product;

	public CartItem() {}
	public CartItem(int id, int cartId, int productId, int quantity) {
		this.id = id;
		this.cartId = cartId;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	

	public CartItem(int id, int productId, int quantity) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
	}
	public CartItem(int id, int cartId, int productId, int quantity, BigDecimal price) {
		this.id = id; this.cartId = cartId; this.productId = productId;
		this.quantity = quantity; this.price = price;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() { 
		return price; 
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	

}

