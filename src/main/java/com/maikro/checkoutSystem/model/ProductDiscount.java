package com.maikro.checkoutSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductDiscount {
	
	@Id
	@GeneratedValue
	private long productDiscountId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discountId")
	private Discount discount;

	public ProductDiscount() {
	}

	public ProductDiscount(long productDiscountId, Product product, Discount discount) {
		super();
		this.productDiscountId = productDiscountId;
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
