package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.repository.DiscountByQuantityRepo;

/**
 * Service class for managing discounts type of quantity.
 */
@Service
public class DiscountByQuantityService {

	@Autowired
	private DiscountByQuantityRepo discountByQuantityRepo;

	/**
	 * Retrieves a discount by its ID.
	 *
	 * @param discountId the ID of the discount
	 * @return an Optional containing the DiscountByQuantity if found, or an empty Optional if not found
	 */
	public Optional<DiscountByQuantity> findByDiscountId(long discountId) {
		return discountByQuantityRepo.findById(discountId);
	}

	/**
	 * Calculates the amount of discount based on the discount ID, price of the product, and product quantity.
	 *
	 * @param discountId      the ID of the discount
	 * @param price           the price of the product (normally unit price)
	 * @param productQuantity the quantity of the product
	 * @return the amount of discount applied to the product, or -1 if the discount is not found
	 */
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
