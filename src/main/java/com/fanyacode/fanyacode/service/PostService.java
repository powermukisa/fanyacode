package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.exception.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.Post;
import java.util.List;

public interface PostService {

  List<Post> fetchAllPosts(Integer userId, Integer categoryId);

  Post fetchPostById(Integer userId, Integer categoryId, Integer postId) throws ResourceNotFoundException;

  Post addPost(Integer userId, Integer categoryId, Double amount, String note, Long postDate) throws BadRequestException;

  void updatePost(Integer userId, Integer categoryId, Integer postId, Post post) throws BadRequestException;

  void removePost(Integer userId, Integer categoryId, Integer postId) throws ResourceNotFoundException;

}
