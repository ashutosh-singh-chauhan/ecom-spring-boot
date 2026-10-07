package com.spring_ecom.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_ecom.model.Order;
import com.spring_ecom.model.OrderItem;
import com.spring_ecom.model.Product;
import com.spring_ecom.model.dto.OrderItemRequest;
import com.spring_ecom.model.dto.OrderItemResponse;
import com.spring_ecom.model.dto.OrderRequest;
import com.spring_ecom.model.dto.OrderResponse;
import com.spring_ecom.repository.OrderRepo;
import com.spring_ecom.repository.ProductRepo;

/**
 * OrderService
 */
@Service 
public class OrderService {

  @Autowired 
  ProductRepo productRepo;

  @Autowired 
  OrderRepo orderRepo;


  public OrderResponse placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    order.setOrderId(orderId);
    order.setCustomerName(orderRequest.customerName());
    order.setEmail(orderRequest.email());
    order.setStatus("PLACED");
    order.setOrderDate(LocalDate.now());

    List<OrderItem> orderItems = new ArrayList<>();
    // Loop over order items
    for (OrderItemRequest itemReq : orderRequest.items()) {
      Product product = productRepo.findById(itemReq.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

      product.setStockQuantity(product.getStockQuantity() - itemReq.quantity());
      productRepo.save(product);

      // Builder pattern to create an object of a class
      OrderItem orderItem = OrderItem.builder()
            .product(product)
            .quantity(itemReq.quantity())
            .totalPrice(product.getPrice() * itemReq.quantity())
            .order(order)
            .build();
      orderItems.add(orderItem);
    }
    order.setOrderItems(orderItems);
    Order savedOrder = orderRepo.save(order);
    
    List<OrderItemResponse> itemResponses = new ArrayList<>();
    for (OrderItem item : order.getOrderItems()) {
      OrderItemResponse orderItemResponse =  new OrderItemResponse(
        item.getProduct().getName(),
        item.getQuantity(),
        item.getTotalPrice()
      );
      itemResponses.add(orderItemResponse);
    }
    OrderResponse orderResponse = new OrderResponse(
      savedOrder.getOrderId(),
      savedOrder.getCustomerName(),
      savedOrder.getEmail(),
      savedOrder.getStatus(),
      savedOrder.getOrderDate(),
      itemResponses
    );
    return orderResponse;
  }

  public List<OrderResponse> getAllOrders() {
    List<Order> orders = orderRepo.findAll();

    List<OrderResponse> orderResponses = new ArrayList<>();

    for (Order order : orders) {
      List<OrderItemResponse> itemResponses = new ArrayList<>();
      // Loop to map the order items responses
      for (OrderItem item : order.getOrderItems()) {
        OrderItemResponse orderItemResponse = new OrderItemResponse(
          item.getProduct().getName(),
          item.getQuantity(),
          item.getTotalPrice()
        );    
        itemResponses.add(orderItemResponse); 
      }
      OrderResponse orderResponse = new OrderResponse(
        order.getOrderId(),
        order.getCustomerName(),
        order.getEmail(),
        order.getStatus(),
        order.getOrderDate(),
        itemResponses
      );
      orderResponses.add(orderResponse);
    }
    return orderResponses;
  }

}
