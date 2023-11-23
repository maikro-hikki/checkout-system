package com.maikro.checkoutSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

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
	
	Admin admin = new Admin("admin", "123", "John", "Doe");
	DiscountByProduct discount = new DiscountByProduct(admin, 0.1);
	
	@BeforeEach
	void setUp() {		
		
		userClassService.addAdminUser(admin);
		discountService.addDiscountByProduct(discount);
		
	}

	@Test
	@DirtiesContext
	void testAmountOfDiscount__ShouldReturn5() {

		// inputs: discountId, price of 10, productQuantity of 5
		double discountAmount = discountByProductService.amountOfDiscount(discount.getDiscountId(), 10, 5);

		assertEquals(5, discountAmount);
	}
	
	@Test
	@DirtiesContext
	void testAmountOfDiscount__ShouldReturn11point9() {

		// inputs: discountId, price of 10, productQuantity of 5
		double discountAmount = discountByProductService.amountOfDiscount(discount.getDiscountId(), 17, 7);

		assertEquals(11.9, discountAmount);
	}

}
