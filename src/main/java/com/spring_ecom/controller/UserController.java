package com.spring_ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring_ecom.model.User;
import com.spring_ecom.service.JwtService;
import com.spring_ecom.service.UserService;

@RestController 
public class UserController {

  @Autowired 
  private UserService userService;

  @Autowired 
  private AuthenticationManager authenticationManager;

  @Autowired 
  JwtService jwtService;

  @PostMapping ("/register")
  public User register(@RequestBody User user) {
    return userService.saveUser(user);
  }


  @PostMapping ("/login")
  public String login(@RequestBody User user) {
    try {
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
      );

      if (authentication.isAuthenticated()) {
        System.out.println("Came inside the if");
        return jwtService.generateToken(user.getUsername());
      }

        
      else return "Login Fail";
    } catch (AuthenticationException ex) {
      ex.printStackTrace();
      return "Auth error: " + ex.getMessage();
    }
  }
}
