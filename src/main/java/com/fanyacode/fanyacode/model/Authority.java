package com.fanyacode.fanyacode.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
  private int authorityId;
  private int userId;
  private int authority;
}
