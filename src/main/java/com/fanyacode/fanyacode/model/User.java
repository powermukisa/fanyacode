package com.fanyacode.fanyacode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "users")
//public class User extends UserAudit {
public class User extends UserAudit {
//  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
  private int userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
