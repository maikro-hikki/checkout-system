package com.maikro.checkoutSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.repository.InventoryRepo;

@Service
public class InventoryService {
	
	@Autowired
	InventoryRepo inventoryRepo;
	
	@Autowired
	ProductService productService;

}
