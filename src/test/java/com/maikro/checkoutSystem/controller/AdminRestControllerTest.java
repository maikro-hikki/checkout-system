package com.maikro.checkoutSystem.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.maikro.checkoutSystem.CheckoutSystemApplication;
import com.maikro.checkoutSystem.constants.ProductType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.UserClass;
import com.maikro.checkoutSystem.repository.UserClassRepo;
import com.maikro.checkoutSystem.service.UserClassService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(classes = CheckoutSystemApplication.class)
@AutoConfigureMockMvc
class AdminRestControllerTest {

	@Autowired
	private AdminRestController adminRestController;

	@Autowired
	private UserClassRepo userClassRepo;

	@Autowired
	private UserClassService userClassService;

	@BeforeEach
	void setUp() {

		RestAssuredMockMvc.standaloneSetup(adminRestController);

	}

	@Test
	void testRegisterAdmin_ForValidInputs_ShouldReturnCreatedHttpStatusAndAddToDatabase() {

		Admin admin = new Admin("admin", "123", "John", "Doe");

		given().contentType("application/json").body(admin).when().post("/api/v1/admin/register").then()
				.statusCode(HttpStatus.CREATED.value()).body("data.username", equalTo(admin.getUsername()));

		UserClass user = userClassRepo.findByUsername(admin.getUsername()).orElse(null);

		assertThat(user).isNotNull();
		assertTrue(userClassService.isAdmin(user.getUserId()));

	}

	@Test
	void testRegisterAdmin_ForInvalidUsername_ShouldReturnConflictHttpStatusWithCorrectMessage() {

		Admin admin = new Admin("admin", "123", "John", "Doe");

		given().contentType("application/json").body(admin).when().post("/api/v1/admin/register").then()
				.statusCode(HttpStatus.CONFLICT.value()).body("message", equalTo("Username already exists"));
	}
	
	@Test
	void testRegisterAdmin_ForMissingInputs_ShouldReturnBadRequestHttpStatusWithCorrectMessage() {

		Admin admin = new Admin();
		admin.setPassword("123");
		admin.setFirstName("firstName");
		admin.setLastName("lastName");

		given().contentType("application/json").body(admin).when().post("/api/v1/admin/register").then()
				.statusCode(HttpStatus.BAD_REQUEST.value()).body("message", equalTo("[Field 'username': must not be blank]"));
	}
	
	@Test
	void testAddProductToShop_ForValidInputs_ShouldReturnCreatedHttpStatusAndAddToDatabase() {

		Product product = new Product("Vacum ABC", 10.5, 50, ProductType.ELECTRONICS);

		given().contentType("application/json")
			.body(product)
			.when()
			.post("/api/v1/admin/1/product")
			.then()
				.statusCode(HttpStatus.CREATED.value())
					.body("data.name", equalTo(product.getName()))
					.body("data.unitPrice", equalTo(product.getUnitPrice()));

	}
}
