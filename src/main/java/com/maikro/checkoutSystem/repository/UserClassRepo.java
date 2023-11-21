package com.maikro.checkoutSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maikro.checkoutSystem.model.UserClass;

@Repository
public interface UserClassRepo extends JpaRepository<UserClass, Long>{
	
	public Optional<UserClass> findByUsername(String username);

}
