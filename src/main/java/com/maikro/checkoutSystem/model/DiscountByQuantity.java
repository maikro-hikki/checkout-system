package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.DiscountType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "discountId")
public class DiscountByQuantity extends Discount{
	
	private int quantity;
	
	private double discount;

	public DiscountByQuantity() {
		super();
	}

	public DiscountByQuantity(long discountId, DiscountType discountType, Admin admin,
			List<ProductDiscount> productDiscount) {
		super(discountId, discountType, admin, productDiscount);
	}

	public DiscountByQuantity(int quantity, double discount) {
		super();
		this.quantity = quantity;
		this.discount = discount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

}
