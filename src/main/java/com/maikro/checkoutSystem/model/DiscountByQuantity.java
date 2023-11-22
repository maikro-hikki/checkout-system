package com.maikro.checkoutSystem.model;

import com.maikro.checkoutSystem.constants.DiscountType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "discountId")
public class DiscountByQuantity extends Discount{
	
	@NotNull
	private int quantity;
	
	@NotNull
	private double discount;

	public DiscountByQuantity() {
		super();
		this.setDiscountType(DiscountType.QUANTITY);
	}

	public DiscountByQuantity(int quantity, double discount, Admin admin) {
		super(admin);
		this.quantity = quantity;
		this.discount = discount;
		this.setDiscountType(DiscountType.QUANTITY);
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
