package com.maikro.checkoutSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Basket;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.repository.BasketRepo;

@Service
public class BasketService {

	@Autowired
	private BasketRepo basketRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;
	
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
					
					if ( i >= 0) {
						List<Basket> retrievedBasket = findByUserId(userId);
						Basket basket = retrievedBasket.get(i);
						basket.setQuantity(basket.getQuantity() + quantity);
						basketRepo.save(basket);
					}else {
						Basket basket = new Basket(product, customer, quantity);
						basketRepo.save(basket);
					}

					return true;
				}
			}
		}

		return false;
	}

}
