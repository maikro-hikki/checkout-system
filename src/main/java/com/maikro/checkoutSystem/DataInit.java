//package com.maikro.checkoutSystem;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import com.maikro.checkoutSystem.constants.ProductType;
//import com.maikro.checkoutSystem.model.Admin;
//import com.maikro.checkoutSystem.model.Basket;
//import com.maikro.checkoutSystem.model.Customer;
//import com.maikro.checkoutSystem.model.DiscountByProduct;
//import com.maikro.checkoutSystem.model.DiscountByQuantity;
//import com.maikro.checkoutSystem.model.Product;
//import com.maikro.checkoutSystem.service.BasketService;
//import com.maikro.checkoutSystem.service.DiscountService;
//import com.maikro.checkoutSystem.service.ProductService;
//import com.maikro.checkoutSystem.service.UserClassService;
//
//@Component
//public class DataInit implements ApplicationRunner {
//
//	@Autowired
//	private ProductService productService;
//
//	@Autowired
//	private UserClassService userClassService;
//
//	@Autowired
//	private DiscountService discountService;
//	
//	@Autowired
//	private BasketService basketService;
//
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		
//		int number = 50;
//
//		Admin admin = new Admin("admin1", "123", "John", "Doe");
//		Admin admin2 = new Admin("admin2", "123", "John", "Doe");
//		Customer customer1 = new Customer("customer2", "123", "John", "Doe");
//		
//		userClassService.addAdminUser(admin);
//		userClassService.addAdminUser(admin2);
//		userClassService.addCustomerUser(customer1);
//
//		for (int i = 0; i < number; i++) {
//
//			DiscountByQuantity discount1 = new DiscountByQuantity(admin, i, 0.1);
//
//			discountService.addDiscountByQuantity(discount1);
//
//			DiscountByProduct discount2 = new DiscountByProduct(admin, 0.1);
//
//			discountService.addDiscountByProduct(discount2);
//
//		}
//
//		for (int i = 0; i < number; i++) {
//
//			Product product = new Product("Apple TV", i, i, ProductType.ELECTRONICS);
//			product.setAdmin(admin);
//			product.setRemainingQuantity(50);
//
//			productService.addNewProduct(product);
//			
//			basketService.addProductToBasket(customer1.getUserId(), product.getProductId(), 50);
//		}
//
//	}
//
//}
