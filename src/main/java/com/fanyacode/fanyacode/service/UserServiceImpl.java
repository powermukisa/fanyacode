package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.security.JwtUtil;
import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.model.User;
import com.fanyacode.fanyacode.repository.UserRepository;
import com.fanyacode.fanyacode.security.MyUserDetailsService;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl  implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Override
  public User validateUser(String email, String password) throws AuthException {
    if(email != null) email = email.toLowerCase();
    return userRepository.findByEmailAndPassword(email, password);
  }

  @Override
  public String authenticate(String email, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
    catch (BadCredentialsException e) {
      throw new Exception("Incorrect username or password", e);
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    return JwtUtil.generateToken(userDetails);
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
