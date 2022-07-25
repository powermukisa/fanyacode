package com.fanyacode.fanyacode.controller;

import com.fanyacode.fanyacode.Constants;
import com.fanyacode.fanyacode.model.User;
import com.fanyacode.fanyacode.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
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
    return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
    String email = (String) userMap.get("email");
    String password = (String) userMap.get("password");
    User user = userService.validateUser(email, password);
    return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
  }

  private Map<String, String> generateJWTToken(User user) {
    long timestamp = System.currentTimeMillis();
    String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
        .setIssuedAt(new Date(timestamp))
        .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
        .claim("userId", user.getUserId())
        .claim("email", user.getEmail())
        .claim("firstName", user.getFirstName())
        .claim("lastName", user.getLastName())
        .compact();
    Map<String, String> map = new HashMap<>();
    map.put("token", token);
    return map;
  }
}
