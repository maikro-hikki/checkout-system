package com.maikro.checkoutSystem.model;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.Entity;

@Entity
public class Customer extends User {
	
	//customer's shopping basket
	private Basket basket;

	public Customer() {
		super();
	}

	public Customer(long userId, String username, String password, UserType userType) {
		super(userId, username, password, userType);
	}

	public Customer(Basket basket) {
		super();
		this.basket = basket;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

}
