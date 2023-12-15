package com.maikro.checkoutSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maikro.checkoutSystem.constants.DiscountType;
import com.maikro.checkoutSystem.model.Discount;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.ProductDiscount;
import com.maikro.checkoutSystem.repository.ProductDiscountRepo;

/**
 * Service class for managing product discounts.
 */
@Service
public class ProductDiscountService {

	@Autowired
	private ProductDiscountRepo productDiscountRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private DiscountByProductService discountByProductService;
	
	@Autowired
	private DiscountByQuantityService discountByQuantityService;

	/**
	 * Retrieves a product discount by its ID.
	 *
	 * @param productDiscountId the ID of the product discount
	 * @return an Optional containing the ProductDiscount if found, or an empty Optional if not found
	 */
	public Optional<ProductDiscount> findByProductDiscountId(long productDiscountId) {
		return productDiscountRepo.findById(productDiscountId);
	}

	/**
	 * Retrieves all product discounts for a given product.
	 *
	 * @param productId the ID of the product
	 * @return a list of ProductDiscount objects associated with the product
	 */
	public List<ProductDiscount> findByProductId(long productId) {
		return productDiscountRepo.findByProductProductId(productId);
	}

	/**
	 * Retrieves a specific product discount by the product and discount IDs.
	 *
	 * @param productId  the ID of the product
	 * @param discountId the ID of the discount
	 * @return the ProductDiscount matching the product and discount IDs, or null if not found
	 */
	public ProductDiscount findByProductIdAndDiscountId(long productId, long discountId) {

		if (productService.productExist(productId)) {

			if (discountService.discountExist(discountId)) {

				List<ProductDiscount> productDiscounts = findByProductId(productId);

				for (int i = 0; i < productDiscounts.size(); i++) {
					if (productDiscounts.get(i).getDiscount().getDiscountId() == discountId) {
						return productDiscounts.get(i);
					}
				}
			}
		}

		return null;
	}

	/**
	 * Checks if a specific discount is already applied to a product.
	 *
	 * @param productId  the ID of the product
	 * @param discountId the ID of the discount
	 * @return true if the discount is already applied to the product, false otherwise
	 */
	public boolean discountAlreadyAppliedToProduct(long productId, long discountId) {

		List<ProductDiscount> productDiscount = findByProductId(productId);
		
		if (!productDiscount.isEmpty()) {
			
			for (int i = 0; i < productDiscount.size(); i++) {
				
				Discount discount = productDiscount.get(i).getDiscount();
				
				if (discount.getDiscountId() == discountId || discountTypeAppliedToProduct(productId, discountId)) {
					
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Adds a product discount by specifying the product and discount IDs.
	 *
	 * @param productId  the ID of the product
	 * @param discountId the ID of the discount
	 * @return true if the product discount is successfully added, false otherwise
	 * @Transactional This method is executed within a transaction
	 */
	@Transactional
	public boolean addProductDiscountByProductAndDiscount(long productId, long discountId) {

		if (productService.productExist(productId)) {

			if (discountService.discountExist(discountId)) {

				if (!discountAlreadyAppliedToProduct(productId, discountId)) {

					Product product = productService.findByProductId(productId).get();
					Discount discount = discountService.findByDiscountId(discountId).get();

					ProductDiscount productDiscount = new ProductDiscount(product, discount);

					productDiscountRepo.save(productDiscount);

					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Adds a product discount.
	 *
	 * @param productDiscount the ProductDiscount to be added
	 * @return true if the product discount is successfully added, false otherwise
	 * @Transactional This method is executed within a transaction
	 */
	@Transactional
	public boolean addProductDiscount(ProductDiscount productDiscount) {

		long productId = productDiscount.getProduct().getProductId();
		long discountId = productDiscount.getDiscount().getDiscountId();
		
		if (productService.productExist(productId)) {
			
			if (discountService.discountExist(discountId)) {
				
				if (!discountAlreadyAppliedToProduct(productId, discountId)) {
					
					productDiscountRepo.save(productDiscount);

					return true;

				}
			}
		}

		return false;
	}

	/**
	 * Removes a product discount by its ID.
	 *
	 * @param productDiscountId the ID of the product discount to be removed
	 * @Transactional This method is executed within a transaction
	 */
	@Transactional
	public void removeProductDiscountByProductDiscountId(long productDiscountId) {
		productDiscountRepo.deleteById(productDiscountId);
	}

	/**
	 * Removes a product discount by specifying the product and discount IDs.
	 *
	 * @param productId  the ID of the product
	 * @param discountId the ID of the discount
	 * @return true if the product discount is successfully removed, false otherwise
	 * @Transactional This method is executed within a transaction
	 */
	@Transactional
	public boolean removeProductDiscountByProductIdAndDiscountId(long productId, long discountId) {

		if (productService.productExist(productId)) {

			if (discountService.discountExist(discountId)) {

				if (discountAlreadyAppliedToProduct(productId, discountId)) {

					ProductDiscount productDiscount = findByProductIdAndDiscountId(productId, discountId);

					productDiscountRepo.deleteById(productDiscount.getProductDiscountId());

					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * Retrieves the discounts applied to a product.
	 *
	 * @param productId the ID of the product
	 * @return a list of Discount objects applied to the product
	 */
	public List<Discount> findDiscountsByProductId(long productId){
		
		return productDiscountRepo.findDiscountsByProductId(productId);
	}
	
	/**
	 * Calculates the final price of a product with the quantity and discounts applied.
	 *
	 * @param productUnitPrice the unit price of the product
	 * @param discounts        a list of Discount objects applied to the product
	 * @param quantity         the quantity of the product
	 * @return the final price of the product with the discounts applied, or -1 if there are invalid inputs
	 */
	public double applyDiscounts(double productUnitPrice, List<Discount> discounts, int quantity) {
		
		if (discounts.isEmpty()) {
			return -1;
		}
		
		if (productUnitPrice < 0) {
			return -1;
		}
		
		if (quantity < 0) {
			return -1;
		}
		
		double discountedPrice = productUnitPrice * quantity;
		
		for (int i = 0; i < discounts.size(); i++) {
			
			//applies price deduction based on the type of discount
			if (discounts.get(i).getDiscountType() == DiscountType.INDIVIDUAL_PRODUCT) {
				
				discountedPrice = discountedPrice - discountByProductService.amountOfDiscount(discounts.get(i).getDiscountId(), productUnitPrice, quantity);
				
			} else if(discounts.get(i).getDiscountType() == DiscountType.QUANTITY){
				
				discountedPrice = discountedPrice - discountByQuantityService.amountOfDiscount(discounts.get(i).getDiscountId(), productUnitPrice, quantity);
			}
		}
		
		return discountedPrice;
	}
	
	/**
	 * Checks if a discount type is applied to a product.
	 *
	 * @param productId  the ID of the product
	 * @param discountId the ID of the discount
	 * @return true if the discount type is applied to the product, false otherwise
	 */
	public boolean discountTypeAppliedToProduct(long productId, long discountId) {
		
		List<Discount> discounts = findDiscountsByProductId(productId);
		
		Discount discount = discountService.findByDiscountId(discountId).get();
		
		for (int i = 0; i < discounts.size(); i++) {
			
			if (discounts.get(i).getDiscountType() == discount.getDiscountType()) {
				
				return true;
			}
		}
		
		return false;
	}

}
