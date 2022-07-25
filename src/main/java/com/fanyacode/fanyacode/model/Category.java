package com.fanyacode.fanyacode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "users")
public class Category {
  private Integer categoryId;
  private Integer userId;
  private String title;
  private String description;
  private Double totalExpense;  //placeholder for learning
}
