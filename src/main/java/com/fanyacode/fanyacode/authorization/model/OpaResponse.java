package com.fanyacode.fanyacode.authorization.model;


import lombok.Data;

@Data
public class OpaResponse {
  private Result result;
  private String decisionId;
}


@Data
class Token {
  private Payload payload;
}

@Data
class Payload {
  private String email;
  private Long exp;
  private String firstNamr;
  private Long iat;
  private String lastName;
  private Integer role;
  private Integer userId;
}