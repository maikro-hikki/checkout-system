package com.maikro.checkoutSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.AdminProduct;

@Repository
public interface AdminProductRepo extends JpaRepository<AdminProduct, Long>{

}
