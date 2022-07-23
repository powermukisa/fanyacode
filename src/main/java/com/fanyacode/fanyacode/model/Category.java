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
public class Category {
  private Integer categoryId;
  private Integer userId;
  private String title;
  private String description;
  private Double totalExpense;  //placeholder for learning
}
