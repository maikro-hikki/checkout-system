package com.maikro.checkoutSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Discount {
	
	@Id
	@GeneratedValue
	private long discountId;
	
	private long discountAmount;
	
	@ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
	
	@ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

	public Discount() {
	}

	public Discount(long discountId, long discountAmount) {
		this.discountId = discountId;
		this.discountAmount = discountAmount;
	}

	public long getDiscountId() {
		return discountId;
	}

	public long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(long discountAmount) {
		this.discountAmount = discountAmount;
	}
	
}
