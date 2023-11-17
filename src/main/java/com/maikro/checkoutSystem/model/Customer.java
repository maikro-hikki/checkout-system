package com.maikro.checkoutSystem.model;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Customer extends UserClass {
	
	//customer's shopping basket
	@OneToOne
	@JoinColumn(name = "fk_basketId", referencedColumnName = "basketId")
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
