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
  private Double amount; //todo set this to "difficulty" on a range of 1-5. a category findById would display the difficulty level in total for each category
  private String note;
  private Long postDate;
}
