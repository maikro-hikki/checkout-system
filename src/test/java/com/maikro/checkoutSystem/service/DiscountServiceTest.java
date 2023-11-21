package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;

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

	@Test
	@DirtiesContext
	void testAddDiscountByQuantity_ShouldAddTypeDiscountByQuantity_ToDataBase() {
		
		//create an admin to make a discount
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a quantity discount and set it under the admin
        DiscountByQuantity discount = new DiscountByQuantity();
        discount.setAdmin(admin);
        
        //add the created discount into the repository
        discountService.addDiscountByQuantity(discount);
        
        //use the discountRepo.findById method to find discount in repo
        Discount savedDiscount = discountRepo.findById(discount.getDiscountId()).orElse(null);
        
        //check that the discount exists in the repo or not
        assertThat(savedDiscount).isNotNull();
        assertThat(savedDiscount.getDiscountId()).isEqualTo(discount.getDiscountId());
        
	}
	
	@Test
	@DirtiesContext
	void testAddDiscountByProduct_ShouldAddTypeDiscountByProduct_ToDataBase() {
		
		//create an admin to make a discount
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a product discount and set it under the admin
        DiscountByProduct discount = new DiscountByProduct();
        discount.setAdmin(admin);
        
        //add the created discount into the repository
        discountService.addDiscountByProduct(discount);
        
        //use the discountRepo.findById method to find discount in repo
        Discount savedDiscount = discountRepo.findById(discount.getDiscountId()).orElse(null);
        
        //check that the discount exists in the repo or not
        assertThat(savedDiscount).isNotNull();
        assertThat(savedDiscount.getDiscountId()).isEqualTo(discount.getDiscountId());
        
	}
	
	@Test
	@DirtiesContext
    void testRemoveDiscountById_ShouldRemoveTypeDiscountByQuantity_FromDatabase() {
    	
		//create an admin to make a discount
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a quantity discount and set it under the admin
        DiscountByQuantity discount = new DiscountByQuantity();
        discount.setAdmin(admin);
        
        //add the created discount into the repository
        discountService.addDiscountByQuantity(discount);
        
        //call the method to remove the discount
        discountService.removeDiscountById(discount.getDiscountId());
        
        //verify that the product is no longer in the repository
        assertThat(discountRepo.findById(discount.getDiscountId())).isEmpty();
        
    }
	
	@Test
	@DirtiesContext
    void testRemoveDiscountById_ShouldRemoveTypeDiscountByProduct_FromDatabase() {
    	
		//create an admin to make a discount
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a product discount and set it under the admin
        DiscountByProduct discount = new DiscountByProduct();
        discount.setAdmin(admin);
        
        //add the created discount into the repository
        discountService.addDiscountByProduct(discount);
        
        //call the method to remove the discount
        discountService.removeDiscountById(discount.getDiscountId());
        
        //verify that the product is no longer in the repository
        assertThat(discountRepo.findById(discount.getDiscountId())).isEmpty();
        
    }

}
