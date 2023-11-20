package com.maikro.checkoutSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.Discount;
import com.maikro.checkoutSystem.model.ProductDiscount;

@Repository
public interface ProductDiscountRepo extends JpaRepository<ProductDiscount, Long> {
	
	public List<ProductDiscount> findByProductProductId(long productId);
	
	@Query("SELECT pd.discount FROM ProductDiscount pd WHERE pd.product.productId = :productId")
	public List<Discount> findDiscountsByProductId(long productId);

}
