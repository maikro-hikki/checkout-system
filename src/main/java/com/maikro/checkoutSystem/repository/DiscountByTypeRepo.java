package com.maikro.checkoutSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.DiscountByType;

@Repository
public interface DiscountByTypeRepo extends JpaRepository<DiscountByType, Long> {

}
