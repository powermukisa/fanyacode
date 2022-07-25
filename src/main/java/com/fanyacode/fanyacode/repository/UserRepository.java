package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.model.User;

//public interface UserRepository extends JpaRepository<User, Integer> { }

//@Repository
public interface UserRepository {

  Integer create(String firstName, String lastName, String email, String password) throws AuthException;

  User findByEmailAndPassword(String email, String password) throws AuthException;

  Integer getCountByEmail(String email);

  User findById(Integer userId);

}
