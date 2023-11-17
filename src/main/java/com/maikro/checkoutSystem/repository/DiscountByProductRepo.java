package com.maikro.checkoutSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.DiscountByProduct;

@Repository
public interface DiscountByProductRepo extends JpaRepository<DiscountByProduct, Long>{

}
