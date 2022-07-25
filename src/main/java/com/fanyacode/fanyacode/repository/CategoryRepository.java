package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.exception.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.Category;
import java.util.List;
import org.springframework.stereotype.Repository;

public interface CategoryRepository {

  List<Category> findAll(Integer userId) throws ResourceNotFoundException;

  Category findById(Integer userId, Integer categoryId) throws ResourceNotFoundException;

  Integer create(Integer userId, String title, String description) throws BadRequestException;

  void update(Integer userId, Integer categoryId, Category category) throws BadRequestException;

  void removeById(Integer userId, Integer categoryId);

}
