package com.maikro.checkoutSystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testAddNewProduct() {
    	
    	//create an admin to make a product
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	// create a product and set it under the admin
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
    public void testRemoveProductById() {
    	
    	//create an admin to make a product
    	Admin admin = new Admin();
    	userClassService.addAdminUser(admin);
    	// create a product and set it under the admin
        Product product = new Product();
        product.setAdmin(admin);
        
        //add the created product into the repository
        productService.addNewProduct(product);
        
        //call the method to remove the product
        productService.removeProductById(product.getProductId());
        
        //verify that the product is no longer in the repository
        assertThat(productRepo.findById(product.getProductId())).isEmpty();
        
    }
    
}
