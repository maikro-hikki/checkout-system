package com.maikro.checkoutSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.ProductDiscount;
import com.maikro.checkoutSystem.repository.ProductDiscountRepo;

@Service
public class ProductDiscountService {
	
	@Autowired
	private ProductDiscountRepo productDiscountRepo;
	
	public void addProductDiscount(ProductDiscount productDiscount) {
		productDiscountRepo.save(productDiscount);
	}
	
	public void removeProductDiscountById(long productDiscountId) {
		productDiscountRepo.deleteById(productDiscountId);
	}

}
