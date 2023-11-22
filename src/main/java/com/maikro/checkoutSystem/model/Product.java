package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.ProductType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {
	
	@Id
	@GeneratedValue
	private long productId;
	
	//name of product
	@NotBlank
	private String name;
	
	//price of one unit
	@NotNull
	private double unitPrice;
	
	//quantity of product remaining
	@NotNull
	private int remainingQuantity;
	
	//type of product
	private ProductType productType;
	
	@OneToMany(fetch = FetchType.EAGER)
    private List<ProductDiscount> productDiscount;
	
	@ManyToOne(fetch = FetchType.EAGER)
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
	
	public Product(String name, double unitPrice, int remainingQuantity, ProductType productType) {
		this.name = name;
		this.unitPrice = unitPrice;
		this.remainingQuantity = remainingQuantity;
		this.productType = productType;
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
