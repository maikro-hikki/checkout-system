package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.DiscountType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Discount {
	
	@Id
	@GeneratedValue
	private long discountId;
	
	@Enumerated(EnumType.STRING)
	private DiscountType discountType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_adminId")
	private Admin admin;
	
	@OneToMany(mappedBy = "discount", fetch = FetchType.EAGER)
    private List<ProductDiscount> productDiscount;

	public Discount() {
	}
	
	public Discount(DiscountType discountType) {
		this.discountType = discountType;
	}

	public Discount(Admin admin) {
		this.admin = admin;
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
