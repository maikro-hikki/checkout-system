package com.maikro.checkoutSystem.model;

import com.maikro.checkoutSystem.constants.DiscountType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "discountId")
public class DiscountByProduct extends Discount{
	
	private double discount;

	public DiscountByProduct() {
		super();
		this.setDiscountType(DiscountType.INDIVIDUAL_PRODUCT);
	}

	public DiscountByProduct(Admin admin, long discount) {
		super(admin);
		this.discount = discount;
		this.setDiscountType(DiscountType.INDIVIDUAL_PRODUCT);
	}

	public DiscountByProduct(double discount) {
		super();
		this.discount = discount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

}
