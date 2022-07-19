package com.fanyacode.fanyacode.controller;

import com.fanyacode.fanyacode.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.User;
import com.fanyacode.fanyacode.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserRepository repository;

  @PostMapping("/user")
  public User create(@RequestBody User user) {
    return repository.save(user);
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> findAll() {
    return ResponseEntity.ok(repository.findAll());
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<User> findById(@PathVariable(value = "id") Integer id) {

    User user = repository.findById(id).orElseThrow(
        ()-> new ResourceNotFoundException("User not found " +id)
    );
    return ResponseEntity.ok().body(user);
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
    User user = findById(id).getBody();
    assert user != null;
    repository.delete(user);
    return ResponseEntity.ok().build();
  }

}
