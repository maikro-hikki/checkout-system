package com.maikro.checkoutSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.maikro.checkoutSystem.constants.ProductType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.service.ProductService;
import com.maikro.checkoutSystem.service.UserClassService;

@Component
public class DataInit implements ApplicationRunner {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserClassService userClassService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		int number = 100;
		
		Admin admin = new Admin("admin1", "123", "John", "Doe");
		
		userClassService.addAdminUser(admin);
		
		for (int i = 0; i < number; i++) {
			
			Product product = new Product("product " + i, i, i, ProductType.ELECTRONICS);
			product.setAdmin(admin);
			
			productService.addNewProduct(product);
		}
		
	}

}
