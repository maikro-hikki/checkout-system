package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.maikro.checkoutSystem.constants.ProductType;
import com.maikro.checkoutSystem.model.Admin;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.repository.ProductRepo;

@SpringBootTest
public class ProductServiceTest {
	
	@Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserClassService userClassService;
    
    Admin admin = new Admin("admin", "123", "John", "Doe");
	Product product = new Product("Apple TV", 10.5, 50, ProductType.ELECTRONICS);
	Product product1 = new Product("Apple TV", 5.5, 30, ProductType.ELECTRONICS);
	Product product2 = new Product("Samsung TV", 20.5, 70, ProductType.ELECTRONICS);

	@BeforeEach
	void setUp() {

		userClassService.addAdminUser(admin);
		product.setAdmin(admin);
		product1.setAdmin(admin);
		product2.setAdmin(admin);
		productService.addNewProduct(product);
		productService.addNewProduct(product1);
		productService.addNewProduct(product2);

	}
    
    @Test
    @DirtiesContext
    public void testAddNewProduct() {
        
        //use the productRepo.findById method to find product in repo
        Product savedProduct = productRepo.findById(product.getProductId()).orElse(null);
        
        //check that the product exists in the repo or not
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProductId()).isEqualTo(product.getProductId());
        
    }
    
//    @Test
//    @DirtiesContext
//    public void testRemoveProductById() {
//        
//        //call the method to remove the product
//        productService.removeProductById(product.getProductId());
//        
//        //verify that the product is no longer in the repository
//        assertThat(productRepo.findById(product.getProductId())).isEmpty();
//        
//    }
//    
//    @Test
//    @DirtiesContext
//    public void testDoesProductExistById_For_ExistingProduct() {
//        
//        //check if the method can find the existing product
//        assertTrue(productService.productExist(product.getProductId()));
//    }
//    
//    @Test
//    public void testDoesProductExistById_For_NonExistingProduct() {
//    	
//    	//not saved to database
//    	Product product7 = new Product("Some TV", 10.5, 10, ProductType.ELECTRONICS);
//    	
//        //check if the method cannot find the non-existing product
//        assertFalse(productService.productExist(product7.getProductId()));
//    }
//    
//    @Test
//    @DirtiesContext
//    public void testDoesProductExistById_For_RemovedProduct() {
//
//    	//remove the product
//        productService.removeProductById(product.getProductId());
//        
//        //check if the method cannot find the removed product
//        assertFalse(productService.productExist(product.getProductId()));
//    }
//    
//    @Test
//    @DirtiesContext
//    public void testFindByProductId_ShouldReturnSameProduct_ForCalledProductId() {
//    	
//        //find the product by productId
//        Product foundProduct = productService.findByProductId(product.getProductId()).get();
//        
//        //check if the found productId is the same as the created product's Id
//        assertEquals(product.getProductId(), foundProduct.getProductId());
//    }
//    
//    @Test
//    @DirtiesContext
//    public void testGetProductUnitPrice_ShouldReturnProductUnitPrice_IfProductExist() {
//    	
//        //get unit price of the product by productId
//        double unitPrice = productService.getProductUnitPrice(product.getProductId());
//        
//        //check if the unit price is the same as the expected unit price
//        assertEquals(10.5, unitPrice);
//    }
//    
//    @Test
//    @DirtiesContext
//    public void testGetProductUnitPrice_ShouldReturnNegative1_IfProductDoesntExist() {
//    	
//    	// not added to database/does not exist
//    			Product product7 = new Product("Some TV", 10.5, 10, ProductType.ELECTRONICS);
//        
//        //find the product by productId which was not added to the database
//        double unitPrice = productService.getProductUnitPrice(product7.getProductId());
//        
//        //check if the output is -1 since product is not in the database
//        assertEquals(-1, unitPrice);
//    }
    
}
