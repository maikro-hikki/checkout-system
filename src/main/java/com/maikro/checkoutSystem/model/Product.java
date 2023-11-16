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
	
	//quantity of product
	private int quantity;
	
	//type of product
	private ProductType productType;
	
	//discounts set on the product
	@OneToMany(mappedBy = "product")
	private List<Discount> discounts;
	
	@ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
	
	@ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

	public Product() {
	}

	public Product(long productId, ProductType productType, List<Discount> discounts) {
		this.productId = productId;
		this.productType = productType;
		this.discounts = discounts;
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

	public void setUnitPrice(double price) {
		this.unitPrice = price;
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

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}	

}
