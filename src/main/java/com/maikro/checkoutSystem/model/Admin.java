package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends UserClass {
	
	//products added by the admin user
	@OneToMany(mappedBy = "admin")
	private List<Product> products;
	
	//discounts added by the admin user
	@OneToMany(mappedBy = "admin")
	private List<Discount> discount;

	public Admin() {
		super();
	}

	public Admin(UserType userType) {
		super(userType);
	}

	public Admin(String username, String password, String firstName, String lastName, UserType userType) {
		super(username, password, firstName, lastName, userType);
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Discount> getDiscount() {
		return discount;
	}

	public void setDiscount(List<Discount> discount) {
		this.discount = discount;
	}

}
