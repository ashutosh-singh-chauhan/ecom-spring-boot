package com.spring_ecom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Data 
@Table(name="users")
public class User {
  @Id 
  private int id;

  private String password;

  private String username;
}
