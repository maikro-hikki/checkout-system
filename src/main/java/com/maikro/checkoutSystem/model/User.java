package com.maikro.checkoutSystem.model;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	
	@Id
	@GeneratedValue
	private long userId;
	
	private String username;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private UserType userType;
	
	public User() {
	}

	public User(long userId, String username, String password, UserType userType) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userType = userType;
	}

	public long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}	

}
