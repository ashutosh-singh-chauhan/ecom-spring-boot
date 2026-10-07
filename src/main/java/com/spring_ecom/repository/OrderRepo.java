package com.spring_ecom.repository;

import com.spring_ecom.model.Order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
  Optional<Order> findByOrderId(String orderId);
}
