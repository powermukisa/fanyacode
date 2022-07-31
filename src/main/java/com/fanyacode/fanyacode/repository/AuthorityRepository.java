package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.AuthException;

public interface AuthorityRepository {

  Integer create(Integer userId, Integer authority) throws AuthException;

}
