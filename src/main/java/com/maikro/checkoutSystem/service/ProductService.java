package com.maikro.checkoutSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	public void addNewProduct(Product product) {
		productRepo.save(product);
	}
	
	public void removeProductById(long productId) {
		productRepo.deleteById(productId);
	}

}
