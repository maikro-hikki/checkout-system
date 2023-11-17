package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.DiscountType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "discountId")
public class DiscountByProduct extends Discount{
	
	private double discount;

	public DiscountByProduct() {
		super();
	}

	public DiscountByProduct(long discountId, DiscountType discountType, Admin admin,
			List<ProductDiscount> productDiscount) {
		super(discountId, discountType, admin, productDiscount);
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
