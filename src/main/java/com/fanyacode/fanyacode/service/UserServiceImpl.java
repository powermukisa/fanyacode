package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.model.User;
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

  @Override
  public User validateUser(String email, String password) throws AuthException {
    if(email != null) email = email.toLowerCase();
    return userRepository.findByEmailAndPassword(email, password);
  }

  @Override
  public User registerUser(String firstName, String lastName, String email, String password) throws AuthException {
    Pattern pattern = Pattern.compile("^(.+)@(.+)$");
    if(email != null) email = email.toLowerCase();
    if(!pattern.matcher(email).matches())
      throw new AuthException("Invalid email format");
    Integer count = userRepository.getCountByEmail(email);
    if(count > 0)
      throw new AuthException("Email already in use");
    Integer userId = userRepository.create(firstName, lastName, email, password);
    return userRepository.findById(userId);
  }
}
