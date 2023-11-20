package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.repository.DiscountByQuantityRepo;

@Service
public class DiscountByQuantityService {

	@Autowired
	private DiscountByQuantityRepo discountByQuantityRepo;

	public Optional<DiscountByQuantity> findByDiscountId(long discountId) {
		return discountByQuantityRepo.findById(discountId);
	}

}
