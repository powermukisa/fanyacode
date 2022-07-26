package com.fanyacode.fanyacode.controller;

import com.fanyacode.fanyacode.security.JwtUtil;
import com.fanyacode.fanyacode.model.User;
import com.fanyacode.fanyacode.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

//  @Autowired
//  private UserRepository repository;
//
//  @PostMapping("/register")
//  public User create(@RequestBody User user) {
//    return repository.save(user);
//  }

//  @GetMapping("/users")
//  public ResponseEntity<List<User>> findAll() {
//    return ResponseEntity.ok(repository.findAll());
//  }
//
//  @GetMapping("/user/{id}")
//  public ResponseEntity<User> findById(@PathVariable(value = "id") Integer id) {
//
//    User user = repository.findById(id).orElseThrow(
//        ()-> new ResourceNotFoundException("User not found " +id)
//    );
//    return ResponseEntity.ok().body(user);
//  }
//
//  @DeleteMapping("/user/{id}")
//  public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) {
//    User user = findById(id).getBody();
//    assert user != null;
//    repository.delete(user);
//    return ResponseEntity.ok().build();
//  }


  @Autowired
  UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
    String firstName = (String) userMap.get("firstName");
    String lastName = (String) userMap.get("lastName");
    String email = (String) userMap.get("email");
    String password = (String) userMap.get("password");
    User user = userService.registerUser(firstName, lastName, email, password);
    return new ResponseEntity<>(JwtUtil.generateToken(user), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
    String email = (String) userMap.get("email");
    String password = (String) userMap.get("password");
    User user = userService.validateUser(email, password);
    return new ResponseEntity<>(userService.authenticate(email, password), HttpStatus.OK);
  }
}
