package com.maikro.checkoutSystem.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Basket {
	
	@Id
	@GeneratedValue
	private long basketId;
	
	//products inside the basket
	@OneToMany(mappedBy = "basket")
	private List<Product> products;

	public Basket() {
	}

	public Basket(long basketId, List<Product> products) {
		this.basketId = basketId;
		this.products = products;
	}

	public long getBasketId() {
		return basketId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
