package com.maikro.checkoutSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductCategory {
	
	@Id
	@GeneratedValue
	private long productCategoryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
	private Category category;

	public ProductCategory() {
	}

	public ProductCategory(long productCategoryId, Product product, Category category) {
		super();
		this.productCategoryId = productCategoryId;
		this.product = product;
		this.category = category;
	}

	public long getProductCategoryId() {
		return productCategoryId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
