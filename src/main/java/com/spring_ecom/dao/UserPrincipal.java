package com.spring_ecom.dao;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring_ecom.model.User;

// UserPrincipal means referring to the current user
public class UserPrincipal implements UserDetails {
  private User user;
  public UserPrincipal(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // This method is to check the user authorization (Roles) -> Admin, User, Customer, Trainer, Employee
    return Collections.singleton(new SimpleGrantedAuthority("USER"));
  }

  @Override
  public @Nullable String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

}
