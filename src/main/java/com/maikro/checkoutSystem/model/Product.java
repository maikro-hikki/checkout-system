package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.ProductType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
	
	@Id
	@GeneratedValue
	private long productId;
	
	//name of product
	private String name;
	
	//price of one unit
	private double unitPrice;
	
	//quantity of product
	private int quantity;
	
	//type of product
	private ProductType productType;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<Inventory> inventory;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductCategory> productCategory;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductDiscount> productDiscount;
	
	@OneToMany(mappedBy = "product")
	private List<AdminProduct> adminProduct;

	public Product() {
	}

	public Product(long productId, String name, double unitPrice, int quantity, ProductType productType,
			List<Inventory> inventory, List<ProductCategory> productCategory, List<ProductDiscount> productDiscount,
			List<AdminProduct> adminProduct) {
		super();
		this.productId = productId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.productType = productType;
		this.inventory = inventory;
		this.productCategory = productCategory;
		this.productDiscount = productDiscount;
		this.adminProduct = adminProduct;
	}

	public long getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public List<Inventory> getInventory() {
		return inventory;
	}

	public void setInventory(List<Inventory> inventory) {
		this.inventory = inventory;
	}

	public List<ProductCategory> getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(List<ProductCategory> productCategory) {
		this.productCategory = productCategory;
	}

	public List<ProductDiscount> getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(List<ProductDiscount> productDiscount) {
		this.productDiscount = productDiscount;
	}

	public List<AdminProduct> getAdminProduct() {
		return adminProduct;
	}

	public void setAdminProduct(List<AdminProduct> adminProduct) {
		this.adminProduct = adminProduct;
	}

}
