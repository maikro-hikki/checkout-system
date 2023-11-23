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

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	public void addNewProduct(Product product) {
		productRepo.save(product);
	}

	public void removeProductById(long productId) {
		productRepo.deleteById(productId);
	}

	public boolean productExist(long productId) {
		return productRepo.findById(productId).isPresent();
	}

	public Optional<Product> findByProductId(long productId) {
		return productRepo.findById(productId);
	}

	public double getProductUnitPrice(long productId) {

		if (productExist(productId)) {
			return findByProductId(productId).get().getUnitPrice();
		}

		return -1;
	}

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

	public List<Product> findAllProductsSorted(String field) {

		return productRepo.findAll(Sort.by(Sort.Direction.ASC, field));
	}

}
