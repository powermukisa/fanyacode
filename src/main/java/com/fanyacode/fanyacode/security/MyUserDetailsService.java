package com.fanyacode.fanyacode.security;

import com.fanyacode.fanyacode.model.User;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    if(email != null) email = email.toLowerCase();
    User user = userRepository.findByEmailAndPassword(email, password);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    }
    return new MyUserPrincipal(user);
  }
}
