package com.maikro.checkoutSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {

}
