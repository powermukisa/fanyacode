package com.fanyacode.fanyacode.controller;

import com.fanyacode.fanyacode.model.Post;
import com.fanyacode.fanyacode.service.PostService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PostController {
  @Autowired
  PostService postService;

  @GetMapping("")
  public ResponseEntity<List<Post>> getAllTransactions(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId) {
    int userId = (Integer) request.getAttribute("userId");
    List<Post> transactions = postService.fetchAllTransactions(userId, categoryId);
    return new ResponseEntity<>(transactions, HttpStatus.OK);
  }

  @GetMapping("/{transactionId}")
  public ResponseEntity<Post> getTransactionById(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId,
      @PathVariable("transactionId") Integer transactionId) {
    int userId = (Integer) request.getAttribute("userId");
    Post post = postService.fetchTransactionById(userId, categoryId, transactionId);
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Post> addTransaction(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId,
      @RequestBody Map<String, Object> transactionMap) {
    int userId = (Integer) request.getAttribute("userId");
    Double amount = Double.valueOf(transactionMap.get("amount").toString());
    String note = (String) transactionMap.get("note");
    Long transactionDate = (Long) transactionMap.get("transactionDate");
    Post post = postService.addTransaction(userId, categoryId, amount, note, transactionDate);
    return new ResponseEntity<>(post, HttpStatus.CREATED);
  }

  @PutMapping("/{transactionId}")
  public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId,
      @PathVariable("transactionId") Integer transactionId,
      @RequestBody Post post) {
    int userId = (Integer) request.getAttribute("userId");
    postService.updateTransaction(userId, categoryId, transactionId, post);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @DeleteMapping("/{transactionId}")
  public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId,
      @PathVariable("transactionId") Integer transactionId) {
    int userId = (Integer) request.getAttribute("userId");
    postService.removeTransaction(userId, categoryId, transactionId);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}
