package com.maikro.checkoutSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.DiscountByQuantity;

@SpringBootTest
class DiscountByQuantityServiceTest {
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private DiscountByQuantityService discountByQuantityService;
	
	@Autowired
	private UserClassService userClassService;

	@Test
	@DirtiesContext
	void testApplyDiscountByQuantity_InputDiscountIdPrice10AndQuantity5_ShouldReturn10() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin, with 50% discount for every second product of the same type
		DiscountByQuantity discount = new DiscountByQuantity();
		discount.setAdmin(admin);
		discount.setDiscount(0.5);
		discount.setQuantity(2);
		discountService.addDiscountByQuantity(discount);
		
		//inputs: discountId, price of 10, productQuantity of 5
		double discountAmount = discountByQuantityService.amountOfDiscount(discount.getDiscountId(), 10, 5);
		
		assertEquals(10, discountAmount);
	}
	
	@Test
	@DirtiesContext
	void testApplyDiscountByQuantity_InputDiscountIdPrice10AndQuantity5_ShouldReturn5() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin, with 50% discount for every third product of the same type
		DiscountByQuantity discount = new DiscountByQuantity();
		discount.setAdmin(admin);
		discount.setDiscount(0.5);
		discount.setQuantity(3);
		discountService.addDiscountByQuantity(discount);
		
		//inputs: discountId, price of 10, productQuantity of 5
		double discountAmount = discountByQuantityService.amountOfDiscount(discount.getDiscountId(), 10, 5);
		
		assertEquals(5, discountAmount);
	}
	
	@Test
	@DirtiesContext
	void testApplyDiscountByQuantity_InputDiscountIdPrice17AndQuantity5_ShouldReturn12point58() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin, with 37% discount for every third product of the same type
		DiscountByQuantity discount = new DiscountByQuantity();
		discount.setAdmin(admin);
		discount.setDiscount(0.37);
		discount.setQuantity(3);
		discountService.addDiscountByQuantity(discount);
		
		//inputs: discountId, price of 17, productQuantity of 7
		double discountAmount = discountByQuantityService.amountOfDiscount(discount.getDiscountId(), 17, 7);
		
		assertEquals(12.58, discountAmount);
	}

}
