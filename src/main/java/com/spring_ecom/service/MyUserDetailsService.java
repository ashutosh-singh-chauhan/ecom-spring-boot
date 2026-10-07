package com.spring_ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring_ecom.dao.UserPrincipal;
import com.spring_ecom.dao.UserRepo;
import com.spring_ecom.model.User;


@Service 
public class MyUserDetailsService implements UserDetailsService {

  @Autowired 
  private UserRepo repo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = repo.findFirstByUsername(username);

    if (user == null) {
      System.out.println("User not found");
      throw new UsernameNotFoundException("User not found!");
    }
    System.out.println("Loaded user for auth: username=" + user.getUsername() + ", passwordHash=" + user.getPassword());
    return new UserPrincipal(user);

  }

  
}
