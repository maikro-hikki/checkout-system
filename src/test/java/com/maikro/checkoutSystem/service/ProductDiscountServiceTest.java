package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Discount;
import com.maikro.checkoutSystem.model.DiscountByProduct;
import com.maikro.checkoutSystem.model.DiscountByQuantity;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.model.ProductDiscount;
import com.maikro.checkoutSystem.repository.ProductDiscountRepo;

@SpringBootTest
class ProductDiscountServiceTest {

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
	@DirtiesContext
	void testAddProductDiscountByProductAndDiscount_ShouldAddProductDiscountInDatabaseByProductIdAndDiscountId_Return1ForTheTest() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount = new DiscountByQuantity();
		discount.setAdmin(admin);
		discountService.addDiscountByQuantity(discount);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);
		productService.addNewProduct(product);

		// add discount to product
		productDiscountService.addProductDiscountByProductAndDiscount(product.getProductId(), discount.getDiscountId());

		// Use productDiscountRepo to look for the added product discount
		List<ProductDiscount> productDiscount = productDiscountRepo.findByProductProductId(product.getProductId());

		assertThat(productDiscount).isNotNull();
		assertEquals(1, productDiscount.size());
	}

	@Test
	@DirtiesContext
	void testAddProductDiscountByProductAndDiscount_ShouldNotAddProductDiscountInDatabaseIfDiscountAlreadyAppliedToProduct_OnlyHave1DiscountForProduct() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount = new DiscountByQuantity();
		discount.setAdmin(admin);
		discountService.addDiscountByQuantity(discount);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);
		productService.addNewProduct(product);

		// add discount to product, should return true as discount was not applied to
		// product yet
		assertTrue(productDiscountService.addProductDiscountByProductAndDiscount(product.getProductId(),
				discount.getDiscountId()));
		// discount already applied to product and should not be added to database,
		// should return false
		assertFalse(productDiscountService.addProductDiscountByProductAndDiscount(product.getProductId(),
				discount.getDiscountId()));

		// Use productDiscountRepo to look for the added product discount
		List<ProductDiscount> productDiscount = productDiscountRepo.findByProductProductId(product.getProductId());

		assertThat(productDiscount).isNotNull();
		// size of the list should be 1 as only one discount got added
		assertEquals(1, productDiscount.size());
	}

	@Test
	@DirtiesContext
	void testDiscountAlreadyAppliedToProduct_ForDiscountAlreadyAppliedToProduct_ShouldReturnTrue() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount = new DiscountByQuantity();
		discount.setAdmin(admin);
		discountService.addDiscountByQuantity(discount);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);
		productService.addNewProduct(product);

		// add discount to product
		productDiscountService.addProductDiscountByProductAndDiscount(product.getProductId(), discount.getDiscountId());

		assertTrue(productDiscountService.discountAlreadyAppliedToProduct(product.getProductId(),
				discount.getDiscountId()));
	}

	@Test
	@DirtiesContext
	void testDiscountAlreadyAppliedToProduct_ShouldReturnFalse_IfDiscountNotYetAppliedToProduct() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discountService.addDiscountByQuantity(discount1);
		// create a product discount and set it under the admin
		DiscountByProduct discount2 = new DiscountByProduct();
		discount2.setAdmin(admin);
		discountService.addDiscountByProduct(discount2);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);
		productService.addNewProduct(product);

		// add discount to product
		productDiscountService.addProductDiscountByProductAndDiscount(product.getProductId(),
				discount1.getDiscountId());

		assertFalse(productDiscountService.discountAlreadyAppliedToProduct(product.getProductId(),
				discount2.getDiscountId()));
	}

	@Test
	@DirtiesContext
	void testAddProductDiscount_ShouldAddProductDiscountInDatabaseByProductDiscountObject_ReturnTrueForValidInput() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount = new DiscountByQuantity();
		discount.setAdmin(admin);
		discountService.addDiscountByQuantity(discount);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);
		productService.addNewProduct(product);

		// create new product discount object
		ProductDiscount productDiscount = new ProductDiscount(product, discount);

		// add product discount object into the database, should return true
		assertTrue(productDiscountService.addProductDiscount(productDiscount));

		// Use productDiscountRepo to look for the added product discount
		ProductDiscount savedProductDiscount = productDiscountRepo.findById(productDiscount.getProductDiscountId())
				.orElse(null);

		assertThat(savedProductDiscount).isNotNull();
		assertThat(productDiscount.getProductDiscountId()).isEqualTo(savedProductDiscount.getProductDiscountId());
	}

	@Test
	@DirtiesContext
	void testAddProductDiscount_ShouldNotAddProductDiscountInDatabaseIfProductOrDiscountNotInDatabase_ReturnFalseForInvalidInput() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount = new DiscountByQuantity();
		discount.setAdmin(admin);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);

		// create new product discount object
		ProductDiscount productDiscount = new ProductDiscount(product, discount);

		// product and discount were not added to database so invalid input, should
		// return false
		assertFalse(productDiscountService.addProductDiscount(productDiscount));

		// Use productDiscountRepo to look for the added product discount
		ProductDiscount savedProductDiscount = productDiscountRepo.findById(productDiscount.getProductDiscountId())
				.orElse(null);

		// should be null as the product discount was not added
		assertThat(savedProductDiscount).isNull();
	}

	@Test
	@DirtiesContext
	void testAddProductDiscount_ShouldNotAddProductDiscountInDatabaseIfDiscountAlreadyAppliedToProduct_ReturnFalse() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discountService.addDiscountByQuantity(discount1);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);
		productService.addNewProduct(product);

		// create new product discount object
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		// create another product discount object with same discount to product
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount1);

		// add productdiscount into the database, should return true since the discount
		// was not added to product yet
		assertTrue(productDiscountService.addProductDiscount(productDiscount1));
		// try to add productdiscount2 into the database, should return false as the
		// discount was already added to product
		assertFalse(productDiscountService.addProductDiscount(productDiscount2));

		// Use productDiscountRepo to look for the failed to add productdiscount2
		ProductDiscount savedProductDiscount = productDiscountRepo.findById(productDiscount2.getProductDiscountId())
				.orElse(null);
		// should be null as productdiscount2 was not added
		assertThat(savedProductDiscount).isNull();
	}

	@Test
	@DirtiesContext
	void testFindByProductIdAndDiscountId_ShouldReturnProductDiscountObjectWithProductIdAndDiscountId_CreatedProductDiscountShouldMatchRetrieved() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discountService.addDiscountByQuantity(discount1);
		// create product discount and set it under the admin
		DiscountByProduct discount2 = new DiscountByProduct();
		discount2.setAdmin(admin);
		discountService.addDiscountByProduct(discount2);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);
		productService.addNewProduct(product);

		// create new product discount object
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		// create another product discount object with same discount to product
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the discounts
		productDiscountService.addProductDiscount(productDiscount1);
		productDiscountService.addProductDiscount(productDiscount2);

		// get productDiscount1
		ProductDiscount retrievedProductDiscount1 = productDiscountService
				.findByProductIdAndDiscountId(product.getProductId(), discount1.getDiscountId());
		// get productDiscount2
		ProductDiscount retrievedProductDiscount2 = productDiscountService
				.findByProductIdAndDiscountId(product.getProductId(), discount2.getDiscountId());

		// Ids of productDiscount1 attributes should match
		assertEquals(productDiscount1.getProductDiscountId(), retrievedProductDiscount1.getProductDiscountId());
		assertEquals(productDiscount1.getProduct().getProductId(),
				retrievedProductDiscount1.getProduct().getProductId());
		assertEquals(productDiscount1.getDiscount().getDiscountId(),
				retrievedProductDiscount1.getDiscount().getDiscountId());
		// Ids of productDiscount2 attributes should match
		assertEquals(productDiscount2.getProductDiscountId(), retrievedProductDiscount2.getProductDiscountId());
		assertEquals(productDiscount2.getProduct().getProductId(),
				retrievedProductDiscount2.getProduct().getProductId());
		assertEquals(productDiscount2.getDiscount().getDiscountId(),
				retrievedProductDiscount2.getDiscount().getDiscountId());
	}

	@Test
	@DirtiesContext
	void testRemoveProductDiscountByProductIdAndDiscountId_ShouldRemoveProductDiscount_ReturnTrueIfProductDiscountExist() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount = new DiscountByQuantity();
		discount.setAdmin(admin);
		discountService.addDiscountByQuantity(discount);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);
		productService.addNewProduct(product);

		// create new product discount object
		ProductDiscount productDiscount = new ProductDiscount(product, discount);

		// add the productDiscount to database
		productDiscountService.addProductDiscount(productDiscount);

		assertTrue(productDiscountService.removeProductDiscountByProductIdAndDiscountId(product.getProductId(),
				discount.getDiscountId()));
		// Use productDiscountRepo to look for the failed to add product discount
		ProductDiscount retrievedProductDiscount = productDiscountRepo.findById(productDiscount.getProductDiscountId())
				.orElse(null);
		// should be null as product discount was removed
		assertThat(retrievedProductDiscount).isNull();

	}

	@Test
	@DirtiesContext
	void testRemoveProductDiscountByProductIdAndDiscountId_ShouldReturnFalse_IfProductDiscountIsNotInDatabase() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discountService.addDiscountByQuantity(discount1);
		// create a product discount and set it under the admin
		DiscountByProduct discount2 = new DiscountByProduct();
		discount2.setAdmin(admin);
		discountService.addDiscountByProduct(discount2);

		// create a product and set it under the admin
		Product product1 = new Product();
		product1.setAdmin(admin);
		productService.addNewProduct(product1);
		// create a product and set it under the admin
		Product product2 = new Product();
		product2.setAdmin(admin);
		productService.addNewProduct(product2);

		// create new product discount object
		ProductDiscount productDiscount = new ProductDiscount(product1, discount1);

		// add the productDiscount to database
		productDiscountService.addProductDiscount(productDiscount);

		// should return true since discount1 was added to product1
		assertTrue(productDiscountService.removeProductDiscountByProductIdAndDiscountId(product1.getProductId(),
				discount1.getDiscountId()));

		// both should return false as the discounts were not added to the respective
		// products
		assertFalse(productDiscountService.removeProductDiscountByProductIdAndDiscountId(product1.getProductId(),
				discount2.getDiscountId()));
		assertFalse(productDiscountService.removeProductDiscountByProductIdAndDiscountId(product2.getProductId(),
				discount1.getDiscountId()));

	}

	@Test
	@DirtiesContext
	void testFindDiscountsByProductId_ShouldReturnDiscountsOnProduct() {

		// create an admin to make a discount
		Admin admin = new Admin();
		userClassService.addAdminUser(admin);

		// create a quantity discount and set it under the admin
		DiscountByQuantity discount1 = new DiscountByQuantity();
		discount1.setAdmin(admin);
		discountService.addDiscountByQuantity(discount1);
		// create a product discount and set it under the admin
		DiscountByProduct discount2 = new DiscountByProduct();
		discount2.setAdmin(admin);
		discountService.addDiscountByProduct(discount2);

		// create a product and set it under the admin
		Product product = new Product();
		product.setAdmin(admin);
		productService.addNewProduct(product);

		// create new product discount objects
		ProductDiscount productDiscount1 = new ProductDiscount(product, discount1);
		ProductDiscount productDiscount2 = new ProductDiscount(product, discount2);

		// add the productDiscount to database
		productDiscountService.addProductDiscount(productDiscount1);		
		productDiscountService.addProductDiscount(productDiscount2);
		
		//retrieve all discounts on a product
		List<Discount> discounts = productDiscountService.findDiscountsByProductId(product.getProductId());
		
		//discountIds should match in the returned list
		assertEquals(discount1.getDiscountId(), discounts.get(0).getDiscountId());
		assertEquals(discount2.getDiscountId(), discounts.get(1).getDiscountId());

	}

}
