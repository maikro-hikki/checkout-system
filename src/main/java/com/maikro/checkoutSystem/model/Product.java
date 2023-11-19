package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.ProductType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	//quantity of product remaining
	private int remainingQuantity;
	
	//type of product
	private ProductType productType;
	
	@OneToMany
    private List<ProductDiscount> productDiscount;
	
	@ManyToOne
    @JoinColumn(name = "fk_adminId")
	private Admin admin;

	public Product() {
	}

	public Product(String name, double unitPrice, int remainingQuantity, ProductType productType,
			List<ProductDiscount> productDiscount, Admin admin) {
		this.name = name;
		this.unitPrice = unitPrice;
		this.remainingQuantity = remainingQuantity;
		this.productType = productType;
		this.productDiscount = productDiscount;
		this.admin = admin;
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

	public int getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(int remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public List<ProductDiscount> getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(List<ProductDiscount> productDiscount) {
		this.productDiscount = productDiscount;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
