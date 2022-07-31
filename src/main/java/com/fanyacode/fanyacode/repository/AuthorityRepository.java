package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.model.Authority;

public interface AuthorityRepository {

  Integer create(Integer userId, Integer authority) throws AuthException;
  Authority findByUserId(Integer userId);
  void update(Integer authority, Integer userId) throws BadRequestException;
}
