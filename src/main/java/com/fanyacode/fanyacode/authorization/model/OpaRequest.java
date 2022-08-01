package com.fanyacode.fanyacode.authorization.model;


import com.fanyacode.fanyacode.authorization.model.Input;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpaRequest {
  private Input input;
}

