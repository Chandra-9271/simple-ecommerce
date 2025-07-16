package com.se.models;
import java.math.BigDecimal;

public class OrderItem {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private String productName;


    public OrderItem() {}
    public OrderItem(int id, int orderId, int productId, int quantity, BigDecimal price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
    
    public OrderItem(int id, int orderId, int productId, int quantity, BigDecimal price, String productName) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
    }

	public OrderItem(int id, String productName,int quantity, BigDecimal price ) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.productName = productName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getProductName() {
	    return productName;
	}
	public void setProductName(String productName) {
	    this.productName = productName;
	}


    
}
