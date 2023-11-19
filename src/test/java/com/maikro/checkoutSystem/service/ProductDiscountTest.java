package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.ProductDiscount;
import com.maikro.checkoutSystem.repository.ProductDiscountRepo;

@SpringBootTest
class ProductDiscountTest {
	
	@Autowired
	private ProductDiscountRepo productDiscountRepo;
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private ProductDiscountService productDiscountService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserClassService userClassService;

	@Test
	void testAddProductDiscount_ShouldAddProductDiscount_InDatabase() {
		
		//create an admin to make a discount
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	
    	//create a quantity discount and set it under the admin
        DiscountByQuantity discount = new DiscountByQuantity();
        discount.setAdmin(admin);
        discountService.addDiscountByQuantity(discount);
        
        //create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);  
        productService.addNewProduct(product);
        
        //add discount to product
        productDiscountService.addProductDiscount(product.getProductId(), discount.getDiscountId());
        
        List<ProductDiscount> productDiscount = productDiscountService.findByProductId(product.getProductId());
        
        assertThat(productDiscount).isNotNull();
        assertEquals(1, productDiscount.size());
	}
	
	@Test
	void testDiscountAlreadyAppliedToProduct_ShouldReturnTrue_IfDiscountAlreadyAppliedToProduct() {
		
		//create an admin to make a discount
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	
    	//create a quantity discount and set it under the admin
        DiscountByQuantity discount = new DiscountByQuantity();
        discount.setAdmin(admin);
        discountService.addDiscountByQuantity(discount);
        
        //create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);  
        productService.addNewProduct(product);
        
        //add discount to product
        productDiscountService.addProductDiscount(product.getProductId(), discount.getDiscountId());
        
        assertTrue(productDiscountService.discountAlreadyAppliedToProduct(product.getProductId(), discount.getDiscountId()));
	}
	
	@Test
	void testDiscountAlreadyAppliedToProduct_ShouldReturnFalse_IfDiscountNotYetAppliedToProduct() {
		
		//create an admin to make a discount
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	
    	//create a quantity discount and set it under the admin
        DiscountByQuantity discount1 = new DiscountByQuantity();
        discount1.setAdmin(admin);
        discountService.addDiscountByQuantity(discount1);
        
        DiscountByQuantity discount2 = new DiscountByQuantity();
        discount2.setAdmin(admin);
        discountService.addDiscountByQuantity(discount2);
        
        //create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);  
        productService.addNewProduct(product);
        
        //add discount to product
        productDiscountService.addProductDiscount(product.getProductId(), discount1.getDiscountId());
        
        assertFalse(productDiscountService.discountAlreadyAppliedToProduct(product.getProductId(), discount2.getDiscountId()));
	}

}
