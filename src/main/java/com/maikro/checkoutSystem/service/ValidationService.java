package com.maikro.checkoutSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.maikro.checkoutSystem.Utility;
import com.maikro.checkoutSystem.constants.CustomResponse;
import com.maikro.checkoutSystem.constants.DiscountType;
import com.maikro.checkoutSystem.constants.UserType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Basket;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.model.Discount;
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.ProductDiscount;
import com.maikro.checkoutSystem.model.UserClass;

@Service
public class ValidationService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserClassService userClassService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private DiscountService discountService;

	/**
	 * Validates the parameters for registering an admin.
	 * 
	 * @param admin         The admin object to be validated.
	 * @param bindingResult The binding result object containing validation errors.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<Admin>> parameterValidator(Admin admin, BindingResult bindingResult) {

		// Perform initial object validation using a utility method
		ResponseEntity<CustomResponse<Admin>> initialValidation = Utility.initialObjectValidator(admin, bindingResult);

		// If initial validation has errors, return the validation result
		if (initialValidation.hasBody()) {
			return initialValidation;
		}

		// Create a CustomResponse object and set the admin as the data
		CustomResponse<Admin> customResponse = new CustomResponse<>();
		customResponse.setData(admin);

		// Validate the admin's user type
		if (admin.getUserType() != UserType.ADMIN) {
			customResponse.setMessage("Only Admin user type is accepted for registration in the Admin portal");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Check if the user ID already exists
		if (userClassService.findByUserId(admin.getUserId()).isPresent()) {
			customResponse.setMessage("User ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		// Check if the username already exists
		if (userClassService.usernameExist(admin.getUsername())) {
			customResponse.setMessage("Username already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		// If all validations pass, return a successful response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the parameters for adding a product.
	 * 
	 * @param product       The product object to be validated.
	 * @param bindingResult The binding result object containing validation errors.
	 * @param userId        The ID of the user performing the action.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<Product>> parameterValidator(Product product, BindingResult bindingResult,
			long userId) {

		// Perform initial object validation using a utility method
		ResponseEntity<CustomResponse<Product>> initialValidation = Utility.initialObjectValidator(product,
				bindingResult);

		// If initial validation has errors, return the validation result
		if (initialValidation.hasBody()) {
			return initialValidation;
		}

		// Create a CustomResponse object and set the product as the data
		CustomResponse<Product> customResponse = new CustomResponse<>();
		customResponse.setData(product);

		// Check if the user is an admin
		if (!adminService.adminExist(userId)) {
			customResponse.setMessage("User doesn't exist or does not have Admin privileges");
			return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
		}

		// Check if the product ID already exists
		if (productService.productExist(product.getProductId())) {
			customResponse.setMessage("Product ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		// Validate the unit price
		double unitPriceDouble = product.getUnitPrice();
		if (unitPriceDouble < 0) {
			customResponse.setMessage("Unit price has to be a number and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Validate the remaining quantity
		int remainingQuantityInt = product.getRemainingQuantity();
		if (remainingQuantityInt < 0 || (remainingQuantityInt % 1 != 0)) {
			customResponse.setMessage("Quantity has to be an integer (number without decimals) and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// If all validations pass, return a successful response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the product ID parameter.
	 * 
	 * @param productId The product ID to be validated.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<Product>> parameterValidatorProductId(String productId) {
		CustomResponse<Product> customResponse = new CustomResponse<>();

		// Check if the product ID is empty
		if (productId.isEmpty()) {
			customResponse.setMessage("Please fill out all fields");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Convert the product ID to a long value
		long productIdLong = Utility.convertStringToLong(productId);

		// Check if the conversion failed or the product ID is invalid
		if (productIdLong == Long.MIN_VALUE) {
			customResponse.setMessage("Please input a valid product ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Find the product with the given product ID
		Product foundProduct = productService.findByProductId(productIdLong).orElse(null);

		// Check if the product was not found
		if (foundProduct == null) {
			customResponse.setMessage("Product not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		// If the product ID is valid and the product exists, return a successful
		// response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the parameters for adding a quantity-based discount.
	 * 
	 * @param discount      The discount object to be validated.
	 * @param bindingResult The binding result object containing validation errors.
	 * @param userId        The ID of the user performing the action.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<DiscountByQuantity>> parameterValidator(DiscountByQuantity discount,
			BindingResult bindingResult, long userId) {
		// Perform initial object validation using a utility method
		ResponseEntity<CustomResponse<DiscountByQuantity>> initialValidation = Utility.initialObjectValidator(discount,
				bindingResult);

		// If initial validation has errors, return the validation result
		if (initialValidation.hasBody()) {
			return initialValidation;
		}

		// Create a CustomResponse object and set the discount as the data
		CustomResponse<DiscountByQuantity> customResponse = new CustomResponse<>();
		customResponse.setData(discount);

		// Check if the discount type is quantity
		if (discount.getDiscountType() != DiscountType.QUANTITY) {
			customResponse.setMessage("Only quantity type discount is accepted for this portal");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Check if the user is an admin
		if (!adminService.adminExist(userId)) {
			customResponse.setMessage("User doesn't exist or does not have Admin privileges");
			return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
		}

		// Check if the discount ID already exists
		if (discountService.discountExist(discount.getDiscountId())) {
			customResponse.setMessage("Discount ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		// Validate the quantity
		int quantity = discount.getQuantity();
		if (quantity < 0 || (quantity % 1 != 0)) {
			customResponse.setMessage("Quantity has to be an integer (number without decimals) and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Validate the discount value
		double discountValue = discount.getDiscount();
		if (discountValue < 0 || discountValue > 1) {
			customResponse.setMessage("Discount has to be between 0 (0%) and 1 (100%) inclusive and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// If all validations pass, return a successful response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the parameters for adding an individual product-based discount.
	 * 
	 * @param discount      The discount object to be validated.
	 * @param bindingResult The binding result object containing validation errors.
	 * @param userId        The ID of the user performing the action.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<DiscountByProduct>> parameterValidator(DiscountByProduct discount,
			BindingResult bindingResult, long userId) {
		// Perform initial object validation using a utility method
		ResponseEntity<CustomResponse<DiscountByProduct>> initialValidation = Utility.initialObjectValidator(discount,
				bindingResult);

		// If initial validation has errors, return the validation result
		if (initialValidation.hasBody()) {
			return initialValidation;
		}

		// Create a CustomResponse object and set the discount as the data
		CustomResponse<DiscountByProduct> customResponse = new CustomResponse<>();
		customResponse.setData(discount);

		// Check if the discount type is individual product
		if (discount.getDiscountType() != DiscountType.INDIVIDUAL_PRODUCT) {
			customResponse.setMessage("Only individual product type discount is accepted for this portal");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Check if the user is an admin
		if (!adminService.adminExist(userId)) {
			customResponse.setMessage("User doesn't exist or does not have Admin privileges");
			return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
		}

		// Check if the discount ID already exists
		if (discountService.discountExist(discount.getDiscountId())) {
			customResponse.setMessage("Discount ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		// Validate the discount value
		double discountValue = discount.getDiscount();
		if (discountValue < 0 || discountValue > 1) {
			customResponse.setMessage("Discount has to be between 0 (0%) and 1 (100%) inclusive and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// If all validations pass, return a successful response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the discount ID parameter.
	 * 
	 * @param discountId The discount ID to be validated.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<Discount>> parameterValidatorDiscountId(String discountId) {
		CustomResponse<Discount> customResponse = new CustomResponse<>();

		// Check if the discount ID is empty
		if (discountId.isEmpty()) {
			customResponse.setMessage("Please fill out all fields");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Convert the discount ID to a long value
		long discountIdLong = Utility.convertStringToLong(discountId);

		// Check if the conversion failed or the discount ID is invalid
		if (discountIdLong == Long.MIN_VALUE) {
			customResponse.setMessage("Please input a valid discount ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Find the discount with the given discount ID
		Discount foundDiscount = discountService.findByDiscountId(discountIdLong).orElse(null);

		// Check if the discount was not found
		if (foundDiscount == null) {
			customResponse.setMessage("Discount not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		// If the discount ID is valid and the discount exists, return a successful
		// response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the parameters for adding a product discount.
	 * 
	 * @param productId  The ID of the product to be validated.
	 * @param discountId The ID of the discount to be validated.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<ProductDiscount>> parameterValidator(String productId, String discountId) {
		CustomResponse<ProductDiscount> customResponse = new CustomResponse<>();

		// Check if the product ID or discount ID is empty
		if (productId.isEmpty() || discountId.isEmpty()) {
			customResponse.setMessage("Please fill out all fields");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Convert the product ID and discount ID to long values
		long productIdLong = Utility.convertStringToLong(productId);
		long discountIdLong = Utility.convertStringToLong(discountId);

		// Check if the product ID conversion failed or the product ID is invalid
		if (productIdLong == Long.MIN_VALUE) {
			customResponse.setMessage("Please input a valid product ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Check if the discount ID conversion failed or the discount ID is invalid
		if (discountIdLong == Long.MIN_VALUE) {
			customResponse.setMessage("Please input a valid discount ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Find the product with the given product ID
		Product foundProduct = productService.findByProductId(productIdLong).orElse(null);

		// Check if the product was not found
		if (foundProduct == null) {
			customResponse.setMessage("Product not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		// Find the discount with the given discount ID
		Discount foundDiscount = discountService.findByDiscountId(discountIdLong).orElse(null);

		// Check if the discount was not found
		if (foundDiscount == null) {
			customResponse.setMessage("Discount not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		// If both the product and discount exist, return a successful response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the parameters for customer registration.
	 * 
	 * @param customer      The customer object to be validated.
	 * @param bindingResult The binding result object containing validation errors.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<Customer>> parameterValidator(Customer customer, BindingResult bindingResult) {
		// Perform initial object validation using a utility method
		ResponseEntity<CustomResponse<Customer>> initialValidation = Utility.initialObjectValidator(customer,
				bindingResult);

		// If initial validation has errors, return the validation result
		if (initialValidation.hasBody()) {
			return initialValidation;
		}

		// Create a CustomResponse object and set the customer as the data
		CustomResponse<Customer> customResponse = new CustomResponse<>();
		customResponse.setData(customer);

		// Check if the user type is customer
		if (customer.getUserType() != UserType.CUSTOMER) {
			customResponse.setMessage("Only Customer user type is accepted for registration in the Customer portal");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Check if the user ID already exists
		if (userClassService.findByUserId(customer.getUserId()).isPresent()) {
			customResponse.setMessage("User ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		// Check if the username already exists
		if (userClassService.usernameExist(customer.getUsername())) {
			customResponse.setMessage("Username already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		// If all validations pass, return a successful response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the parameters for retrieving a page of baskets.
	 * 
	 * @param userId   The ID of the user to be validated.
	 * @param offset   The offset value for pagination.
	 * @param pageSize The page size value for pagination.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<Page<Basket>>> parameterValidator(String userId, int offset, int pageSize) {
		CustomResponse<Page<Basket>> customResponse = new CustomResponse<>();

		// Convert the user ID to a long value
		long userIdLong = Utility.convertStringToLong(userId);

		// Check if the user ID conversion failed or the user ID is invalid
		if (userIdLong == Long.MIN_VALUE) {
			customResponse.setMessage("Invalid user ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Find the user with the given user ID
		UserClass user = userClassService.findByUserId(userIdLong).orElse(null);

		// Check if the user does not exist
		if (user == null) {
			customResponse.setMessage("User doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
		}

		// If the user exists, return a successful response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the parameters for adding a product to a customer's basket.
	 * 
	 * @param userId    The ID of the user to be validated.
	 * @param productId The ID of the product to be validated.
	 * @param quantity  The quantity of the product to be validated.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<Basket>> parameterValidator(String userId, String productId, String quantity) {
		CustomResponse<Basket> customResponse = new CustomResponse<>();

		// Convert the user ID to a long value
		long userIdLong = Utility.convertStringToLong(userId);

		// Check if the user ID conversion failed or the user ID is invalid
		if (userIdLong == Long.MIN_VALUE) {
			customResponse.setMessage("Invalid user ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Check if the user is a customer
		if (!userClassService.isCustomer(userIdLong)) {
			customResponse.setMessage("Customer user doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Convert the product ID to a long value
		long productIdLong = Utility.convertStringToLong(productId);

		// Check if the product ID conversion failed or the product ID is invalid
		if (productIdLong == Long.MIN_VALUE) {
			customResponse.setMessage("Invalid product ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Convert the quantity to an integer value
		int quantityInt = Utility.convertStringToInt(quantity);

		// Check if the quantity conversion failed, the quantity is negative, or has
		// decimals
		if (quantityInt == Integer.MIN_VALUE || quantityInt < 0 || (quantityInt % 1 != 0)) {
			customResponse.setMessage("Quantity has to be a non-negative integer (number without decimals)");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Find the customer with the given user ID
		Customer customer = customerService.findByUserId(userIdLong).orElse(null);

		// Check if the customer does not exist
		if (customer == null) {
			customResponse.setMessage("User doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Find the product with the given product ID
		Product product = productService.findByProductId(productIdLong).orElse(null);

		// Check if the product does not exist
		if (product == null) {
			customResponse.setMessage("Product doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// If all validations pass, return a successful response
		return ResponseEntity.noContent().build();
	}

	/**
	 * Validates the parameter for retrieving a customer's balance.
	 * 
	 * @param userId The ID of the user to be validated.
	 * @return ResponseEntity containing a CustomResponse object with the validation
	 *         result.
	 */
	public ResponseEntity<CustomResponse<Double>> parameterValidator(String userId) {
		CustomResponse<Double> customResponse = new CustomResponse<>();

		// Convert the user ID to a long value
		long userIdLong = Utility.convertStringToLong(userId);

		// Check if the user ID conversion failed or the user ID is invalid
		if (userIdLong == Long.MIN_VALUE) {
			customResponse.setMessage("Invalid user ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// Check if the user is a customer
		if (!userClassService.isCustomer(userIdLong)) {
			customResponse.setMessage("Customer user doesn't exist");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		// If all validations pass, return a successful response
		return ResponseEntity.noContent().build();
	}

}
