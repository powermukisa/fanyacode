package com.fanyacode.fanyacode.controller.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
