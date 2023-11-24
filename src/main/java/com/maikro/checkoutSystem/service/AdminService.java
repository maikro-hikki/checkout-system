package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.repository.AdminRepo;

/**
 * Service class for managing admin child class operations.
 */
@Service
public class AdminService {
	
	@Autowired
	private AdminRepo adminRepo;
	
	/**
     * Retrieves an admin by user ID.
     *
     * @param userId the ID of the user
     * @return an Optional containing the Admin if found, or an empty Optional if not found
     */
	public Optional<Admin> findByUserId(long userId){
		return adminRepo.findById(userId);
	}

	/**
     * Adds an admin user.
     *
     * @param admin the admin to be added
     * @return the added Admin object
     */
	public Admin addAdminUser(Admin admin) {
		return adminRepo.save(admin);
	}
	
	/**
     * Checks if an admin with the given user ID exists.
     *
     * @param userId the ID of the user
     * @return true if an admin with the given user ID exists, false otherwise
     */
	public boolean adminExist(long userId) {
		
		if (adminRepo.findById(userId).isPresent()) {
			return true;
		}
		
		return false;
	}

}
