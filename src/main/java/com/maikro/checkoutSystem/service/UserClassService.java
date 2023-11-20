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
		return userClassRepo.save(admin);
	}

	public Customer addCustomerUser(Customer customer) {
		return userClassRepo.save(customer);
	}

	public void removeUserById(long userId) {
		userClassRepo.deleteById(userId);
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

}
