package com.maikro.checkoutSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BasketInventory {
	
	@Id
	@GeneratedValue
	private long basketInventoryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basketId")
	private Basket basket;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inventoryId")
	private Inventory inventory;

	public BasketInventory() {
	}

	public BasketInventory(long basketInventoryId, Basket basket, Inventory inventory) {
		super();
		this.basketInventoryId = basketInventoryId;
		this.basket = basket;
		this.inventory = inventory;
	}

	public long getBasketInventoryId() {
		return basketInventoryId;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
