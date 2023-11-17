package com.maikro.checkoutSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "discountId")
public class DiscountByProduct extends Discount{

}
