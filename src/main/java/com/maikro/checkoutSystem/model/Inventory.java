package com.maikro.checkoutSystem.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Inventory {
	
	@Id
	@GeneratedValue
	private long inventoryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_productId")
	private Product product;
	
	@OneToMany(mappedBy = "inventory", fetch = FetchType.LAZY)
	private List<BasketInventory> basketInventory;

	public Inventory() {
	}

	public Inventory(long inventoryId, Product product, List<BasketInventory> basketInventory) {
		super();
		this.inventoryId = inventoryId;
		this.product = product;
		this.basketInventory = basketInventory;
	}

	public long getInventoryId() {
		return inventoryId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<BasketInventory> getBasketInventory() {
		return basketInventory;
	}

	public void setBasketInventory(List<BasketInventory> basketInventory) {
		this.basketInventory = basketInventory;
	}

}
