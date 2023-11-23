package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.Utility;
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.repository.DiscountByProductRepo;

@Service
public class DiscountByProductService {
	
	@Autowired
	private DiscountByProductRepo discountByProductRepo;

	public Optional<DiscountByProduct> findByDiscountId(long discountId) {
		return discountByProductRepo.findById(discountId);
	}
	
	//returns the value of only the discounted amount from the unit price
	//inputs: discountId, price of product (normally unit price), quantity of product
	public double amountOfDiscount(long discountId, double price, int productQuantity) {
		
		double discountedValue = price * productQuantity;
		
		Optional<DiscountByProduct> discount = findByDiscountId(discountId);
		
		if (discount.isPresent()) {
			
			double discountAmount = discount.get().getDiscount();
			
			discountedValue = discountedValue * discountAmount;
			
			return discountedValue;
		}
		
		return -1;
	}

}
