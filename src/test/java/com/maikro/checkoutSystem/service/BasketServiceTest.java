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
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.ProductDiscount;

@SpringBootTest
class BasketServiceTest {

	@Autowired
	private BasketService basketService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserClassService userClassService;

	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private ProductDiscountService productDiscountService;

	@Test
	void testAddProductToBasket_ShouldReturnTrue_IfSuccessfullyAddedWithValidInputs() {

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
	void testAddProductToBasket_ShouldReturnFalse_IfCustomerOrProductNotInDataBase() {

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
	void testAddProductToBasket_ShouldReturnFalse_IfQuantityIsInvalid() {

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

		// valid range: quantity <= 10 && quantity > 0

		// valid quantity of 5
		assertTrue(basketService.addProductToBasket(customer1.getUserId(), product.getProductId(), 5));

		// invalid quantity -10 < 0
		assertFalse(basketService.addProductToBasket(customer2.getUserId(), product.getProductId(), -10));

		// invalid quantity 0 = 0
		assertFalse(basketService.addProductToBasket(customer3.getUserId(), product.getProductId(), 0));

		// invalid quantity 20 > 10
		assertFalse(basketService.addProductToBasket(customer4.getUserId(), product.getProductId(), 20));

		// valid quantity 10 = 10
		assertTrue(basketService.addProductToBasket(customer5.getUserId(), product.getProductId(), 10));

	}

	@Test
	void testFindByCustomerId_ShouldReturnSameNumberOfItems_AsExpectedNumber() {

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

	@Test
	void testAddProductToBasket_ShouldIncreaseTheQuantityOfProduct_IfProductAlreadyExists() {

		Customer customer1 = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer1);

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

		basketService.addProductToBasket(customer1.getUserId(), product1.getProductId(), 5);
		basketService.addProductToBasket(customer1.getUserId(), product2.getProductId(), 5);
		basketService.addProductToBasket(customer1.getUserId(), product1.getProductId(), 2);

		List<Basket> retrievedBasket = basketService.findByUserId(customer1.getUserId());

		assertEquals(7, retrievedBasket.get(0).getQuantity());

	}

	@Test
	void testRemoveProduct_ShouldReturnTrue_IfSuccessfullyRemovedProductFromBasket() {

		Customer customer = new Customer(UserType.CUSTOMER);
		userClassService.addCustomerUser(customer);

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

		basketService.addProductToBasket(customer.getUserId(), product1.getProductId(), 5);
		basketService.addProductToBasket(customer.getUserId(), product2.getProductId(), 5);

		basketService.removeProduct(customer.getUserId(), product1.getProductId());

		assertEquals(-1, basketService.productInCustomerBasket(customer.getUserId(), product1.getProductId()));
		assertTrue(basketService.productInCustomerBasket(customer.getUserId(), product2.getProductId()) >= 0);

	}

	@Test
	void testCalculateProductPrice_ShouldReturnPriceAfterDiscountOfProducts_ExpectedResultOf43() {

		Admin admin = new Admin(UserType.ADMIN);
		userClassService.addAdminUser(admin);

		Product product = new Product();
		product.setAdmin(admin);
		product.setUnitPrice(10);
		product.setRemainingQuantity(20);
		productService.addNewProduct(product);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discount1.setDiscount(0.2);
		discount1.setQuantity(2);
		discountService.addDiscountByQuantity(discount1);
		// create a product discount and set it under the admin
		DiscountByProduct discount2 = new DiscountByProduct();
		discount2.setAdmin(admin);
		discount2.setDiscount(0.3);
		discountService.addDiscountByProduct(discount2);

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		double finalPrice = basketService.calculateProductPrice(product.getProductId(), 7);

		assertEquals(43, finalPrice);

	}
	
	@Test
	void testCalculateProductPrice_ShouldReturnPriceAfterDiscountOfProducts_ExpectedResultOf216() {

		Admin admin = new Admin(UserType.ADMIN);
		userClassService.addAdminUser(admin);

		Product product = new Product();
		product.setAdmin(admin);
		product.setUnitPrice(40);
		product.setRemainingQuantity(20);
		productService.addNewProduct(product);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discount1.setDiscount(0.1);
		discount1.setQuantity(3);
		discountService.addDiscountByQuantity(discount1);
		// create a product discount and set it under the admin
		DiscountByProduct discount2 = new DiscountByProduct();
		discount2.setAdmin(admin);
		discount2.setDiscount(0.2);
		discountService.addDiscountByProduct(discount2);

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		double finalPrice = basketService.calculateProductPrice(product.getProductId(), 7);

		assertEquals(216, finalPrice);

	}
	
	@Test
	void testCalculateProductPrice_ForProductNotFound_ShouldReturnNegative1() {

		Admin admin = new Admin(UserType.ADMIN);
		userClassService.addAdminUser(admin);

		//product created but not added to database
		Product product = new Product();
		product.setAdmin(admin);
		product.setUnitPrice(40);
		product.setRemainingQuantity(20);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discount1.setDiscount(0.1);
		discount1.setQuantity(3);
		discountService.addDiscountByQuantity(discount1);
		// create a product discount and set it under the admin
		DiscountByProduct discount2 = new DiscountByProduct();
		discount2.setAdmin(admin);
		discount2.setDiscount(0.2);
		discountService.addDiscountByProduct(discount2);

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		double finalPrice = basketService.calculateProductPrice(product.getProductId(), 7);

		assertEquals(-1, finalPrice);

	}
	
	@Test
	void testCalculateProductPrice_ForProductUnitPriceLessThan0_ShouldReturnNegative1() {

		Admin admin = new Admin(UserType.ADMIN);
		userClassService.addAdminUser(admin);

		Product product = new Product();
		product.setAdmin(admin);
		//unit price is set to -40 which is less than 0
		product.setUnitPrice(-40);
		product.setRemainingQuantity(20);
		productService.addNewProduct(product);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discount1.setDiscount(0.1);
		discount1.setQuantity(3);
		discountService.addDiscountByQuantity(discount1);
		// create a product discount and set it under the admin
		DiscountByProduct discount2 = new DiscountByProduct();
		discount2.setAdmin(admin);
		discount2.setDiscount(0.2);
		discountService.addDiscountByProduct(discount2);

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		double finalPrice = basketService.calculateProductPrice(product.getProductId(), 7);

		assertEquals(-1, finalPrice);

	}
	
	@Test
	void testCalculateProductPrice_ForQuantityLessThan0_ShouldReturnNegative1() {

		Admin admin = new Admin(UserType.ADMIN);
		userClassService.addAdminUser(admin);

		Product product = new Product();
		product.setAdmin(admin);
		product.setUnitPrice(40);
		product.setRemainingQuantity(20);
		productService.addNewProduct(product);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discount1.setDiscount(0.1);
		discount1.setQuantity(3);
		discountService.addDiscountByQuantity(discount1);
		// create a product discount and set it under the admin
		DiscountByProduct discount2 = new DiscountByProduct();
		discount2.setAdmin(admin);
		discount2.setDiscount(0.2);
		discountService.addDiscountByProduct(discount2);

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);
		
		//quantity attribute is set to -7 which is less than 0
		double finalPrice = basketService.calculateProductPrice(product.getProductId(), -7);

		assertEquals(-1, finalPrice);

	}
	
	@Test
	void testCalculateProductPrice_ForProductWithoutDiscount_ShouldReturn280() {

		Admin admin = new Admin(UserType.ADMIN);
		userClassService.addAdminUser(admin);

		Product product = new Product();
		product.setAdmin(admin);
		product.setUnitPrice(40);
		product.setRemainingQuantity(20);
		productService.addNewProduct(product);

		//quantity of 7
		double finalPrice = basketService.calculateProductPrice(product.getProductId(), 7);

		assertEquals(280, finalPrice);

	}

}
