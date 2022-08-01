package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.controller.model.response.LoginResponse;
import com.fanyacode.fanyacode.exception.AuthException;

public interface UserService {

  LoginResponse validateUser(String email, String password) throws AuthException;

  void registerUser(String firstName, String lastName, String email, String password) throws AuthException;

}
