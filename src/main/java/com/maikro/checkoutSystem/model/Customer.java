package com.maikro.checkoutSystem.model;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Customer extends UserClass {

	public Customer() {
		super();
		this.setUserType(UserType.CUSTOMER);
	}

	public Customer(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
		this.setUserType(UserType.CUSTOMER);
	}

}
