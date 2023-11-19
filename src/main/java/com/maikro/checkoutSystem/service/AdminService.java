package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.repository.AdminRepo;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepo adminRepo;
	
	public Optional<Admin> findByUserId(long userId){
		return adminRepo.findById(userId);
	}
	
	public boolean customerExist(long userId) {
		
		if (adminRepo.findById(userId).isPresent()) {
			return true;
		}
		
		return false;
	}

}
