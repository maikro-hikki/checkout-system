package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maikro.checkoutSystem.constants.UserType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.model.UserClass;
import com.maikro.checkoutSystem.repository.UserClassRepo;

@SpringBootTest
class UserClassTest {
	
	@Autowired
	private UserClassService userClassService;
	
	@Autowired
	private UserClassRepo userClassRepo;

	@Test
	void testAddAdminUser() {
		
		//create an admin
    	Admin admin = new Admin(UserType.ADMIN);
    	
    	//save admin user using the service method
    	userClassService.addAdminUser(admin);
    	
    	//find the admin user in the database
    	UserClass savedUser = userClassRepo.findById(admin.getUserId()).orElse(null);
    	
    	//verify that the user exist and is the admin that was added
    	assertThat(savedUser).isNotNull();
    	assertThat(savedUser.getUserId()).isEqualTo(admin.getUserId());
    	assertThat(savedUser.getUserType()).isEqualTo(admin.getUserType());
	}
	
	@Test
	void testAddCustomerUser() {
		
		//create a customer
    	Customer customer = new Customer(UserType.CUSTOMER);
    	
    	//save customer user using the service method
    	userClassService.addCustomerUser(customer);
    	
    	//find the customer user in the database
    	UserClass savedUser = userClassRepo.findById(customer.getUserId()).orElse(null);
    	
    	//verify that the user exist and is the customer that was added
    	assertThat(savedUser).isNotNull();
    	assertThat(savedUser.getUserId()).isEqualTo(customer.getUserId());
    	assertThat(savedUser.getUserType()).isEqualTo(customer.getUserType());
	}
	
	@Test
	void testRemoveByUserId_ForBothAdminAndCustomer() {
		
		//create a customer
    	Customer customer = new Customer(UserType.CUSTOMER);
    	//create an admin
    	Admin admin = new Admin(UserType.ADMIN);
    	
    	//save customer user using the service method
    	userClassService.addCustomerUser(customer);
    	//save admin user using the service method
    	userClassService.addAdminUser(admin);
    	
    	//remover the customer user in the database
    	userClassService.removeUserById(customer.getUserId());
    	//remover the admin user in the database
    	userClassService.removeUserById(admin.getUserId());
    	
    	//try to find by userId of the customer in the database
    	UserClass foundCustomer = userClassRepo.findById(customer.getUserId()).orElse(null);
    	//try to find by userId of the admin in the database
    	UserClass foundAdmin = userClassRepo.findById(admin.getUserId()).orElse(null);
    	
    	//verify that the customer user is not found
    	assertThat(foundCustomer).isNull();
    	//verify that the customer user is not found
    	assertThat(foundAdmin).isNull();
    	
	}
	
	@Test
	void testIsAdmin_ForBothCustomerAndAdmin() {
		
		//create a customer
    	Customer customer = new Customer(UserType.CUSTOMER);
    	//create an admin
    	Admin admin = new Admin(UserType.ADMIN);
    	
    	//save customer user using the service method
    	userClassService.addCustomerUser(customer);
    	//save admin user using the service method
    	userClassService.addAdminUser(admin);
    	
    	//test isAdmin method, if user is admin, should return true, else false
    	assertFalse(userClassService.isAdmin(customer.getUserId()));
    	assertTrue(userClassService.isAdmin(admin.getUserId()));
	}
	
	@Test
	void testIsCustomer_ForBothCustomerAndAdmin() {
		
		//create a customer
    	Customer customer = new Customer(UserType.CUSTOMER);
    	//create an admin
    	Admin admin = new Admin(UserType.ADMIN);
    	
    	//save customer user using the service method
    	userClassService.addCustomerUser(customer);
    	//save admin user using the service method
    	userClassService.addAdminUser(admin);
    	
    	//test isCustomer method, if user is customer, should return true, else false
    	assertTrue(userClassService.isCustomer(customer.getUserId()));
    	assertFalse(userClassService.isCustomer(admin.getUserId()));
	}

}
