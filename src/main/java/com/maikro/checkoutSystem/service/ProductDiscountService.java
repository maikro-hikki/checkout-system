package com.maikro.checkoutSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//	public void addNewProduct()

	public Optional<ProductDiscount> findByProductDiscountId(long productDiscountId) {
		return productDiscountRepo.findById(productDiscountId);
	}

	public List<ProductDiscount> findByProductId(long productId) {
		return productDiscountRepo.findByProductProductId(productId);
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

	public boolean addProductDiscount(long productId, long discountId) {

		if (productService.productExist(productId)) {

			if (discountService.discountExist(discountId)) {

				Product product = productService.findByProductId(productId).get();
				Discount discount = discountService.findByDiscountId(discountId).get();

				ProductDiscount productDiscount = new ProductDiscount(product, discount);

				productDiscountRepo.save(productDiscount);

				return true;
			}
		}

		return false;
	}

	public void removeProductDiscountById(long productDiscountId) {
		productDiscountRepo.deleteById(productDiscountId);
	}

}
