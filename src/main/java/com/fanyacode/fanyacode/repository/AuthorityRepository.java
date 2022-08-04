package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.model.Authority;
import java.util.List;

public interface AuthorityRepository {

  Integer create(Integer userId, Integer authority) throws AuthException;
  List<Authority> findAll();
  Authority findByUserId(Integer userId);
  void update(Integer authority, Integer userId) throws BadRequestException;
}
