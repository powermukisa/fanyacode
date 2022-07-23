package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.model.User;

public interface UserService {

  User validateUser(String email, String password) throws AuthException;

  User registerUser(String firstName, String lastName, String email, String password) throws AuthException;

}
