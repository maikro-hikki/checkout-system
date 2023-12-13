package com.maikro.checkoutSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

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

	public ResponseEntity<CustomResponse<Admin>> parameterValidator(Admin admin, BindingResult bindingResult) {

		ResponseEntity<CustomResponse<Admin>> initialValidation = Utility.initialObjectValidator(admin, bindingResult);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}

		CustomResponse<Admin> customResponse = new CustomResponse<>();
		customResponse.setData(admin);

		if (admin.getUserType() != UserType.ADMIN) {

			customResponse.setMessage("Only Admin user type is accepted for registration in the Admin portal");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (userClassService.findByUserId(admin.getUserId()).isPresent()) {

			customResponse.setMessage("User ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		if (userClassService.usernameExist(admin.getUsername())) {

			customResponse.setMessage("Username already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<CustomResponse<Product>> parameterValidator(Product product, BindingResult bindingResult,
			long userId) {

		ResponseEntity<CustomResponse<Product>> initialValidation = Utility.initialObjectValidator(product,
				bindingResult);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}

		CustomResponse<Product> customResponse = new CustomResponse<>();
		customResponse.setData(product);

		if (!adminService.adminExist(userId)) {

			customResponse.setMessage("User doesn't exist or does not have Admin privilages");
			return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
		}

		if (productService.productExist(product.getProductId())) {

			customResponse.setMessage("Product ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		double unitPriceDouble = product.getUnitPrice();
		int remainingQuantityInt = product.getRemainingQuantity();

		if (unitPriceDouble < 0) {

			customResponse.setMessage("Unit price have to be a number and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (remainingQuantityInt < 0 || (remainingQuantityInt % 1 != 0)) {

			customResponse.setMessage("Quantity have to be an integer (number without decimals) and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<CustomResponse<Product>> parameterValidatorProductId(String productId) {

		CustomResponse<Product> customResponse = new CustomResponse<>();

		if (productId.isEmpty()) {

			customResponse.setMessage("Please fill out all fields");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		long productIdLong = Utility.convertStringToLong(productId);

		if (productIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Please input a valid product ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		Product foundProduct = productService.findByProductId(productIdLong).orElse(null);

		if (foundProduct == null) {

			customResponse.setMessage("Product not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<CustomResponse<DiscountByQuantity>> parameterValidator(DiscountByQuantity discount,
			BindingResult bindingResult, long userId) {

		ResponseEntity<CustomResponse<DiscountByQuantity>> initialValidation = Utility.initialObjectValidator(discount,
				bindingResult);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}

		CustomResponse<DiscountByQuantity> customResponse = new CustomResponse<>();
		customResponse.setData(discount);

		if (discount.getDiscountType() != DiscountType.QUANTITY) {

			customResponse.setMessage("Only quantity type discount is accepted for this portal");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (!adminService.adminExist(userId)) {

			customResponse.setMessage("User doesn't exist or does not have Admin privilages");
			return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
		}

		if (discountService.discountExist(discount.getDiscountId())) {

			customResponse.setMessage("Discount ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		int quantity = discount.getQuantity();
		double discountValue = discount.getDiscount();

		if (quantity < 0 || (quantity % 1 != 0)) {

			customResponse.setMessage("Quantity have to be an integer (number without decimals) and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (discountValue < 0 || discountValue > 1) {

			customResponse.setMessage("Discount have to be between 0 (0%) and 1 (100%) inclusive and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<CustomResponse<DiscountByProduct>> parameterValidator(DiscountByProduct discount,
			BindingResult bindingResult, long userId) {

		ResponseEntity<CustomResponse<DiscountByProduct>> initialValidation = Utility.initialObjectValidator(discount,
				bindingResult);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}

		CustomResponse<DiscountByProduct> customResponse = new CustomResponse<>();
		customResponse.setData(discount);

		if (discount.getDiscountType() != DiscountType.INDIVIDUAL_PRODUCT) {

			customResponse.setMessage("Only individual product type discount is accepted for this portal");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (!adminService.adminExist(userId)) {

			customResponse.setMessage("User doesn't exist or does not have Admin privilages");
			return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
		}

		if (discountService.discountExist(discount.getDiscountId())) {

			customResponse.setMessage("Discount ID already exists");
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		double discountValue = discount.getDiscount();

		if (discountValue < 0 || discountValue > 1) {

			customResponse.setMessage("Discount have to be between 0 (0%) and 1 (100%) inclusive and not negative");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<CustomResponse<Discount>> parameterValidatorDiscountId(String discountId) {

		CustomResponse<Discount> customResponse = new CustomResponse<>();

		if (discountId.isEmpty()) {

			customResponse.setMessage("Please fill out all fields");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		long discountIdLong = Utility.convertStringToLong(discountId);

		if (discountIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Please input a valid discount ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		Discount foundDiscount = discountService.findByDiscountId(discountIdLong).orElse(null);

		if (foundDiscount == null) {

			customResponse.setMessage("Discount not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<CustomResponse<ProductDiscount>> parameterValidator(String productId, String discountId) {

		CustomResponse<ProductDiscount> customResponse = new CustomResponse<>();

		if (productId.isEmpty() || discountId.isEmpty()) {

			customResponse.setMessage("Please fill out all fields");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		long productIdLong = Utility.convertStringToLong(productId);
		long discountIdLong = Utility.convertStringToLong(discountId);

		if (productIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Please input a valid product ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		if (discountIdLong == Long.MIN_VALUE) {

			customResponse.setMessage("Please input a valid discount ID");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		}

		Product foundProduct = productService.findByProductId(productIdLong).orElse(null);
		Discount foundDiscount = discountService.findByDiscountId(discountIdLong).orElse(null);

		if (foundProduct == null) {

			customResponse.setMessage("Product not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		if (foundDiscount == null) {

			customResponse.setMessage("Discount not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<CustomResponse<Customer>> parameterValidator(Customer customer, BindingResult bindingResult) {

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

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<CustomResponse<Page<Basket>>> parameterValidator(String userId, int offset, int pageSize) {

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
		
		return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<CustomResponse<Basket>> parameterValidator(String userId, String productId, String quantity) {
		
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
		
		return ResponseEntity.noContent().build();
	}

}
