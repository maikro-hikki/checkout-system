package com.maikro.checkoutSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.maikro.checkoutSystem.Utility;
import com.maikro.checkoutSystem.constants.CustomResponse;
import com.maikro.checkoutSystem.constants.UserType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Product;

@Service
public class ValidationService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserClassService userClassService;

	@Autowired
	private ProductService productService;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private ProductDiscountService productDiscountService;

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
	
	public ResponseEntity<CustomResponse<Product>> parameterValidator(String productId) {
		
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

}
