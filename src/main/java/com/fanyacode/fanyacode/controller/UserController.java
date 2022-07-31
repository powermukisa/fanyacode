package com.fanyacode.fanyacode.controller;

import com.fanyacode.fanyacode.controller.model.response.LoginResponse;
import com.fanyacode.fanyacode.controller.model.request.LoginRequest;
import com.fanyacode.fanyacode.controller.model.request.SignupRequest;
import com.fanyacode.fanyacode.controller.model.response.SimpleSuccessResponse;
import com.fanyacode.fanyacode.model.User;
import com.fanyacode.fanyacode.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Autowired
  UserService userService;

  @Operation(summary = "Create a user with default role: USER. Admins are given the ADMIN role by manually updating the authorities table (this is temporary)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "User Created",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = User.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid input",
          content = @Content)
  })
  @PostMapping("/signup")
  public ResponseEntity<SimpleSuccessResponse> registerUser(@RequestBody SignupRequest request) {
    userService.registerUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword());
    return new ResponseEntity<>(new SimpleSuccessResponse(true), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) {
    LoginResponse response = userService.validateUser(request.getEmail(), request.getPassword());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

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
}
