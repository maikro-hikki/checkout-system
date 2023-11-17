package com.maikro.checkoutSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.BasketInventory;

@Repository
public interface BasketInventoryRepo extends JpaRepository<BasketInventory, Long>{

}
