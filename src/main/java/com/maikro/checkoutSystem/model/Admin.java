package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends UserClass {
	
//	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
//	private List<Product> products;

	public Admin() {
		super();
		this.setUserType(UserType.ADMIN);
	}

	public Admin(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
		this.setUserType(UserType.ADMIN);
	}

}
