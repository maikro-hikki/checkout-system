package com.maikro.checkoutSystem.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maikro.checkoutSystem.Utility;
import com.maikro.checkoutSystem.constants.CustomResponse;
import com.maikro.checkoutSystem.constants.UserType;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.service.BasketService;
import com.maikro.checkoutSystem.service.CustomerService;
import com.maikro.checkoutSystem.service.DiscountService;
import com.maikro.checkoutSystem.service.ProductDiscountService;
import com.maikro.checkoutSystem.service.ProductService;
import com.maikro.checkoutSystem.service.UserClassService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserClassService userClassService;
	
	@Autowired
	private BasketService basketService;

	@Autowired
	private ProductService productService;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private ProductDiscountService productDiscountService;
	
	@PostMapping("/register")
	public ResponseEntity<CustomResponse<Customer>> registerCustomer(@Valid @RequestBody Customer customer,
			BindingResult bindingResult) {

		ResponseEntity<CustomResponse<Customer>> initialValidation = Utility.initialObjectValidator(customer, bindingResult);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}

		CustomResponse<Customer> customResponse = new CustomResponse<>();
		customResponse.setData(customer);

		if (customer.getUserType() != UserType.CUSTOMER) {

			customResponse.setMessage("Only Customer user type is accepted for registration in the Customer portal");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (userClassService.findByUserId(customer.getUserId()).isPresent()) {

			customResponse.setMessage("User ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		if (userClassService.usernameExist(customer.getUsername())) {

			customResponse.setMessage("User ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		Customer savedCustomer = userClassService.addCustomerUser(customer);

		if (savedCustomer == null) {

			customResponse.setMessage("Error occured during registration");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		} else {

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(customer.getUserId()).toUri();

			customResponse.setMessage("Successfully added Customer user: " + customer.getFirstName() + " "
					+ customer.getLastName() + " (user ID: " + customer.getUserId() + ")");
			return ResponseEntity.created(location).body(customResponse);
		}
	}

}
