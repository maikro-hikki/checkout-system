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

	@Autowired
	private AdminService adminService;

	@PostMapping
	public ResponseEntity<Admin> addUser(@RequestBody Admin admin) {
		
		Admin createdUser = adminService.addAdminUser(admin);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

}
