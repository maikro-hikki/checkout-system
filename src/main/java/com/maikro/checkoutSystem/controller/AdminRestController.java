package com.maikro.checkoutSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maikro.checkoutSystem.constants.ProductType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.service.AdminService;
import com.maikro.checkoutSystem.service.ProductService;
import com.maikro.checkoutSystem.service.UserClassService;

@RestController
@RequestMapping("/api/admin/")
public class AdminRestController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserClassService userClassService;

	@Autowired
	private ProductService productService;

	@PostMapping("/register")
	public ResponseEntity<String> registerAdmin(@RequestParam String username, @RequestParam String password,
			@RequestParam String firstName, @RequestParam String lastName) {

		if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {

			return new ResponseEntity<>("Please fill out all fields", HttpStatus.BAD_REQUEST);
		}

		if (userClassService.usernameExist(username)) {

			return new ResponseEntity<>("Username already taken", HttpStatus.CONFLICT);
		}

		Admin admin = userClassService.addAdminUser(username, password, firstName, lastName);

		if (admin == null) {
			return new ResponseEntity<>("Error occured during registration", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Admin registered successfully", HttpStatus.CREATED);
		}
	}

	@PostMapping("/product")
	public ResponseEntity<String> addProductToShop(@RequestParam String name, @RequestParam double unitPrice,
			@RequestParam int remainingQuantity, @RequestParam String productType) {

		if (name.isEmpty() || productType.isEmpty()) {

			return new ResponseEntity<>("Please fill out all fields", HttpStatus.BAD_REQUEST);
		}

		if (unitPrice < 0) {

			return new ResponseEntity<>("Unit price cannot be less than 0", HttpStatus.BAD_REQUEST);
		}

		if (remainingQuantity < 0 || (remainingQuantity % 1 != 0)) {

			return new ResponseEntity<>("Quantity cannot be a decimal or less than 0", HttpStatus.BAD_REQUEST);
		}

		ProductType productEnumType = productService.stringToProductType(productType);

		Product product = new Product(name, unitPrice, remainingQuantity, productEnumType);

		productService.addNewProduct(product);

		return new ResponseEntity<>("Admin registered successfully", HttpStatus.CREATED);
	}

}
