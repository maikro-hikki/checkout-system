package com.maikro.checkoutSystem.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maikro.checkoutSystem.Utility;
import com.maikro.checkoutSystem.constants.CustomResponse;
import com.maikro.checkoutSystem.constants.UserType;
import com.maikro.checkoutSystem.model.Basket;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.UserClass;
import com.maikro.checkoutSystem.service.BasketService;
import com.maikro.checkoutSystem.service.CustomerService;
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

	@PostMapping("/register")
	public ResponseEntity<CustomResponse<Customer>> registerCustomer(@Valid @RequestBody Customer customer,
			BindingResult bindingResult) {

		ResponseEntity<CustomResponse<Customer>> initialValidation = Utility.initialObjectValidator(customer,
				bindingResult);

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

			customResponse.setMessage("Username already exists");
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

//	@PostMapping("/register")
//	public ResponseEntity<CustomResponse<Customer>> registerCustomer(@Valid @RequestBody Customer customer,
//			BindingResult bindingResult) {
//
//		ResponseEntity<CustomResponse<Customer>> initialValidation = Utility.initialObjectValidator(customer, bindingResult);
//
//		if (initialValidation.hasBody()) {
//
//			return initialValidation;
//		}
//
//		CustomResponse<Customer> customResponse = new CustomResponse<>();
//		customResponse.setData(customer);
//
//		if (customer.getUserType() != UserType.CUSTOMER) {
//
//			customResponse.setMessage("Only Customer user type is accepted for registration in the Customer portal");
//			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
//		}
//		
//		customer.setUserId(-1);
//		System.out.println(customer.getUserId());
//		
//		System.out.println(userClassService.findByUserId(customer.getUserId()).isPresent());
//
//		if (userClassService.findByUserId(customer.getUserId()).isPresent()) {
//			System.out.println("test");
//			customResponse.setMessage("User ID already exists");
//			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
//		}
//
//		if (userClassService.usernameExist(customer.getUsername())) {
//
//			customResponse.setMessage("Username already exists");
//			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
//		}
//		
//		Customer updatedCustomer = new Customer(customer.getUsername(), customer.getPassword(), customer.getFirstName(), customer.getLastName());
//		
////		updatedCustomer.setFirstName(customer.getFirstName());
////		updatedCustomer.setLastName(customer.getLastName());
////		updatedCustomer.setPassword(customer.getPassword());
////		updatedCustomer.setUsername(customer.getUsername());
////		updatedCustomer.setUserType(customer.getUserType());
//
//		Customer savedCustomer = userClassService.addCustomerUser(updatedCustomer);
//		System.out.println(updatedCustomer.getUserId());
//
//		if (savedCustomer == null) {
//
//			customResponse.setMessage("Error occured during registration");
//			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
//		} else {
//			
//			customResponse.setData(savedCustomer);
//
//			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//					.buildAndExpand(savedCustomer.getUserId()).toUri();
//
//			customResponse.setMessage("Successfully added Customer user: " + savedCustomer.getFirstName() + " "
//					+ savedCustomer.getLastName() + " (user ID: " + savedCustomer.getUserId() + ")");
//			return ResponseEntity.created(location).body(customResponse);
//		}
//	}

	@GetMapping("/all-products")
	public ResponseEntity<Page<Product>> getProductByPage(@RequestParam int offset, @RequestParam int pageSize) {

		Page<Product> allProducts = productService.findAllProductWithPagination(offset, pageSize);

		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}

	@GetMapping("/{userId}/basket")
	public ResponseEntity<CustomResponse<Page<Basket>>> addProductToBasket(@PathVariable String userId,
			@RequestParam int offset, @RequestParam int pageSize) {

		CustomResponse<Page<Basket>> customResponse = new CustomResponse<>();

		long userIdLong = Utility.convertStringToLong(userId);

		if (userIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Invalid user ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		UserClass user = userClassService.findByUserId(userIdLong).orElse(null);

		if (user == null) {

			customResponse.setMessage("User doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
		}

		Page<Basket> basketItems = basketService.findAllBasketItemsWithPagination(userIdLong, offset, pageSize);

		if (basketItems.isEmpty()) {
			customResponse.setMessage("No items to show");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}

		customResponse.setMessage("Retreived basket items");
		customResponse.setData(basketItems);

		return new ResponseEntity<>(customResponse, HttpStatus.OK);

	}

//	@PostMapping("/{userId}/basket")
//	public ResponseEntity<CustomResponse<Customer>> addProductToBasket(@PathVariable String userId, @RequestParam String productId, @RequestParam String quantity) {
//		
//	}

	@PostMapping("/{userId}/add-to-basket")
	public ResponseEntity<CustomResponse<Basket>> addProductToBasket(@PathVariable String userId,
			@RequestParam String productId, @RequestParam String quantity) {

		CustomResponse<Basket> customResponse = new CustomResponse<>();

		long userIdLong = Utility.convertStringToLong(userId);

		if (userIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Invalid user ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (!userClassService.isCustomer(userIdLong)) {

			customResponse.setMessage("Customer user doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		long productIdLong = Utility.convertStringToLong(productId);

		if (productIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Invalid product ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		int quantityInt = Utility.convertStringToInt(quantity);

		if (quantityInt == Integer.MIN_VALUE || quantityInt < 0 || (quantityInt % 1 != 0)) {

			customResponse.setMessage("Quantity have to be an integer (number without decimals) and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		Customer customer = customerService.findByUserId(userIdLong).orElse(null);

		if (customer == null) {

			customResponse.setMessage("User doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		Product product = productService.findByProductId(productIdLong).orElse(null);

		if (product == null) {

			customResponse.setMessage("Product doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		Basket basketItem = basketService.addProductToBasket(userIdLong, productIdLong, quantityInt);

		if (basketItem == null) {

			customResponse.setMessage("Not enough product in stock");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		customResponse.setData(basketItem);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(basketItem.getBasketId()).toUri();

		customResponse.setMessage("Successfully added product: " + product.getName() + " (product ID: "
				+ product.getProductId() + ") to the basket (basket ID: " + basketItem.getBasketId() + ")");
		return ResponseEntity.created(location).body(customResponse);
	}

	@PutMapping("/{userId}/remove-from-basket")
	public ResponseEntity<CustomResponse<Basket>> removeProductFromBasket(@PathVariable String userId,
			@RequestParam String productId, @RequestParam String quantity) {
		
		CustomResponse<Basket> customResponse = new CustomResponse<>();

		long userIdLong = Utility.convertStringToLong(userId);

		if (userIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Invalid user ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (!userClassService.isCustomer(userIdLong)) {

			customResponse.setMessage("Customer user doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		long productIdLong = Utility.convertStringToLong(productId);

		if (productIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Invalid product ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		int quantityInt = Utility.convertStringToInt(quantity);

		if (quantityInt == Integer.MIN_VALUE || quantityInt < 0 || (quantityInt % 1 != 0)) {

			customResponse.setMessage("Quantity have to be an integer (number without decimals) and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		Product product = productService.findByProductId(productIdLong).orElse(null);

		if (product == null) {

			customResponse.setMessage("Product doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
		
		List<Basket> retrievedBasket = basketService.findByUserId(userIdLong);
		int index = basketService.productInCustomerBasket(userIdLong, productIdLong);
		
		Basket basketItem = retrievedBasket.get(index);
		
		int remaingQuantity = basketItem.getQuantity() - quantityInt;
		
		if (!basketService.removeProductFromBasket(userIdLong, productIdLong, quantityInt)) {

			customResponse.setMessage("Error occured while updating the basket");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
		
		basketItem.setQuantity(remaingQuantity);

		customResponse.setData(basketItem);
		customResponse.setMessage("Successfully updated basket status");
		return new ResponseEntity<>(customResponse, HttpStatus.OK);
	}
	
	@PutMapping("/{userId}/check-out")
	public ResponseEntity<CustomResponse<Double>> basketCheckOut(@PathVariable String userId){
		
		CustomResponse<Double> customResponse = new CustomResponse<>();
		
		long userIdLong = Utility.convertStringToLong(userId);

		if (userIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Invalid user ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (!userClassService.isCustomer(userIdLong)) {

			customResponse.setMessage("Customer user doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
		
		double totalCost = basketService.totalCostInBasket(userIdLong);
		
		int deductionProcess = basketService.deductRemainingQuantityAfterPurchase(userIdLong);
		
		if (deductionProcess < 0) {
			
			customResponse.setMessage("Something went wrong with product quantity update");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}
		
		customResponse.setData(totalCost);
		customResponse.setMessage("Successfully checked out");
		return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		
	}
	
}
