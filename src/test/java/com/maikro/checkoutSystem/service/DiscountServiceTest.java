package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Discount;
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.repository.DiscountRepo;

@SpringBootTest
class DiscountServiceTest {

	@Autowired
	private DiscountRepo discountRepo;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private UserClassService userClassService;

	Admin admin = new Admin("admin", "123", "John", "Doe");
	DiscountByQuantity discount1 = new DiscountByQuantity(admin, 3, 0.2);
	DiscountByProduct discount2 = new DiscountByProduct(admin, 0.1);

	@BeforeEach
	void setUp() {

		userClassService.addAdminUser(admin);
		discountService.addDiscountByQuantity(discount1);
		discountService.addDiscountByProduct(discount2);
	}

	@Test
	@DirtiesContext
	void testAddDiscountByQuantity_ShouldAddTypeDiscountByQuantity_ToDataBase() {

		// use the discountRepo.findById method to find discount in repo
		Discount savedDiscount = discountRepo.findById(discount1.getDiscountId()).orElse(null);

		// check that the discount exists in the repo or not
		assertThat(savedDiscount).isNotNull();
		assertThat(savedDiscount.getDiscountId()).isEqualTo(discount1.getDiscountId());

	}

	@Test
	@DirtiesContext
	void testAddDiscountByProduct_ShouldAddTypeDiscountByProduct_ToDataBase() {

		// use the discountRepo.findById method to find discount in repo
		Discount savedDiscount = discountRepo.findById(discount2.getDiscountId()).orElse(null);

		// check that the discount exists in the repo or not
		assertThat(savedDiscount).isNotNull();
		assertThat(savedDiscount.getDiscountId()).isEqualTo(discount2.getDiscountId());

	}

	@Test
	@DirtiesContext
	void testRemoveDiscountById_ShouldRemoveTypeDiscountByQuantity_FromDatabase() {

		// call the method to remove the discount
		discountService.removeDiscountById(discount1.getDiscountId());

		// verify that the product is no longer in the repository
		assertThat(discountRepo.findById(discount1.getDiscountId())).isEmpty();

	}

	@Test
	@DirtiesContext
	void testRemoveDiscountById_ShouldRemoveTypeDiscountByProduct_FromDatabase() {

		// call the method to remove the discount
		discountService.removeDiscountById(discount2.getDiscountId());

		// verify that the product is no longer in the repository
		assertThat(discountRepo.findById(discount2.getDiscountId())).isEmpty();

	}

}
