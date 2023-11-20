package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.repository.DiscountByProductRepo;

@Service
public class DiscountByProductService {
	
	@Autowired
	private DiscountByProductRepo discountByProductRepo;

	public Optional<DiscountByProduct> findByDiscountId(long discountId) {
		return discountByProductRepo.findById(discountId);
	}

}
