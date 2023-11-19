package com.maikro.checkoutSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.repository.BasketRepo;

@Service
public class BasketService {

	@Autowired
	BasketRepo basketRepo;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserClassService userClassService;

	public void addProductToBasket(long customerId, long productId) {
		
	}

}
