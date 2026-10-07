package com.spring_ecom.model.dto;

// Records have been added in Java 16 to act like an immutable carrier of data  
public record OrderItemRequest(
  int productId,
  int quantity
) {

}
