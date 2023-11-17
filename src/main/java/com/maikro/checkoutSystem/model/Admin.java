package com.maikro.checkoutSystem.model;

import java.util.List;

import com.maikro.checkoutSystem.constants.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends UserClass {
	
	//products added by the admin user
	@OneToMany(mappedBy = "admin")
	private List<AdminProduct> adminProduct;
	
	//discounts added by the admin user
	@OneToMany(mappedBy = "admin")
	private List<Discount> discount;

	public Admin() {
		super();
	}

	public Admin(long userId, String username, String password, UserType userType) {
		super(userId, username, password, userType);
	}

	public Admin(List<AdminProduct> adminProduct, List<Discount> discount) {
		super();
		this.adminProduct = adminProduct;
		this.discount = discount;
	}

	public List<AdminProduct> getAdminProduct() {
		return adminProduct;
	}

	public void setAdminProduct(List<AdminProduct> adminProduct) {
		this.adminProduct = adminProduct;
	}

	public List<Discount> getDiscount() {
		return discount;
	}

	public void setDiscount(List<Discount> discount) {
		this.discount = discount;
	}

}
