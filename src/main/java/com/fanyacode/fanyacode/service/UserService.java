package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.controller.model.response.AuthResponse;
import com.fanyacode.fanyacode.exception.AuthException;

public interface UserService {

  AuthResponse validateUser(String email, String password) throws AuthException;

  AuthResponse registerUser(String firstName, String lastName, String email, String password) throws AuthException;

}
