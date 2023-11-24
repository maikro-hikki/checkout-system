package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.repository.CustomerRepo;

/**
 * Service class for managing customer child class operations.
 */
@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	/**
	 * Retrieves a customer by their user ID.
	 *
	 * @param userId the ID of the user
	 * @return an Optional containing the Customer if found, or an empty Optional if not found
	 */
	public Optional<Customer> findByUserId(long userId){
		return customerRepo.findById(userId);
	}

	/**
	 * Adds a new customer user.
	 *
	 * @param customer the customer to be added
	 * @return the added Customer
	 */
	public Customer addCustomerUser(Customer customer) {
		return customerRepo.save(customer);
	}
	
	/**
	 * Checks if a customer with the given user ID exists.
	 *
	 * @param userId the ID of the user
	 * @return true if the customer exists, false otherwise
	 */
	public boolean customerExist(long userId) {
		
		if (customerRepo.findById(userId).isPresent()) {
			return true;
		}
		
		return false;
	}

}
