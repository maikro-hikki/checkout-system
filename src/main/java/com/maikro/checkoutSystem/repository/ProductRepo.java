package com.maikro.checkoutSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

}
