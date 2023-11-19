package com.maikro.checkoutSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.ProductDiscount;

@Repository
public interface ProductDiscountRepo extends JpaRepository<ProductDiscount, Long> {
	
	public List<ProductDiscount> findByProductProductId(long productId);

}
