package com.maikro.checkoutSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.maikro.checkoutSystem.constants.ProductType;
import com.maikro.checkoutSystem.model.Product;
import com.maikro.checkoutSystem.repository.ProductRepo;

/**
 * Service class for managing products.
 */
@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	/**
	 * Adds a new product.
	 *
	 * @param product the product to be added
	 */
	public void addNewProduct(Product product) {
		productRepo.save(product);
	}

	/**
	 * Removes a product by its ID.
	 *
	 * @param productId the ID of the product to be removed
	 */
	public void removeProductById(long productId) {
		productRepo.deleteById(productId);
	}

	/**
	 * Checks if a product with the given ID exists.
	 *
	 * @param productId the ID of the product
	 * @return true if the product exists, false otherwise
	 */
	public boolean productExist(long productId) {
		return productRepo.findById(productId).isPresent();
	}

	/**
	 * Retrieves a product by its ID.
	 *
	 * @param productId the ID of the product
	 * @return an Optional containing the Product if found, or an empty Optional if not found
	 */
	public Optional<Product> findByProductId(long productId) {
		return productRepo.findById(productId);
	}

	/**
	 * Retrieves the unit price of a product by its ID.
	 *
	 * @param productId the ID of the product
	 * @return the unit price of the product, or -1 if the product is not found
	 */
	public double getProductUnitPrice(long productId) {

		if (productExist(productId)) {
			return findByProductId(productId).get().getUnitPrice();
		}

		return -1;
	}

	/**
	 * Converts a string representation of a product type to the corresponding enum value.
	 *
	 * @param productType the string representation of the product type
	 * @return the corresponding ProductType enum value
	 */
	public ProductType stringToProductType(String productType) {

		switch (productType) {
		case "Computer":
			return ProductType.COMPUTER;
		case "Cooling":
			return ProductType.COOLING;
		case "Fridge":
			return ProductType.FRIDGE;
		case "TV":
			return ProductType.TV;
		default:
			return ProductType.ELECTRONICS;
		}
	}

	/**
	 * Retrieves all products with pagination.
	 *
	 * @param offset   the offset of the page
	 * @param pageSize the maximum number of products to retrieve per page
	 * @return a Page containing the products
	 */
	public Page<Product> findAllProductWithPagination(int offset, int pageSize) {

		// added
		Pageable pageable = PageRequest.of(offset, pageSize);
		Page<Product> products = productRepo.findAll(pageable);

		// Check if the requested page is out of bounds
		if (offset >= products.getTotalPages()) {
			// Adjust the offset to the last page if necessary
			pageable = PageRequest.of(products.getTotalPages() - 1, pageSize);
			products = productRepo.findAll(pageable);
		}

		return products;
	}

	/**
	 * Retrieves all products sorted by the specified field in ascending order.
	 *
	 * @param field the field to sort the products by
	 * @return a List containing the sorted products
	 */
	public List<Product> findAllProductsSorted(String field) {

		return productRepo.findAll(Sort.by(Sort.Direction.ASC, field));
	}

}
