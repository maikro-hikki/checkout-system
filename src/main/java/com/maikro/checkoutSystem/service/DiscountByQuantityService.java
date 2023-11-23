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

	//returns the value of only the discounted amount from the unit price
	//inputs: discountId, price of product (normally unit price), quantity of product
	public double amountOfDiscount(long discountId, double price, int productQuantity) {

		double discountedValue = 0;

		Optional<DiscountByQuantity> discount = findByDiscountId(discountId);

		if (discount.isPresent()) {
			
			if (discount.get().getQuantity() == 0) {
				return 0;
			}
			
			int numberOfDiscounts = productQuantity / discount.get().getQuantity();

			double discountAmount = discount.get().getDiscount();
			
			//value of discount received for the productQuantity of the price
			discountedValue = (price * discountAmount) * numberOfDiscounts;

			return discountedValue;
		}

		return -1;
	}

}
