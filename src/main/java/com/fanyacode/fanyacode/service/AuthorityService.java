package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.model.Authority;

public interface AuthorityService {
  Authority createAuthority(Integer userId) throws BadRequestException;

  void updateAuthority(Integer userId, Integer authority) throws BadRequestException;
}
