package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Customer extends UserClass {
	
	//customer's shopping basket
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_customerId")
	private List<Basket> basket;

	public Customer() {
		super();
	}
	
	public Customer(UserType userType) {
		super(userType);
	}

	public Customer(String username, String password, String firstName, String lastName, UserType userType) {
		super(username, password, firstName, lastName, userType);
	}

	public Customer(List<Basket> basket) {
		super();
		this.basket = basket;
	}

	public List<Basket> getBasket() {
		return basket;
	}

	public void setBasketProduct(List<Basket> basket) {
		this.basket = basket;
	}

}
