package com.fanyacode.fanyacode.authorization.model;

import lombok.Data;

@Data
public class AuthZ {

  private Boolean allow;
  private Token token;
  private String user_owns_token;
}
