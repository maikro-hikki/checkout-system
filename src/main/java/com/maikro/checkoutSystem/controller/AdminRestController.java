package com.maikro.checkoutSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.service.AdminService;
import com.maikro.checkoutSystem.service.UserClassService;

@RestController
@RequestMapping("/api/admin/")
public class AdminRestController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserClassService userClassService;

//	@PostMapping("/register")
//	public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
//		
//		Admin createdUser = adminService.addAdminUser(admin);
//		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//	}

	@PostMapping("/register")
	public ResponseEntity<Admin> registerAdmin(@RequestParam String username, @RequestParam String password,
			@RequestParam String firstName, @RequestParam String lastName) {
		
		if (username.isEmpty() || userClassService.usernameExist(username) || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
	        // Invalid input data
//	        return ResponseEntity.badRequest().body(admin);
	    }

		Admin admin = userClassService.addAdminUser(username, password, firstName, lastName);

		if (admin == null) {
			return new ResponseEntity<>(admin, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(admin, HttpStatus.CREATED);
		}
	}

}
