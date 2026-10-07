package com.spring_ecom.service;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring_ecom.dao.UserRepo;
import com.spring_ecom.model.User;

@Service 
public class UserService {
  @Autowired 
  private UserRepo userRepo;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public User saveUser(User user) {
    user.setPassword(encoder.encode(user.getPassword()));
    return userRepo.save(user);
  }
}
