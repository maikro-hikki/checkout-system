//package com.maikro.checkoutSystem.model;
//
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//
//public class AdminDiscount {
//	
//	@Id
//	@GeneratedValue
//	private long adminDiscountId;
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//	private Admin admin;
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "discountId")
//	private Discount discount;
//
//	public AdminDiscount() {
//	}
//
//	public AdminDiscount(long adminDiscountId, Admin admin, Discount discount) {
//		super();
//		this.adminDiscountId = adminDiscountId;
//		this.admin = admin;
//		this.discount = discount;
//	}
//
//	public long getAdminDiscountId() {
//		return adminDiscountId;
//	}
//
//	public Admin getAdmin() {
//		return admin;
//	}
//
//	public void setAdmin(Admin admin) {
//		this.admin = admin;
//	}
//
//	public Discount getDiscount() {
//		return discount;
//	}
//
//	public void setDiscount(Discount discount) {
//		this.discount = discount;
//	}
//
//}
