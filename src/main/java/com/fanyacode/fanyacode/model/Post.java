package com.fanyacode.fanyacode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "users")
public class Post {
  private Integer postId;
  private Integer categoryId;
  private Integer userId;
  private Double amount; //placeholder for learning
  private String note;
  private Long postDate;
}
