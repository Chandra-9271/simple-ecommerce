package com.se.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

import java.util.List;

public class Order {
    private int id;
    private int userId;
    private Timestamp createdAt;
    private BigDecimal totalAmount;
    private List<OrderItem> items;   // new field


    public Order() {}
    public Order(int id, int userId, Timestamp createdAt, BigDecimal totalAmount) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }
    
    
    
	public Order(int id, Timestamp createdAt, BigDecimal totalAmount) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.totalAmount = totalAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<OrderItem> getItems() {
	    return items;
	}
	public void setItems(List<OrderItem> items) {
	    this.items = items;
	}

    
    
}

