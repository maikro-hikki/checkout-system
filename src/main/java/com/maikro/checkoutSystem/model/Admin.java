package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Admin extends User {
	
	//products added by the admin user
	@OneToMany(mappedBy = "admin")
	private List<Product> products;
	
	//discounts added by the admin user
	@OneToMany(mappedBy = "admin")
	private List<Discount> discounts;

	public Admin() {
		super();
	}

	public Admin(long userId, String username, String password, UserType userType) {
		super(userId, username, password, userType);
	}

	public Admin(List<Product> products, List<Discount> discounts) {
		super();
		this.products = products;
		this.discounts = discounts;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

}
