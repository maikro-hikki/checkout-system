package com.maikro.checkoutSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AdminProduct {
	
	@Id
	@GeneratedValue
	private long adminProductId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminId")
	private Admin admin;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
	private Product product;

	public AdminProduct() {
	}

	public AdminProduct(long adminProductId, Admin admin, Product product) {
		super();
		this.adminProductId = adminProductId;
		this.admin = admin;
		this.product = product;
	}

	public long getAdminProductId() {
		return adminProductId;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
