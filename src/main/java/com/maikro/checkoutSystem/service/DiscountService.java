package com.maikro.checkoutSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Discount;
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.repository.DiscountRepo;

/**
 * Service class for managing discounts.
 */
@Service
public class DiscountService {
	
	@Autowired
	private DiscountRepo discountRepo;
	
	/**
	 * Retrieves a discount by its ID.
	 *
	 * @param discountId the ID of the discount
	 * @return an Optional containing the Discount if found, or an empty Optional if not found
	 */
	public Optional<Discount> findByDiscountId(long discountId){
		return discountRepo.findById(discountId);
	}
	
	/**
	 * Checks if a discount with the given ID exists.
	 *
	 * @param discountId the ID of the discount
	 * @return true if the discount exists, false otherwise
	 */
	public boolean discountExist(long discountId) {
		return discountRepo.findById(discountId).isPresent();
	}
	
	/**
	 * Adds a discount of type DiscountByQuantity.
	 *
	 * @param discount the DiscountByQuantity to be added
	 */
	public void addDiscountByQuantity(DiscountByQuantity discount) {
		discountRepo.save(discount);
	}
	
	/**
	 * Adds a discount of type DiscountByProduct.
	 *
	 * @param discount the DiscountByProduct to be added
	 */
	public void addDiscountByProduct(DiscountByProduct discount) {
		discountRepo.save(discount);
	}
	
	/**
	 * Removes a discount by its ID.
	 *
	 * @param discountId the ID of the discount to be removed
	 */
	public void removeDiscountById(long discountId) {
		discountRepo.deleteById(discountId);
	}

}
