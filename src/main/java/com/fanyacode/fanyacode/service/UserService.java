package com.fanyacode.fanyacode.service;

import com.fanyacode.fanyacode.controller.model.AuthResponse;
import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.model.User;

public interface UserService {

  AuthResponse validateUser(String email, String password) throws AuthException;

  AuthResponse registerUser(String firstName, String lastName, String email, String password) throws AuthException;

}
