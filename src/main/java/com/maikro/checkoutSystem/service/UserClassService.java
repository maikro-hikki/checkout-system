package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maikro.checkoutSystem.constants.UserType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.model.UserClass;
import com.maikro.checkoutSystem.repository.UserClassRepo;

/**
 * Service class for managing general abstract user classes.
 */
@Service
public class UserClassService {

	@Autowired
	private UserClassRepo userClassRepo;

	/**
	 * Retrieves a user class by its user ID.
	 *
	 * @param userId the ID of the user class
	 * @return an Optional containing the UserClass if found, or an empty Optional
	 *         if not found
	 */
	public Optional<UserClass> findByUserId(long userId) {
		return userClassRepo.findById(userId);
	}

	/**
	 * Retrieves a user class by its username.
	 *
	 * @param username the username of the user class
	 * @return an Optional containing the UserClass if found, or an empty Optional
	 *         if not found
	 */
	public Optional<UserClass> findByUsername(String username) {

		return userClassRepo.findByUsername(username);
	}

	/**
	 * Adds an admin user.
	 *
	 * @param admin the admin user to be added
	 * @return the added Admin user, or null if the user already exists
	 * @Transactional This method is executed within a transaction
	 */
	@Transactional
	public Admin addAdminUser(Admin admin) {

		if (findByUserId(admin.getUserId()).isPresent() || usernameExist(admin.getUsername())) {

			return null;
		}

		return userClassRepo.save(admin);
	}

	/**
	 * Adds a customer user.
	 *
	 * @param customer the customer user to be added
	 * @return the added Customer user, or null if the user already exists
	 * @Transactional This method is executed within a transaction
	 */
	@Transactional
	public Customer addCustomerUser(Customer customer) {

		if (findByUserId(customer.getUserId()).isPresent() || usernameExist(customer.getUsername())) {

			return null;
		}

		return userClassRepo.save(customer);
	}

	/**
	 * Adds an admin user with the specified details.
	 *
	 * @param username  the username of the admin user
	 * @param password  the password of the admin user
	 * @param firstName the first name of the admin user
	 * @param lastName  the last name of the admin user
	 * @return the added Admin user, or null if the user already exists
	 * @Transactional This method is executed within a transaction
	 */
	@Transactional
	public Admin addAdminUser(String username, String password, String firstName, String lastName) {

		if (usernameExist(username)) {

			return null;
		}

		Admin admin = new Admin(username, password, firstName, lastName);

		return userClassRepo.save(admin);
	}

	/**
	 * Adds a customer user with the specified details.
	 *
	 * @param username  the username of the customer user
	 * @param password  the password of the customer user
	 * @param firstName the first name of the customer user
	 * @param lastName  the last name of the customer user
	 * @return the added Customer user, or null if the user already exists
	 * @Transactional This method is executed within a transaction
	 */
	@Transactional
	public Customer addCustomerUser(String username, String password, String firstName, String lastName) {

		if (usernameExist(username)) {

			return null;
		}

		Customer customer = new Customer(username, password, firstName, lastName);

		return userClassRepo.save(customer);
	}

	/**
	 * Removes a user by its user ID.
	 *
	 * @param userId the ID of the user to be removed
	 * @return 1 if the user is removed, -1 if the user is not found
	 * @Transactional This method is executed within a transaction
	 */
	@Transactional
	public int removeUserById(long userId) {

		if (findByUserId(userId).isPresent()) {

			userClassRepo.deleteById(userId);
			return 1;
		}

		return -1;
	}

	/**
	 * Checks if a user is an admin.
	 *
	 * @param userId the ID of the user
	 * @return true if the user is an admin, false otherwise
	 */
	public boolean isAdmin(long userId) {

		Optional<UserClass> user = userClassRepo.findById(userId);

		if (user.isPresent()) {
			if (user.get().getUserType() == UserType.ADMIN) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if a user is a customer.
	 *
	 * @param userId the ID of the user
	 * @return true if the user is a customer, false otherwise
	 */
	public boolean isCustomer(long userId) {

		Optional<UserClass> user = userClassRepo.findById(userId);

		if (user.isPresent()) {
			if (user.get().getUserType() == UserType.CUSTOMER) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if a username already exists.
	 *
	 * @param username the username to check
	 * @return true if the username exists, false otherwise
	 */
	public boolean usernameExist(String username) {

		return userClassRepo.findByUsername(username).isPresent();
	}

}
