package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> { }
