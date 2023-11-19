package com.maikro.checkoutSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductDiscount {
	
	@Id
	@GeneratedValue
	private long productDiscountId;
	
	@ManyToOne
    @JoinColumn(name = "productId")
	private Product product;
	
	@ManyToOne
    @JoinColumn(name = "discountId")
	private Discount discount;

	public ProductDiscount() {
	}

	public ProductDiscount(Product product, Discount discount) {
		this.product = product;
		this.discount = discount;
	}

	public long getProductDiscountId() {
		return productDiscountId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

}
