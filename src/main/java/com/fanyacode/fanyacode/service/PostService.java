package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.exception.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.Post;
import java.util.List;

public interface PostService {

  List<Post> fetchAllTransactions(Integer userId, Integer categoryId);

  Post fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException;

  Post addTransaction(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws BadRequestException;

  void updateTransaction(Integer userId, Integer categoryId, Integer transactionId, Post transaction) throws BadRequestException;

  void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException;

}
