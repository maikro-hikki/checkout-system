package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.DiscountType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public abstract class Discount {
	
	@Id
	@GeneratedValue
	private long discountId;
	
	private DiscountType discountType;
	
	@ManyToOne
	@JoinColumn(name = "fk_adminId")
	private Admin admin;
	
	@OneToMany(mappedBy = "discount")
    private List<ProductDiscount> productDiscount;

	public Discount() {
	}

	public Discount(long discountId, DiscountType discountType, Admin admin, List<ProductDiscount> productDiscount) {
		super();
		this.discountId = discountId;
		this.discountType = discountType;
		this.admin = admin;
		this.productDiscount = productDiscount;
	}

	public long getDiscountId() {
		return discountId;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<ProductDiscount> getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(List<ProductDiscount> productDiscount) {
		this.productDiscount = productDiscount;
	}
	
}
