package com.maikro.checkoutSystem.model;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends UserClass {

	public Admin() {
		super();
		this.setUserType(UserType.ADMIN);
	}

	public Admin(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
		this.setUserType(UserType.ADMIN);
	}

}
