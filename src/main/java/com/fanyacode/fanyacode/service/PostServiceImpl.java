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
  public List<Post> fetchAllTransactions(Integer userId, Integer categoryId) {
    return postRepository.findAll(userId, categoryId);
  }

  @Override
  public Post fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {
    return postRepository.findById(userId, categoryId, transactionId);
  }

  @Override
  public Post addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws BadRequestException {
    int transactionId = postRepository.create(userId, categoryId, amount, note, transactionDate);
    return postRepository.findById(userId, categoryId, transactionId);
  }

  @Override
  public void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Post transaction) throws BadRequestException {
    postRepository.update(userId, categoryId, transactionId, transaction);
  }

  @Override
  public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {
    postRepository.removeById(userId, categoryId, transactionId);
  }
}
