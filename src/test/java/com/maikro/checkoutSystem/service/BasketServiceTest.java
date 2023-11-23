package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.maikro.checkoutSystem.constants.ProductType;
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

	Admin admin = new Admin("admin", "123", "John", "Doe");
	Customer customer = new Customer("customer", "123", "John", "Doe");
	Customer customer1 = new Customer("customer1", "123", "John", "Doe");
	Customer customer2 = new Customer("customer2", "123", "John", "Doe");
	Customer customer3 = new Customer("customer3", "123", "John", "Doe");
	Customer customer4 = new Customer("customer4", "123", "John", "Doe");
	Customer customer5 = new Customer("customer5", "123", "John", "Doe");
	DiscountByQuantity discount1 = new DiscountByQuantity(admin, 3, 0.2);
	DiscountByProduct discount2 = new DiscountByProduct(admin, 0.1);
	Product product = new Product("Apple TV", 10.5, 50, ProductType.ELECTRONICS);
	Product product1 = new Product("Apple TV", 5.5, 30, ProductType.ELECTRONICS);
	Product product2 = new Product("Samsung TV", 20.5, 70, ProductType.ELECTRONICS);

	@BeforeEach
	void setUp() {

		userClassService.addAdminUser(admin);
		userClassService.addCustomerUser(customer);
		userClassService.addCustomerUser(customer1);
		userClassService.addCustomerUser(customer2);
		userClassService.addCustomerUser(customer3);
		userClassService.addCustomerUser(customer4);
		userClassService.addCustomerUser(customer5);
		discountService.addDiscountByQuantity(discount1);
		discountService.addDiscountByProduct(discount2);
		product.setAdmin(admin);
		product1.setAdmin(admin);
		product2.setAdmin(admin);
		productService.addNewProduct(product);
		productService.addNewProduct(product1);
		productService.addNewProduct(product2);

	}

	@Test
	@DirtiesContext
	void testAddProductToBasket_ShouldReturnTrue_IfSuccessfullyAddedWithValidInputs() {

		assertThat(basketService.addProductToBasket(customer.getUserId(), product.getProductId(), 5)).isNotNull();

	}

	@Test
	@DirtiesContext
	void testAddProductToBasket_ShouldReturnFalse_IfCustomerOrProductNotInDataBase() {

		Product product8 = new Product("Apple TV", 10.5, 50, ProductType.ELECTRONICS);
		Customer customer8 = new Customer("customer8", "123", "John", "Doe");

		assertThat(basketService.addProductToBasket(customer8.getUserId(), product8.getProductId(), 5)).isNull();

		userClassService.addCustomerUser(customer8);

		assertThat(basketService.addProductToBasket(customer8.getUserId(), product8.getProductId(), 5)).isNull();

		productService.addNewProduct(product8);

		assertThat(basketService.addProductToBasket(customer.getUserId(), product.getProductId(), 5)).isNotNull();

	}

	@Test
	@DirtiesContext
	void testAddProductToBasket_ShouldReturnFalse_IfQuantityIsInvalid() {

		// valid range: quantity <= 50 && quantity > 0
		assertThat(basketService.addProductToBasket(customer1.getUserId(), product.getProductId(), 5)).isNotNull();

		assertThat(basketService.addProductToBasket(customer2.getUserId(), product.getProductId(), -10)).isNull();

		assertThat(basketService.addProductToBasket(customer3.getUserId(), product.getProductId(), 0)).isNull();

		assertThat(basketService.addProductToBasket(customer4.getUserId(), product.getProductId(), 100)).isNull();

		assertThat(basketService.addProductToBasket(customer5.getUserId(), product.getProductId(), 10)).isNotNull();

	}

	@Test
	@DirtiesContext
	void testFindByCustomerId_ShouldReturnSameNumberOfItems_AsExpectedNumber() {

		basketService.addProductToBasket(customer1.getUserId(), product1.getProductId(), 5);
		basketService.addProductToBasket(customer1.getUserId(), product2.getProductId(), 10);
		basketService.addProductToBasket(customer2.getUserId(), product2.getProductId(), 2);

		List<Basket> retrievedBasket = basketService.findByUserId(customer1.getUserId());

		assertEquals(2, retrievedBasket.size());
	}

	@Test
	@DirtiesContext
	void testAddProductToBasket_ShouldIncreaseTheQuantityOfProduct_IfProductAlreadyExists() {

		basketService.addProductToBasket(customer.getUserId(), product1.getProductId(), 5);
		basketService.addProductToBasket(customer.getUserId(), product2.getProductId(), 5);
		basketService.addProductToBasket(customer.getUserId(), product1.getProductId(), 2);

		List<Basket> retrievedBasket = basketService.findByUserId(customer.getUserId());

		assertEquals(7, retrievedBasket.get(0).getQuantity());

	}

	@Test
	@DirtiesContext
	void testRemoveProduct_ShouldReturnTrue_IfSuccessfullyRemovedProductFromBasket() {

		basketService.addProductToBasket(customer.getUserId(), product1.getProductId(), 10);
		basketService.addProductToBasket(customer.getUserId(), product2.getProductId(), 5);

		basketService.removeProductFromBasket(customer.getUserId(), product1.getProductId(), 10);

		assertEquals(-1, basketService.productInCustomerBasket(customer.getUserId(), product1.getProductId()));
		assertTrue(basketService.productInCustomerBasket(customer.getUserId(), product2.getProductId()) >= 0);

	}

	@Test
	@DirtiesContext
	void testCalculateProductPrice_ShouldReturnPriceAfterDiscountOfProducts_ExpectedResultOf61Point95() {

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		double finalPrice = basketService.calculateProductPrice(product.getProductId(), 7);

		assertEquals(61.95, finalPrice);
	}

	@Test
	@DirtiesContext
	void testCalculateProductPrice_ForProductNotFound_ShouldReturnNegative1() {

		Product product7 = new Product("Some TV", 10.5, 10, ProductType.ELECTRONICS);

		// product 7 was not added to database/doesn't exist
		double finalPrice = basketService.calculateProductPrice(product7.getProductId(), 7);

		assertEquals(-1, finalPrice);

	}

	@Test
	@DirtiesContext
	void testCalculateProductPrice_ForProductUnitPriceLessThan0_ShouldReturnNegative1() {

		Product product3 = new Product("LG TV", 30.5, 60, ProductType.ELECTRONICS);
		product3.setAdmin(admin);
		// unit price is set to -40 which is less than 0
		product3.setUnitPrice(-40);
		product3.setRemainingQuantity(20);
		productService.addNewProduct(product3);

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product3, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product3, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		double finalPrice = basketService.calculateProductPrice(product3.getProductId(), 7);

		assertEquals(-1, finalPrice);

	}

	@Test
	@DirtiesContext
	void testCalculateProductPrice_ForQuantityLessThan0_ShouldReturnNegative1() {

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		// quantity attribute is set to -7 which is less than 0
		double finalPrice = basketService.calculateProductPrice(product.getProductId(), -7);

		assertEquals(-1, finalPrice);

	}

	@Test
	@DirtiesContext
	void testCalculateProductPrice_ForProductWithoutDiscount_ShouldReturn73Point5() {

		double finalPrice = basketService.calculateProductPrice(product.getProductId(), 7);

		assertEquals(73.5, finalPrice);

	}

	@Test
	@DirtiesContext
	void testTotalCostInBasket_ForOneTypeOfProductWithoutDiscount_ShouldReturn52Point50() {

		basketService.addProductToBasket(customer.getUserId(), product.getProductId(), 5);

		// quantity of 7
		double totalPrice = basketService.totalCostInBasket(customer.getUserId());

		assertEquals(52.50, totalPrice);

	}

	@Test
	@DirtiesContext
	void testTotalCostInBasket_ForOneTypeOfProductWithOneDiscount_ShouldReturn69Point30() {

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);

		basketService.addProductToBasket(customer.getUserId(), product.getProductId(), 7);

		// quantity of 7
		double totalPrice = basketService.totalCostInBasket(customer.getUserId());

		assertEquals(69.30, totalPrice);

	}

	@Test
	@DirtiesContext
	void testTotalCostInBasket_ForOneTypeOfProductWithTwoDiscounts_ShouldReturn61Point95() {

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		basketService.addProductToBasket(customer.getUserId(), product.getProductId(), 7);

		// quantity of 7
		double totalPrice = basketService.totalCostInBasket(customer.getUserId());

		assertEquals(61.95, totalPrice);

	}

	@Test
	@DirtiesContext
	void testTotalCostInBasket_ForTwoTypeOfProductWithoutDiscount_ShouldReturn68Point5() {

		basketService.addProductToBasket(customer.getUserId(), product1.getProductId(), 5);
		basketService.addProductToBasket(customer.getUserId(), product2.getProductId(), 2);

		// quantity of 7
		double totalPrice = basketService.totalCostInBasket(customer.getUserId());

		assertEquals(68.5, totalPrice);

	}

	@Test
	@DirtiesContext
	void testTotalCostInBasket_ForTwoTypeOfProductWithOneHavingTwoDiscounts_ShouldReturn73Point45() {

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product1, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product1, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		basketService.addProductToBasket(customer.getUserId(), product1.getProductId(), 7);
		basketService.addProductToBasket(customer.getUserId(), product2.getProductId(), 2);

		// quantity of 7
		double totalPrice = basketService.totalCostInBasket(customer.getUserId());

		assertEquals(73.45, totalPrice);

	}

	@Test
	@DirtiesContext
	void testTotalCostInBasket_ForTwoTypeOfProductWithBothHavingTwoDiscounts_ShouldReturn134Point95() {

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product1, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product1, discount2);
		ProductDiscount productDiscount3 = new ProductDiscount(product2, discount1);
		ProductDiscount productDiscount4 = new ProductDiscount(product2, discount2);

		// add the productDiscounts to database
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);
		productDiscountService.addProductDiscount(productDiscount3);
		productDiscountService.addProductDiscount(productDiscount4);

		basketService.addProductToBasket(customer.getUserId(), product1.getProductId(), 7);
		basketService.addProductToBasket(customer.getUserId(), product2.getProductId(), 6);

		// quantity of 7
		double totalPrice = basketService.totalCostInBasket(customer.getUserId());

		assertEquals(134.95, totalPrice);

	}

}
