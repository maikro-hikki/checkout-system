package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.constants.UserType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.model.UserClass;
import com.maikro.checkoutSystem.repository.UserClassRepo;

@Service
public class UserClassService {

	@Autowired
	private UserClassRepo userClassRepo;
	
	public Optional<UserClass> findByUserId(long userId) {
		return userClassRepo.findById(userId);
	}

	public Admin addAdminUser(Admin admin) {
		
		if (findByUserId(admin.getUserId()).isPresent() || usernameExist(admin.getUsername())) {
			
			return null;
		}
		
		return userClassRepo.save(admin);
	}

	public Customer addCustomerUser(Customer customer) {
		System.out.println(customer.getUsername());
		if (findByUserId(customer.getUserId()).isPresent() || usernameExist(customer.getUsername())) {
			
			return null;
		}
		
		return userClassRepo.save(customer);
	}
	
	public Admin createAdminUser(String username, String password, String firstName, String lastName) {
		
		Admin admin = new Admin(username, password, firstName, lastName, UserType.ADMIN);
		
		return userClassRepo.save(admin);
	}

	public int removeUserById(long userId) {
		
		if (findByUserId(userId).isPresent()) {
			
			userClassRepo.deleteById(userId);
			return 1;
		}
		
		return -1;
	}

	public boolean isAdmin(long userId) {

		Optional<UserClass> user = userClassRepo.findById(userId);

		if (user.isPresent()) {
			if (user.get().getUserType() == UserType.ADMIN) {
				return true;
			}
		}

		return false;
	}

	public boolean isCustomer(long userId) {

		Optional<UserClass> user = userClassRepo.findById(userId);

		if (user.isPresent()) {
			if (user.get().getUserType() == UserType.CUSTOMER) {
				return true;
			}
		}

		return false;
	}
	
	public boolean usernameExist(String username) {
		
		return userClassRepo.findByUsername(username).isPresent();
	}

}
