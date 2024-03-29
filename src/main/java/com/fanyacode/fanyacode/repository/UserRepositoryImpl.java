package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.model.User;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl  implements UserRepository{
  private static final String SQL_CREATE = "INSERT INTO USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ENABLED) VALUES(NEXTVAL('USERS_SEQ'), ?, ?, ?, ?, ?)";
  private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM USERS WHERE EMAIL = ?";
  private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ENABLED " +
      "FROM USERS WHERE USER_ID = ?";
  private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ENABLED " +
      "FROM USERS WHERE EMAIL = ?";

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Integer create(String firstName, String lastName, String email, String password) throws AuthException {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, email);
        ps.setString(4, hashedPassword);
        ps.setBoolean(5, true);
        return ps;
      }, keyHolder);
      return (Integer) keyHolder.getKeys().get("USER_ID");
    }catch (Exception e) {
      throw new AuthException("Invalid details. Failed to create account");
    }
  }

  @Override
  public User findByEmailAndPassword(String email, String password) throws AuthException {
    try {
      User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
      if(!BCrypt.checkpw(password, user.getPassword()))
        throw new AuthException("Invalid email/password");
      return user;
    }catch (EmptyResultDataAccessException e) {
      throw new AuthException("Invalid email/password");
    }
  }

  @Override
  public Integer getCountByEmail(String email) {
    return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
  }

  @Override
  public User findById(Integer userId) {
    return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
  }

  private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
    return new User(rs.getInt("USER_ID"),
        rs.getString("FIRST_NAME"),
        rs.getString("LAST_NAME"),
        rs.getString("EMAIL"),
        rs.getString("PASSWORD"),
        rs.getBoolean("ENABLED")
    );
  });
}
