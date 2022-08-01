package com.fanyacode.fanyacode.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Input {
  private String path;
  private String token;
  private String method;
}
