package com.maikro.checkoutSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.service.AdminService;

@RestController
@RequestMapping("/api/admin/")
public class AdminRestController {

//	private final ProductService productService;
//
//	private final DiscountService discountService;
//
//	private final UserClassService userClassService;
//
//	@Autowired
//	public AdminRestController(ProductService productService, DiscountService discountService,
//			UserClassService userClassService) {
//		this.productService = productService;
//		this.discountService = discountService;
//		this.userClassService = userClassService;
//	}
	
	@Autowired
	private AdminService adminService;

	@PostMapping
	public ResponseEntity<Admin> addUser(@RequestBody Admin admin) {
		
		Admin createdUser = adminService.addAdminUser(admin);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

}
