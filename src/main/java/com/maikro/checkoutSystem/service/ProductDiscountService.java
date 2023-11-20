package com.maikro.checkoutSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.constants.DiscountType;
import com.maikro.checkoutSystem.model.Discount;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.ProductDiscount;
import com.maikro.checkoutSystem.repository.ProductDiscountRepo;

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

//	public void addNewProduct()

	public Optional<ProductDiscount> findByProductDiscountId(long productDiscountId) {
		return productDiscountRepo.findById(productDiscountId);
	}

	public List<ProductDiscount> findByProductId(long productId) {
		return productDiscountRepo.findByProductProductId(productId);
	}

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

	public boolean discountAlreadyAppliedToProduct(long productId, long discountId) {

		List<ProductDiscount> productDiscount = findByProductId(productId);

		if (!productDiscount.isEmpty()) {

			for (int i = 0; i < productDiscount.size(); i++) {
				if (productDiscount.get(i).getDiscount().getDiscountId() == discountId) {
					return true;
				}
			}
		}

		return false;
	}

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

	public void removeProductDiscountByProductDiscountId(long productDiscountId) {
		productDiscountRepo.deleteById(productDiscountId);
	}

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
	
	public List<Discount> findDiscountsByProductId(long productId){
		
		return productDiscountRepo.findDiscountsByProductId(productId);
	}
	
	//returns the final price of the product with the quantity and discounts applied
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

}
