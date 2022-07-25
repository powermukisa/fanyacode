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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories/{categoryId}/posts")
public class PostController {
  @Autowired
  PostService postService;

  @GetMapping("")
  public ResponseEntity<List<Post>> getAllPosts(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId) {
    int userId = (Integer) request.getAttribute("userId");
    List<Post> posts = postService.fetchAllPosts(userId, categoryId);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<Post> getPostById(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId,
      @PathVariable("postId") Integer postId) {
    int userId = (Integer) request.getAttribute("userId");
    Post post = postService.fetchPostById(userId, categoryId, postId);
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Post> addPost(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId,
      @RequestBody Map<String, Object> postMap) {
    int userId = (Integer) request.getAttribute("userId");
    Double amount = Double.valueOf(postMap.get("amount").toString());
    String note = (String) postMap.get("note");
    Long postDate = (Long) postMap.get("postDate");
    Post post = postService.addPost(userId, categoryId, amount, note, postDate);
    return new ResponseEntity<>(post, HttpStatus.CREATED);
  }

  @PutMapping("/{postId}")
  public ResponseEntity<Map<String, Boolean>> updatePost(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId,
      @PathVariable("postId") Integer postId,
      @RequestBody Post post) {
    int userId = (Integer) request.getAttribute("userId");
    postService.updatePost(userId, categoryId, postId, post);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<Map<String, Boolean>> deletePost(HttpServletRequest request,
      @PathVariable("categoryId") Integer categoryId,
      @PathVariable("postId") Integer postId) {
    int userId = (Integer) request.getAttribute("userId");
    postService.removePost(userId, categoryId, postId);
    Map<String, Boolean> map = new HashMap<>();
    map.put("success", true);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}
