package com.maikro.checkoutSystem.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maikro.checkoutSystem.Utility;
import com.maikro.checkoutSystem.constants.CustomResponse;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Discount;
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.ProductDiscount;
import com.maikro.checkoutSystem.service.AdminService;
import com.maikro.checkoutSystem.service.DiscountService;
import com.maikro.checkoutSystem.service.ProductDiscountService;
import com.maikro.checkoutSystem.service.ProductService;
import com.maikro.checkoutSystem.service.UserClassService;
import com.maikro.checkoutSystem.service.ValidationService;

import jakarta.validation.Valid;

@RestController
@EnableTransactionManagement
@RequestMapping("/api/v1/admin")
public class AdminRestController {

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

	@Autowired
	private ValidationService validationService;

	@PostMapping("/register")
	public ResponseEntity<CustomResponse<Admin>> registerAdmin(@Valid @RequestBody Admin admin,
			BindingResult bindingResult) {

		ResponseEntity<CustomResponse<Admin>> adminObjectValidation = validationService.parameterValidator(admin,
				bindingResult);

		if (adminObjectValidation.hasBody()) {

			return adminObjectValidation;
		}

		CustomResponse<Admin> customResponse = new CustomResponse<>();
		customResponse.setData(admin);

		Admin savedAdmin = userClassService.addAdminUser(admin);

		if (savedAdmin == null) {

			customResponse.setMessage("Error occured during registration");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		} else {

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(admin.getUserId()).toUri();

			customResponse.setMessage("Successfully added Admin user: " + admin.getFirstName() + " "
					+ admin.getLastName() + " (user ID: " + admin.getUserId() + ")");
			return ResponseEntity.created(location).body(customResponse);
		}
	}

	@PostMapping("/{userId}/product")
	public ResponseEntity<CustomResponse<Product>> addProductToShop(@Valid @RequestBody Product product,
			BindingResult bindingResult, @PathVariable("userId") long userId) {

		ResponseEntity<CustomResponse<Product>> initialValidation = validationService.parameterValidator(product,
				bindingResult, userId);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}

		CustomResponse<Product> customResponse = new CustomResponse<>();
		customResponse.setData(product);

		product.setAdmin(adminService.findByUserId(userId).get());

		productService.addNewProduct(product);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(product.getProductId()).toUri();

		customResponse.setMessage("Successfully added product: " + product.getName() + " (product ID: "
				+ product.getProductId() + "), added by Admin user: " + userId + " (user ID)");
		return ResponseEntity.created(location).body(customResponse);
	}

	@DeleteMapping("/delete-product")
	public ResponseEntity<CustomResponse<Product>> removeProductFromShop(@RequestParam String productId) {

		ResponseEntity<CustomResponse<Product>> initialValidation = validationService.parameterValidatorProductId(productId);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}

		CustomResponse<Product> customResponse = new CustomResponse<>();
		long productIdLong = Utility.convertStringToLong(productId);
		Product foundProduct = productService.findByProductId(productIdLong).orElse(null);

		customResponse.setData(foundProduct);
		customResponse.setMessage("Product deleted successfully");
		productService.removeProductById(productIdLong);

		return new ResponseEntity<>(customResponse, HttpStatus.OK);

	}

	@PostMapping("/{userId}/discount-by-quantity")
	public ResponseEntity<CustomResponse<DiscountByQuantity>> addDiscountByQuantity(
			@Valid @RequestBody DiscountByQuantity discount, BindingResult bindingResult, @PathVariable long userId) {

		ResponseEntity<CustomResponse<DiscountByQuantity>> initialValidation = validationService
				.parameterValidator(discount, bindingResult, userId);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}

		CustomResponse<DiscountByQuantity> customResponse = new CustomResponse<>();
		customResponse.setData(discount);

		discount.setAdmin(adminService.findByUserId(userId).get());

		discountService.addDiscountByQuantity(discount);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(discount.getDiscountId()).toUri();

		customResponse.setMessage("Successfully added discount-by-quantity: " + discount.getDiscountId()
				+ " (discount ID), added by Admin user: " + userId + " (user ID)");

		return ResponseEntity.created(location).body(customResponse);
	}

	@PostMapping("/{userId}/discount-by-product")
	public ResponseEntity<CustomResponse<DiscountByProduct>> addDiscountByProduct(
			@Valid @RequestBody DiscountByProduct discount, BindingResult bindingResult, @PathVariable long userId) {

		ResponseEntity<CustomResponse<DiscountByProduct>> initialValidation = validationService
				.parameterValidator(discount, bindingResult, userId);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}
		
		CustomResponse<DiscountByProduct> customResponse = new CustomResponse<>();
		customResponse.setData(discount);

		discount.setAdmin(adminService.findByUserId(userId).get());

		discountService.addDiscountByProduct(discount);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(discount.getDiscountId()).toUri();

		customResponse.setMessage("Successfully added discount-by-product: " + discount.getDiscountId()
				+ " (discount ID), added by Admin user: " + userId + " (user ID)");

		return ResponseEntity.created(location).body(customResponse);
	}

	@DeleteMapping("/delete-discount")
	public ResponseEntity<CustomResponse<Discount>> removeDiscountFromDatabase(@RequestParam String discountId) {

		ResponseEntity<CustomResponse<Discount>> initialValidation = validationService.parameterValidatorDiscountId(discountId);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}
		
		CustomResponse<Discount> customResponse = new CustomResponse<>();
		long discountIdLong = Utility.convertStringToLong(discountId);
		Discount foundDiscount = discountService.findByDiscountId(discountIdLong).orElse(null);

		customResponse.setData(foundDiscount);
		customResponse.setMessage("Discount deleted successfully");
		discountService.removeDiscountById(discountIdLong);

		return new ResponseEntity<>(customResponse, HttpStatus.OK);

	}

	@PostMapping("/product-discount")
	public ResponseEntity<CustomResponse<ProductDiscount>> addDiscountToProduct(@RequestParam String productId,
			String discountId) {
		
		ResponseEntity<CustomResponse<ProductDiscount>> initialValidation = validationService.parameterValidator(productId, discountId);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}
		
		CustomResponse<ProductDiscount> customResponse = new CustomResponse<>();
		long productIdLong = Utility.convertStringToLong(productId);
		long discountIdLong = Utility.convertStringToLong(discountId);
		Product foundProduct = productService.findByProductId(productIdLong).orElse(null);
		Discount foundDiscount = discountService.findByDiscountId(discountIdLong).orElse(null);

		ProductDiscount productDiscount = new ProductDiscount();
		productDiscount.setProduct(foundProduct);
		productDiscount.setDiscount(foundDiscount);

		boolean addSuccess = productDiscountService.addProductDiscount(productDiscount);

		if (!addSuccess) {

			customResponse.setMessage("Error occured during adding discount to product, e.g. discount could already have been added to product");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		} else {

			customResponse.setData(productDiscount);
			customResponse.setMessage("Discount added to product successfully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	@DeleteMapping("/delete-product-discount")
	public ResponseEntity<CustomResponse<ProductDiscount>> deleteDiscountToProduct(@RequestParam String productId,
			String discountId) {

		ResponseEntity<CustomResponse<ProductDiscount>> initialValidation = validationService.parameterValidator(productId, discountId);

		if (initialValidation.hasBody()) {

			return initialValidation;
		}
		
		CustomResponse<ProductDiscount> customResponse = new CustomResponse<>();
		long productIdLong = Utility.convertStringToLong(productId);
		long discountIdLong = Utility.convertStringToLong(discountId);

		ProductDiscount foundProductDiscount = productDiscountService.findByProductIdAndDiscountId(productIdLong,
				discountIdLong);

		if (foundProductDiscount == null) {

			customResponse.setMessage("Product discount not found");
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		boolean deleteSuccess = productDiscountService.removeProductDiscountByProductIdAndDiscountId(productIdLong,
				discountIdLong);

		if (!deleteSuccess) {

			customResponse.setMessage("Error occured during removal of product discount");
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		} else {

			customResponse.setData(foundProductDiscount);
			customResponse.setMessage("Discount removed from product successfully");
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}
	}

	@GetMapping("/all-products")
	public ResponseEntity<Page<Product>> getAllProductByPage(@RequestParam int offset, @RequestParam int pageSize) {

		Page<Product> allProducts = productService.findAllProductWithPagination(offset, pageSize);

		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}

}
