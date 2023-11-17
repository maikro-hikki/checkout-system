package com.maikro.checkoutSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.DiscountByQuantity;

@Repository
public interface DiscountByQuantityRepo extends JpaRepository<DiscountByQuantity, Long> {

}
