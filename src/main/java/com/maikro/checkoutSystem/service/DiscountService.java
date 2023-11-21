package com.maikro.checkoutSystem.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Discount;
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.repository.DiscountRepo;

@Service
public class DiscountService {
	
	@Autowired
	private DiscountRepo discountRepo;
	
	public Optional<Discount> findByDiscountId(long discountId){
		return discountRepo.findById(discountId);
	}
	
	public boolean discountExist(long discountId) {
		return discountRepo.findById(discountId).isPresent();
	}
	
	public void addDiscountByQuantity(DiscountByQuantity discount) {
		discountRepo.save(discount);
	}
	
	public void addDiscountByProduct(DiscountByProduct discount) {
		discountRepo.save(discount);
	}
	
	public void removeDiscountById(long discountId) {
		discountRepo.deleteById(discountId);
	}

}
