package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
class UserClassServiceTest {

	@Autowired
	private UserClassService userClassService;

	@Autowired
	private UserClassRepo userClassRepo;

	@Test
	void testAddAdminUser_ForValidUsernameAdmin_ShouldReturnSavedAdminObjectAndAddToDatabase() {

		// create an admin
		Admin admin = new Admin();
		admin.setUsername("admin1");

		// save admin user using the service method
		Admin savedAdmin = userClassService.addAdminUser(admin);

		// find the admin user in the database
		UserClass savedUser = userClassRepo.findById(admin.getUserId()).orElse(null);

		// verify returned customer from method is the same as the one created
		assertEquals(admin.getUserId(), savedAdmin.getUserId());
		assertEquals(admin.getUsername(), savedAdmin.getUsername());

		// verify that the user exist and is the admin that was added
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getUserId()).isEqualTo(admin.getUserId());
		assertThat(savedUser.getUserType()).isEqualTo(admin.getUserType());
		assertThat(savedUser.getUsername()).isEqualTo(admin.getUsername());
	}

	@Test
	void testAddAdminUser_ForInvalidUsernameAdmin_ShouldReturnNullObjectAndNotAddToDatabase() {

		// create an admin
		Admin admin = new Admin();
		admin.setUsername("admin2");

		// create an admin
		Admin admin2 = new Admin();
		admin2.setUsername("admin2");

		// save admin user using the service method
		userClassService.addAdminUser(admin);
		Admin savedAdmin = userClassService.addAdminUser(admin2);

		// find the admin user in the database
		UserClass savedUser = userClassRepo.findById(admin2.getUserId()).orElse(null);

		// verify returned value is null from method
		assertThat(savedAdmin).isNull();

		// verify that the admin was not added to the database
		assertThat(savedUser).isNull();
	}

	@Test
	void testAddCustomerUser_ForValidUsernameCustomer_ShouldReturnSavedCustomerObjectAndAddToDatabase() {

		// create a customer
		Customer customer = new Customer();
		customer.setUsername("customer1");

		// save customer user using the service method
		Customer savedCustomer = userClassService.addCustomerUser(customer);

		// find the customer user in the database
		UserClass savedUser = userClassRepo.findById(customer.getUserId()).orElse(null);

		// verify returned customer from method is the same as the one created
		assertEquals(customer.getUserId(), savedCustomer.getUserId());
		assertEquals(customer.getUsername(), savedCustomer.getUsername());

		// verify that the user exist in the database
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getUserId()).isEqualTo(customer.getUserId());
		assertThat(savedUser.getUserType()).isEqualTo(customer.getUserType());
		assertThat(savedUser.getUsername()).isEqualTo(customer.getUsername());
	}
	
	@Test
	void testAddCustomerUser_ForInvalidUsernameCustomer_ShouldReturnNullObjectAndNotAddToDatabase() {

		// create an admin
		Customer customer = new Customer();
		customer.setUsername("customer3333");

		// create an admin
		Customer customer2 = new Customer();
		customer2.setUsername("customer3333");

		// save admin user using the service method
		userClassService.addCustomerUser(customer);
		Customer savedCustomer = userClassService.addCustomerUser(customer2);

		// find the admin user in the database
		UserClass savedUser = userClassRepo.findById(customer2.getUserId()).orElse(null);

		// verify returned value is null from method
		assertThat(savedCustomer).isNull();

		// verify that the admin was not added to the database
		assertThat(savedUser).isNull();
	}

	@Test
	void testRemoveByUserId_ForExistingAdmin_ShouldReturn1AndRemoveAdminUser() {

		// create an admin
		Admin admin = new Admin();
		admin.setUsername("admin3");

		// save admin user using the service method
		userClassService.addAdminUser(admin);

		// remover the admin user in the database
		int result = userClassService.removeUserById(admin.getUserId());

		// try to find by userId of the admin in the database
		UserClass foundAdmin = userClassRepo.findById(admin.getUserId()).orElse(null);

		// value returned should be 1
		assertEquals(1, result);
		// verify that the customer user is not found
		assertThat(foundAdmin).isNull();

	}

	@Test
	void testRemoveByUserId_ForExistingCustomer_ShouldReturn1AndRemoveFromDatabase() {

		// create a customer
		Customer customer = new Customer();
		customer.setUsername("customer2");

		// save customer user using the service method
		userClassService.addCustomerUser(customer);

		// remover the customer user in the database
		int result = userClassService.removeUserById(customer.getUserId());

		// try to find by userId of the customer in the database
		UserClass foundCustomer = userClassRepo.findById(customer.getUserId()).orElse(null);

		// value returned should be 1
		assertEquals(1, result);
		// verify that the customer user is not found
		assertThat(foundCustomer).isNull();

	}

	@Test
	void testRemoveByUserId_ForNonExistingUser_ShouldReturnNegative1() {

		// remover the admin user in the database
		int result = userClassService.removeUserById(123456789);

		// value returned should be -1
		assertEquals(-1, result);
	}

	@Test
	void testIsCustomer_ForCustomer_ShouldReturnTrue() {

		// create a customer
		Customer customer = new Customer();
		customer.setUsername("customer3");

		// save customer user using the service method
		userClassService.addCustomerUser(customer);

		// test isCustomer method, if user is customer, should return true, else false
		assertTrue(userClassService.isCustomer(customer.getUserId()));
	}

	@Test
	void testIsCustomer_ForAdmin_ShouldReturnFalse() {

		// create an admin
		Admin admin = new Admin();
		admin.setUsername("admin4");

		// save admin user using the service method
		userClassService.addAdminUser(admin);

		// test isCustomer method, if user is admin, should return false
		assertFalse(userClassService.isCustomer(admin.getUserId()));
	}

	@Test
	void testIsAdmin_ForAdmin_ShouldReturnTrue() {

		// create an admin
		Admin admin = new Admin();
		admin.setUsername("admin5");

		// save admin user using the service method
		userClassService.addAdminUser(admin);

		// test isAdmin method, if user is admin, should return true
		assertTrue(userClassService.isAdmin(admin.getUserId()));
	}

	@Test
	void testIsAdmin_ForCustomer_ShouldReturnFalse() {

		// create a customer
		Customer customer = new Customer();
		customer.setUsername("customer4");

		// save customer user using the service method
		userClassService.addCustomerUser(customer);

		// test isAdmin method, if user is customer, should return false
		assertFalse(userClassService.isAdmin(customer.getUserId()));
	}

	@Test
	void testUsernameExist_ForExistingUsername_ShouldReturnTrue() {

		Customer user = new Customer();
		user.setUsername("customer5");

		userClassService.addCustomerUser(user);

		assertTrue(userClassService.usernameExist("customer5"));
	}

	@Test
	void testUsernameExist_ForNonExistingUsername_ShouldReturnFalse() {

		assertFalse(userClassService.usernameExist("john1"));
	}

	@Test
	void testUsernameExist_ForDeletedUser_ShouldReturnFalse() {

		Customer user = new Customer();
		user.setUsername("customer6");

		userClassService.addCustomerUser(user);

		userClassService.removeUserById(user.getUserId());

		assertFalse(userClassService.usernameExist("customer6"));
	}

}
