package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.authorization.Role;
import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.exception.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.Authority;
import com.fanyacode.fanyacode.model.Post;
import com.fanyacode.fanyacode.repository.AuthorityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

  @Autowired
  AuthorityRepository authorityRepository;

  @Override
  public Authority createAuthority(Integer userId) throws BadRequestException {
    authorityRepository.create(userId, Role.DEFAULT_USER.getRole());
    return authorityRepository.findByUserId(userId);
  }

  @Override
  public void updateAuthority(Integer userId, Integer authority) throws BadRequestException {
    authorityRepository.update(userId, authority);
  }
}
