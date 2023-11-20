package com.maikro.checkoutSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.DiscountByProduct;

@SpringBootTest
class DiscountByProductServiceTest {

	@Autowired
	private DiscountService discountService;

	@Autowired
	private DiscountByProductService discountByProductService;

	@Autowired
	private UserClassService userClassService;

	@Test
	void testAmountOfDiscount_InputDiscountIdPrice10AndQuantity5_ShouldReturn25() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin, with 50% discount for
		// the product
		DiscountByProduct discount = new DiscountByProduct();
		discount.setAdmin(admin);
		discount.setDiscount(0.5);
		discountService.addDiscountByProduct(discount);

		// inputs: discountId, price of 10, productQuantity of 5
		double discountAmount = discountByProductService.amountOfDiscount(discount.getDiscountId(), 10, 5);

		assertEquals(25, discountAmount);
	}
	
	@Test
	void testAmountOfDiscount_InputDiscountIdPrice10AndQuantity5_ShouldReturn10() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin, with 20% discount for
		// the product
		DiscountByProduct discount = new DiscountByProduct();
		discount.setAdmin(admin);
		discount.setDiscount(0.2);
		discountService.addDiscountByProduct(discount);

		// inputs: discountId, price of 10, productQuantity of 5
		double discountAmount = discountByProductService.amountOfDiscount(discount.getDiscountId(), 10, 5);

		assertEquals(10, discountAmount);
	}
	
	@Test
	void testAmountOfDiscount_InputDiscountIdPrice10AndQuantity5_ShouldReturn44point03() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin, with 37% discount for
		// the product
		DiscountByProduct discount = new DiscountByProduct();
		discount.setAdmin(admin);
		discount.setDiscount(0.37);
		discountService.addDiscountByProduct(discount);

		// inputs: discountId, price of 10, productQuantity of 5
		double discountAmount = discountByProductService.amountOfDiscount(discount.getDiscountId(), 17, 7);

		assertEquals(44.03, discountAmount);
	}

}
