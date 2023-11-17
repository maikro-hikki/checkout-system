package com.maikro.checkoutSystem.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Basket {
	
	@Id
	@GeneratedValue
	private long basketId;
	
	@OneToOne
	@JoinColumn(name = "fk_customerId", referencedColumnName = "userId")
	private Customer customer;
	
	//products inside the basket
	@OneToMany(mappedBy = "basket", fetch = FetchType.LAZY)
	private List<BasketInventory> basketInventory;

	public Basket() {
	}

	public Basket(long basketId, List<BasketInventory> basketInventory) {
		this.basketId = basketId;
		this.basketInventory = basketInventory;
	}

	public long getBasketId() {
		return basketId;
	}

	public List<BasketInventory> getBasketInventory() {
		return basketInventory;
	}

	public void setBasketInventory(List<BasketInventory> basketInventory) {
		this.basketInventory = basketInventory;
	}

}
