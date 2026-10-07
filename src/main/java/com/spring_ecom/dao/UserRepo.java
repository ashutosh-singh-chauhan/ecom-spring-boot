package com.spring_ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_ecom.model.User;

/**
 * UserRepo
 */

public interface UserRepo extends JpaRepository<User, Integer> {

  
  User findFirstByUsername(String username);
} 
