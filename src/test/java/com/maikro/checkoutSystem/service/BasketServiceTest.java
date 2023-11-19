package com.maikro.checkoutSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maikro.checkoutSystem.constants.UserType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Basket;
import com.maikro.checkoutSystem.model.Customer;
import com.maikro.checkoutSystem.model.Product;

@SpringBootTest
class BasketServiceTest {
	
	@Autowired
	private BasketService basketService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
    private UserClassService userClassService;
	
	@Autowired
	private CustomerService customerService;

	@Test
	void testAddProductToBasket_ShouldReturnTrueIfSuccessfullyAddedWithValidInputs() {
			
		Customer customer1 = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer1);	
		
		Admin admin = new Admin(UserType.ADMIN);		
		userClassService.addAdminUser(admin);
		
		Product product = new Product();
		product.setAdmin(admin);
		product.setRemainingQuantity(10);		
		productService.addNewProduct(product);	
		
		assertTrue(basketService.addProductToBasket(customer1.getUserId(), product.getProductId(), 5));
		
	}
	
	@Test
	void testAddProductToBasket_ShouldReturnFalseIfCustomerOrProductNotInDataBase() {
			
		Customer customer1 = new Customer(UserType.CUSTOMER);
		
		Admin admin = new Admin(UserType.ADMIN);
		userClassService.addAdminUser(admin);
		
		Product product = new Product();
		product.setAdmin(admin);
		product.setRemainingQuantity(10);
		
		assertFalse(basketService.addProductToBasket(customer1.getUserId(), product.getProductId(), 5));
		
		userClassService.addCustomerUser(customer1);
		
		assertFalse(basketService.addProductToBasket(customer1.getUserId(), product.getProductId(), 5));
		
		productService.addNewProduct(product);
		
		assertTrue(basketService.addProductToBasket(customer1.getUserId(), product.getProductId(), 5));
		
	}
	
	@Test
	void testAddProductToBasket_ShouldReturnFalseIfQuantityIsInvalid() {
			
		Customer customer1 = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer1);
		Customer customer2 = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer2);
		Customer customer3 = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer3);
		Customer customer4 = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer4);
		Customer customer5 = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer5);
		
		Admin admin = new Admin(UserType.ADMIN);		
		userClassService.addAdminUser(admin);
		
		Product product = new Product();
		product.setAdmin(admin);
		product.setRemainingQuantity(10);		
		productService.addNewProduct(product);
		
		//valid range: quantity <= 10 && quantity > 0
		
		//valid quantity of 5
		assertTrue(basketService.addProductToBasket(customer1.getUserId(), product.getProductId(), 5));
		
		//invalid quantity -10 < 0
		assertFalse(basketService.addProductToBasket(customer2.getUserId(), product.getProductId(), -10));
		
		//invalid quantity 0 = 0
		assertFalse(basketService.addProductToBasket(customer3.getUserId(), product.getProductId(), 0));
		
		//invalid quantity 20 > 10
		assertFalse(basketService.addProductToBasket(customer4.getUserId(), product.getProductId(), 20));
		
		//valid quantity 10 = 10
		assertTrue(basketService.addProductToBasket(customer5.getUserId(), product.getProductId(), 10));
		
	}
	
	@Test
	void testFindByCustomerId_ShouldReturnSameNumberOfItemsAsExpectedNumber() {
		
		Admin admin = new Admin(UserType.ADMIN);		
		userClassService.addAdminUser(admin);
		
		Product product1 = new Product();
		product1.setAdmin(admin);
		product1.setRemainingQuantity(10);
		productService.addNewProduct(product1);
		
		Product product2 = new Product();
		product2.setAdmin(admin);
		product2.setRemainingQuantity(20);
		productService.addNewProduct(product2);
		
		Customer customer1 = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer1);
		Customer customer2 = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer2);
		
		basketService.addProductToBasket(customer1.getUserId(), product1.getProductId(), 5);
		basketService.addProductToBasket(customer1.getUserId(), product2.getProductId(), 10);
		basketService.addProductToBasket(customer2.getUserId(), product2.getProductId(), 2);
		
		List<Basket> retrievedBasket = basketService.findByUserId(customer1.getUserId());
		
		assertEquals(2, retrievedBasket.size());
	}

}
