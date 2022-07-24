package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.exception.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.Post;
import com.fanyacode.fanyacode.repository.PostRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PostServiceImpl implements PostService {

  @Autowired
  PostRepository postRepository;

  @Override
  public List<Post> fetchAllPosts(Integer userId, Integer categoryId) {
    return postRepository.findAll(userId, categoryId);
  }

  @Override
  public Post fetchPostById(Integer userId, Integer categoryId, Integer postId) throws ResourceNotFoundException {
    return postRepository.findById(userId, categoryId, postId);
  }

  @Override
  public Post addPost(Integer userId, Integer categoryId, Double amount, String note, Long postDate) throws BadRequestException {
    int postId = postRepository.create(userId, categoryId, amount, note, postDate);
    return postRepository.findById(userId, categoryId, postId);
  }

  @Override
  public void updatePost(Integer userId, Integer categoryId, Integer postId, Post post) throws BadRequestException {
    postRepository.update(userId, categoryId, postId, post);
  }

  @Override
  public void removePost(Integer userId, Integer categoryId, Integer postId) throws ResourceNotFoundException {
    postRepository.removeById(userId, categoryId, postId);
  }
}
