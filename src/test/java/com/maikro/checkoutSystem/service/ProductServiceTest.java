package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

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
    
    @Test
    @DirtiesContext
    public void testAddNewProduct() {
    	
    	//create an admin to make a product
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);
        
        //add the created product into the repository
        productService.addNewProduct(product);
        
        //use the productRepo.findById method to find product in repo
        Product savedProduct = productRepo.findById(product.getProductId()).orElse(null);
        
        //check that the product exists in the repo or not
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProductId()).isEqualTo(product.getProductId());
        
    }
    
    @Test
    @DirtiesContext
    public void testRemoveProductById() {
    	
    	//create an admin to make a product
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);
        
        //add the created product into the repository
        productService.addNewProduct(product);
        
        //call the method to remove the product
        productService.removeProductById(product.getProductId());
        
        //verify that the product is no longer in the repository
        assertThat(productRepo.findById(product.getProductId())).isEmpty();
        
    }
    
    @Test
    @DirtiesContext
    public void testDoesProductExistById_For_ExistingProduct() {
    	
    	//create an admin to make a product
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);
        
        //add the created product into the repository
        productService.addNewProduct(product);
        
        //check if the method can find the existing product
        assertTrue(productService.productExist(product.getProductId()));
    }
    
    @Test
    public void testDoesProductExistById_For_NonExistingProduct() {
    	
        //check if the method cannot find the non-existing product
        assertFalse(productService.productExist(123456789));
    }
    
    @Test
    @DirtiesContext
    public void testDoesProductExistById_For_RemovedProduct() {
    	
    	//create an admin to make a product
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);
        
        //add the created product into the repository
        productService.addNewProduct(product);
        //remove the product
        productService.removeProductById(product.getProductId());
        
        //check if the method cannot find the removed product
        assertFalse(productService.productExist(product.getProductId()));
    }
    
    @Test
    @DirtiesContext
    public void testFindByProductId_ShouldReturnSameProduct_ForCalledProductId() {
    	
    	//create an admin to make a product
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);
        
        //add the created product into the repository
        productService.addNewProduct(product);
        //find the product by productId
        Product foundProduct = productService.findByProductId(product.getProductId()).get();
        
        //check if the found productId is the same as the created product's Id
        assertEquals(product.getProductId(), foundProduct.getProductId());
    }
    
    @Test
    @DirtiesContext
    public void testGetProductUnitPrice_ShouldReturnProductUnitPrice_IfProductExist() {
    	
    	//create an admin to make a product
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);
        product.setUnitPrice(10.07);
        
        //add the created product into the repository
        productService.addNewProduct(product);
        //get unit price of the product by productId
        double unitPrice = productService.getProductUnitPrice(product.getProductId());
        
        //check if the unit price is the same as the expected unit price
        assertEquals(10.07, unitPrice);
    }
    
    @Test
    @DirtiesContext
    public void testGetProductUnitPrice_ShouldReturnNegative1_IfProductDoesntExist() {
    	
    	//create an admin to make a product
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	//create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);
        product.setUnitPrice(10.07);
        
        //find the product by productId which was not added to the database
        double unitPrice = productService.getProductUnitPrice(product.getProductId());
        
        //check if the output is -1 since product is not in the database
        assertEquals(-1, unitPrice);
    }
    
}
