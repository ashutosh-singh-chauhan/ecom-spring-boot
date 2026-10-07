package com.spring_ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_ecom.model.dto.OrderRequest;
import com.spring_ecom.model.dto.OrderResponse;
import com.spring_ecom.service.OrderService;

@RestController 
@RequestMapping ("/api")
@CrossOrigin (origins = "http://localhost:5173")
public class OrderController {

  @Autowired 
  private OrderService orderService;

  @PostMapping ("/orders/place")
  public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
    OrderResponse orderResponse = orderService.placeOrder(orderRequest);

    return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
  }

  @GetMapping ("/orders")
  public ResponseEntity<List<OrderResponse>> getAllOrders() {
    List<OrderResponse> responses = orderService.getAllOrders();

    return new ResponseEntity<>(responses, HttpStatus.OK);
  }
}
