package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.exception.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.Category;
import java.util.List;

public interface CategoryService {

  List<Category> fetchAllCategories(Integer userId);

  Category fetchCategoryById(Integer userId, Integer categoryId) throws ResourceNotFoundException;

  Category addCategory(Integer userId, String title, String description) throws BadRequestException;

  void updateCategory(Integer userId, Integer categoryId, Category category) throws BadRequestException;

  void removeCategoryWithAllPosts(Integer userId, Integer categoryId) throws ResourceNotFoundException;

}
