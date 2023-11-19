package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.repository.CustomerRepo;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	public Optional<Customer> findByUserId(long userId){
		return customerRepo.findById(userId);
	}
	
	public boolean customerExist(long userId) {
		
		if (customerRepo.findById(userId).isPresent()) {
			return true;
		}
		
		return false;
	}

}
