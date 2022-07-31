package com.fanyacode.fanyacode.service;

import static com.fanyacode.fanyacode.authorization.JwtUtil.generateJWTToken;

import com.fanyacode.fanyacode.authorization.Role;
import com.fanyacode.fanyacode.controller.model.AuthResponse;
import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.model.User;
import com.fanyacode.fanyacode.repository.AuthorityRepository;
import com.fanyacode.fanyacode.repository.UserRepository;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl  implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  AuthorityRepository authorityRepository;


  @Override
  public AuthResponse validateUser(String email, String password) throws AuthException {
    if(email != null) email = email.toLowerCase();
    User user = userRepository.findByEmailAndPassword(email, password);
    String token = generateJWTToken(user, Role.DEFAULT_USER.getRole());
    return new AuthResponse(token);
  }

  @Override
  public AuthResponse registerUser(String firstName, String lastName, String email, String password) throws AuthException {
    //check email is valid and it's not duplicate
    Pattern pattern = Pattern.compile("^(.+)@(.+)$");
    if(email != null) email = email.toLowerCase();
    if(!pattern.matcher(email).matches())
      throw new AuthException("Invalid email format");
    Integer count = userRepository.getCountByEmail(email);
    if(count > 0)
      throw new AuthException("Email already in use");

    //create user in users table
    Integer userId = userRepository.create(firstName, lastName, email, password);

    //assign default role to user
    authorityRepository.create(userId, Role.DEFAULT_USER.getRole());

    User user = userRepository.findById(userId);
    String token = generateJWTToken(user, Role.DEFAULT_USER.getRole());
    return new AuthResponse(token);
  }
}
