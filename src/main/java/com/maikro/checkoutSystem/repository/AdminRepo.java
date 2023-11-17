package com.maikro.checkoutSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long>{

}
