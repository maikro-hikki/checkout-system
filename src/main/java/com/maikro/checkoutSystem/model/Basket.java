package com.maikro.checkoutSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Basket {
	
	@Id
	@GeneratedValue
	private long basketId;
	
	//product in the basket
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_productId")
	private Product product;
	
	//quantity of product in the basket
	private int quantity;

	public Basket() {
	}

	public Basket(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public long getBasketProductId() {
		return basketId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
