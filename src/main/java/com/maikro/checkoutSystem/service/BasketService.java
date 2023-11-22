package com.maikro.checkoutSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.model.Basket;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.model.Discount;
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

	@Autowired
	private ProductDiscountService productDiscountService;

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

	/**
	 * Adds a product to the basket for a given user.
	 *
	 * @param userId    the ID of the user
	 * @param productId the ID of the product
	 * @param quantity  the quantity of the product to add
	 * @return true if the product was successfully added to the basket, false otherwise
	 */
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

	public boolean removeProductFromBasket(long userId, long productId) {

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

	public double calculateProductPrice(long productId, int quantity) {
		
		//get unit price of product
		double productUnitPrice = productService.getProductUnitPrice(productId);
		
		//return -1 if there is error
		if (productUnitPrice == -1) {
			
			return -1;
		}
		
		//get all discounts by productId
		List<Discount> discounts = productDiscountService.findDiscountsByProductId(productId);
		
		if (discounts.isEmpty()) {
			
			return productUnitPrice * quantity;
		}
		
		//calculate final price after adding discounts
		double finalProductPrice = productDiscountService.applyDiscounts(productUnitPrice, discounts, quantity);
		
		//returns final price, or -1 if there are error
		return finalProductPrice;
	}
	
	public double totalCostInBasket(long userId) {
		
		double totalCost = 0;
		
		List<Basket> basket = findByUserId(userId);
		
		for (int i = 0; i < basket.size(); i++) {
			totalCost += calculateProductPrice(basket.get(i).getProduct().getProductId(), basket.get(i).getQuantity());
		}
		
		return totalCost;
	}
	
	public int deductRemainingQuantityAfterPurchase(long userId) {
		
		List<Basket> checkout = findByUserId(userId);
		
		for (int i = 0; i < checkout.size(); i++) {
			
			Product product = productService.findByProductId(checkout.get(i).getProduct().getProductId()).orElse(null);
			
			if (product == null) {
				return -1;
			}
			
			int boughtQuantity = checkout.get(i).getQuantity();
			int remainingQuantity = product.getRemainingQuantity();
			
			if (boughtQuantity > remainingQuantity) {
				return -1;
			}
			
			product.setRemainingQuantity(remainingQuantity - boughtQuantity);
			
			productService.addNewProduct(product);
			
		}
		
		return 1;
	}

}
