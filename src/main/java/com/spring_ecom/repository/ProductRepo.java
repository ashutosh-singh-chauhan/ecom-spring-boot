package com.spring_ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring_ecom.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

  @Query("Select p from Product p Where " +
      "Lower(p.name) Like LOWER(CONCAT('%', :keyword, '%'))")
  List<Product> searchProducts(String keyword);
}
