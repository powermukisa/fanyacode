package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.exception.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.Post;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository {

  List<Post> findAll(Integer userId, Integer categoryId);

  Post findById(Integer userId, Integer categoryId, Integer postId) throws ResourceNotFoundException;

  Integer create(Integer userId, Integer categoryId, Double amount, String note, Long postDate) throws BadRequestException;

  void update(Integer userId, Integer categoryId, Integer postId, Post post) throws BadRequestException;

  void removeById(Integer userId, Integer categoryId, Integer postId) throws ResourceNotFoundException;

}
