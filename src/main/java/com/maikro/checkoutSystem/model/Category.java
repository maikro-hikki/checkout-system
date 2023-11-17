package com.maikro.checkoutSystem.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
	
	@Id
	@GeneratedValue
	private long categoryId;
	
	private String name;
	
	private String description;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<ProductCategory> productCategory;

	public Category() {
	}

	public Category(long categoryId, String name, String description, List<ProductCategory> productCategory) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.productCategory = productCategory;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProductCategory> getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(List<ProductCategory> productCategory) {
		this.productCategory = productCategory;
	}

}
