package com.maikro.checkoutSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.Discount;

@Repository
public interface DiscountRepo extends JpaRepository<Discount, Long> {

}
