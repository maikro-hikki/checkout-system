package com.maikro.checkoutSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.Basket;

@Repository
public interface BasketRepo extends JpaRepository<Basket, Long> {
	
	public List<Basket> findByCustomerUserId(long userId);

}
