//package com.maikro.checkoutSystem;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import com.maikro.checkoutSystem.constants.UserType;
//import com.maikro.checkoutSystem.model.Admin;
//import com.maikro.checkoutSystem.model.Customer;
//import com.maikro.checkoutSystem.model.Product;
//import com.maikro.checkoutSystem.service.BasketService;
//import com.maikro.checkoutSystem.service.ProductService;
//import com.maikro.checkoutSystem.service.UserClassService;
//
//@Component
//public class DataInit implements ApplicationRunner {
//	
//	@Autowired
//	private UserClassService userClassService;
//	
//	@Autowired
//	private ProductService productService;
//	
//	@Autowired
//	private BasketService basketService;
//
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		
//		Admin admin = new Admin(UserType.ADMIN);		
//		userClassService.addAdminUser(admin);
//		
//		Product product1 = new Product();
//		product1.setAdmin(admin);
//		product1.setRemainingQuantity(10);
//		productService.addNewProduct(product1);
//		
//		Product product2 = new Product();
//		product2.setAdmin(admin);
//		product2.setRemainingQuantity(20);
//		productService.addNewProduct(product2);
//		
//		Customer customer = new Customer(UserType.CUSTOMER);
//		userClassService.addCustomerUser(customer);
//		
//		basketService.addProductToBasket(customer.getUserId(), product1.getProductId(), 5);
//		basketService.addProductToBasket(customer.getUserId(), product2.getProductId(), 10);
//		
//	}
//
//}
