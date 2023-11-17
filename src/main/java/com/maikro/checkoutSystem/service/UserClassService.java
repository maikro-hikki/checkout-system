package com.maikro.checkoutSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.repository.UserClassRepo;

@Service
public class UserClassService {
	
	@Autowired
	private UserClassRepo userClassRepo;
	
	public void addAdminUser(Admin admin) {
		userClassRepo.save(admin);
	}

}
