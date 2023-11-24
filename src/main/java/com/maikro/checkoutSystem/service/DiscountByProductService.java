package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.repository.DiscountByProductRepo;

/**
 * Service class for managing discount type of individual product.
 */
@Service
public class DiscountByProductService {
	
	@Autowired
	private DiscountByProductRepo discountByProductRepo;

	/**
	 * Retrieves an individual product type discount by its ID.
	 *
	 * @param discountId the ID of the discount
	 * @return an Optional containing the DiscountByProduct if found, or an empty Optional if not found
	 */
	public Optional<DiscountByProduct> findByDiscountId(long discountId) {
		return discountByProductRepo.findById(discountId);
	}
	
	/**
	 * Calculates the amount of discount for a product based on the discount ID, price, and quantity.
	 *
	 * @param discountId      the ID of the discount
	 * @param price           the price of the product (normally the unit price)
	 * @param productQuantity the quantity of the product
	 * @return the amount of discount for the product, or -1 if the discount is not found
	 */
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
