package com.maikro.checkoutSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.constants.DiscountType;
import com.maikro.checkoutSystem.model.Basket;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.ProductDiscount;
import com.maikro.checkoutSystem.repository.BasketRepo;

@Service
public class BasketService {

	@Autowired
	private BasketRepo basketRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductDiscountService productDiscountService;

	@Autowired
	private DiscountByQuantityService discountByQuantityService;

	@Autowired
	private DiscountByProductService discountByProductService;

	public Optional<Basket> findByBasketId(long basketId) {
		return basketRepo.findById(basketId);
	}

	public List<Basket> findByUserId(long userId) {
		return basketRepo.findByCustomerUserId(userId);
	}

	public int productInCustomerBasket(long userId, long productId) {

		List<Basket> retrievedBasket = findByUserId(userId);

		for (int i = 0; i < retrievedBasket.size(); i++) {

			if (productId == retrievedBasket.get(i).getProduct().getProductId()) {
				return i;
			}
		}

		return -1;
	}

	public boolean addProductToBasket(long userId, long productId, int quantity) {

		if (customerService.customerExist(userId)) {

			Customer customer = customerService.findByUserId(userId).get();

			if (productService.productExist(productId)) {

				Product product = productService.findByProductId(productId).get();

				if (quantity <= product.getRemainingQuantity() && quantity > 0) {

					int i = productInCustomerBasket(userId, productId);

					if (i >= 0) {
						List<Basket> retrievedBasket = findByUserId(userId);
						Basket basket = retrievedBasket.get(i);
						basket.setQuantity(basket.getQuantity() + quantity);
						basketRepo.save(basket);
					} else {
						Basket basket = new Basket(product, customer, quantity);
						basketRepo.save(basket);
					}

					return true;
				}
			}
		}

		return false;
	}

	public boolean removeProduct(long userId, long productId) {

		if (customerService.customerExist(userId)) {

			if (productService.productExist(productId)) {

				int i = productInCustomerBasket(userId, productId);

				if (i >= 0) {

					List<Basket> retrievedBasket = findByUserId(userId);

					basketRepo.deleteById(retrievedBasket.get(i).getBasketId());

					return true;
				}
			}
		}

		return false;
	}

	public double calculateProductPrice(long userId, long productId) {
		
		double productUnitPrice = productService.getProductUnitPrice(productId);
		
		return productUnitPrice;
	}

}
