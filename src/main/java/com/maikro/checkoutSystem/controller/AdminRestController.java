package com.maikro.checkoutSystem.controller;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maikro.checkoutSystem.Utility;
import com.maikro.checkoutSystem.constants.ProductType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.UserClass;
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

	@PostMapping("/product")//////////////////////////////////
	public ResponseEntity<String> addProductToShop(@RequestParam String name, @RequestParam String unitPrice,
			@RequestParam String remainingQuantity, @RequestParam String productType) {
		
//		String currentUser = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//				.getUsername();
//		Optional<UserClass> userOptional = userClassService.getUserByUsername(currentUser);		
//		UserClass user = userOptional.get();

		if (name.isEmpty() || unitPrice.isEmpty() || remainingQuantity.isEmpty() || productType.isEmpty()) {

			return new ResponseEntity<>("Please fill out all fields", HttpStatus.BAD_REQUEST);
		}
		
		double unitPriceDouble = Utility.convertStringToDouble(unitPrice);
		int remainingQuantityInt = Utility.convertStringToInt(remainingQuantity);

		if (unitPriceDouble == Double.MIN_VALUE || unitPriceDouble < 0) {
			
			return new ResponseEntity<>("Unit price have to be a number and not negative", HttpStatus.BAD_REQUEST);
        }
		
		if (remainingQuantityInt == Integer.MIN_VALUE || remainingQuantityInt < 0 || (remainingQuantityInt % 1 != 0)) {

			return new ResponseEntity<>("Quantity have to be an integer (number without decimals) and not negative", HttpStatus.BAD_REQUEST);
		}

		ProductType productEnumType = productService.stringToProductType(productType);

		Product product = new Product(name, unitPriceDouble, remainingQuantityInt, productEnumType);

		productService.addNewProduct(product);

		return new ResponseEntity<>("Admin registered successfully", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<String> removeProductFromShop(@PathVariable("productId") String productId) {

		if (productId.isEmpty()) {

			return new ResponseEntity<>("Please fill out all fields", HttpStatus.BAD_REQUEST);
		}
		
		long productIdLong = Utility.convertStringToLong(productId);
		
		if (productIdLong == Long.MIN_VALUE) {
			
			return new ResponseEntity<>("Please input a valid product ID", HttpStatus.BAD_REQUEST);
		}
		
		if (!(productService.productExist(productIdLong))) {
			
			return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
		}
		
		productService.removeProductById(productIdLong);
		
		return new ResponseEntity<>("Product deleted successfully", HttpStatus.NO_CONTENT);
		
	}
	
	@PostMapping("/quantity-discount")//////////////////////////////////
	public ResponseEntity<String> addDiscountByQuantity(@RequestParam String quantity, @RequestParam String discount) {

		if (quantity.isEmpty() || discount.isEmpty()) {

			return new ResponseEntity<>("Please fill out all fields", HttpStatus.BAD_REQUEST);
		}
		
		int quantityInt = Utility.convertStringToInt(quantity);
		double discountDouble = Utility.convertStringToDouble(discount);
		
		Pageable page;///////////////////////////
		
		if (quantityInt == Integer.MIN_VALUE || quantityInt < 0 || (quantityInt % 1 != 0)) {

			return new ResponseEntity<>("Quantity have to be an integer (number without decimals) and not negative", HttpStatus.BAD_REQUEST);
		}
		
		if (discountDouble == Double.MIN_VALUE || discountDouble < 0 || discountDouble > 1) {
			
			return new ResponseEntity<>("Discount have to be between 0 (0%) and 1 (100%) inclusive and not negative", HttpStatus.BAD_REQUEST);
        }
		
		DiscountByQuantity quantityDiscount = new DiscountByQuantity(quantityInt, discountDouble);///////////////////////////

		return new ResponseEntity<>("Discount added to database successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("/product")
	public ResponseEntity<Page<Product>> getProductByPage (@RequestParam int offset, @RequestParam int pageSize){
		
		Page<Product> allProducts = productService.findProductWithPagination(offset, pageSize);
		
		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}

}
