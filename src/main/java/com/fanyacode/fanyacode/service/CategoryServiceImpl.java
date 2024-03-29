package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.exception.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.Category;
import com.fanyacode.fanyacode.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  @Override
  public List<Category> fetchAllCategories(Integer userId) {
    return categoryRepository.findAll(userId);
  }

  @Override
  public Category fetchCategoryById(Integer userId, Integer categoryId) throws ResourceNotFoundException {
    return categoryRepository.findById(userId, categoryId);
  }

  @Override
  public Category addCategory(Integer userId, String title, String description) throws BadRequestException {
    int categoryId = categoryRepository.create(userId, title, description);
    return categoryRepository.findById(userId, categoryId);
  }

  @Override
  public void updateCategory(Integer userId, Integer categoryId, Category category) throws BadRequestException {
    categoryRepository.update(userId, categoryId, category);
  }

  @Override
  public void removeCategoryWithAllPosts(Integer userId, Integer categoryId) throws ResourceNotFoundException {
    this.fetchCategoryById(userId, categoryId);
    categoryRepository.removeById(userId, categoryId);
  }
}
