package com.maikro.checkoutSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.service.DiscountService;
import com.maikro.checkoutSystem.service.ProductService;
import com.maikro.checkoutSystem.service.UserClassService;

@Component
public class DataInit implements ApplicationRunner {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserClassService userClassService;
	
	@Autowired
	private DiscountService discountService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		int number = 50;
		
		Admin admin = new Admin("admin1", "123", "John", "Doe");
		
		userClassService.addAdminUser(admin);
		
		for (int i = 0; i < number; i++) {
			
			DiscountByQuantity discount1 = new DiscountByQuantity(admin, i, 0.1);
			
			discountService.addDiscountByQuantity(discount1);
			
			DiscountByProduct discount2 = new DiscountByProduct(admin, 0.1);
			
			discountService.addDiscountByProduct(discount2);
		
		}
		
	}

}
