package com.maikro.checkoutSystem.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Basket {

	@Id
	@GeneratedValue
	private long basketId;

	// product in the basket
	@ManyToOne
	@JoinColumn(name = "fk_productId")
	private Product product;

	// the customer the basket belongs to
	@ManyToOne
	@JoinColumn(name = "fk_customerId")
	private Customer customer;

	// quantity of product in the basket
	private int quantity;

	public Basket() {
	}

	public Basket(Product product, Customer customer, int quantity) {
		this.product = product;
		this.customer = customer;
		this.quantity = quantity;
	}

	public long getBasketId() {
		return basketId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
