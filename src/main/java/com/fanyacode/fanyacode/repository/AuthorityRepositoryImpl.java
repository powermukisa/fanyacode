package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.model.Authority;
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
public class AuthorityRepositoryImpl implements AuthorityRepository{
  private static final String SQL_CREATE = "INSERT INTO AUTHORITIES (AUTHORITY_ID, USER_ID, AUTHORITY) VALUES(NEXTVAL('AUTHORITIES_SEQ'), ?, ?)";

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Integer create(Integer userId, Integer authority) throws AuthException {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, userId);
        ps.setInt(2, authority);
        return ps;
      }, keyHolder);
      return (Integer) keyHolder.getKeys().get("AUTHORITY_ID");
    }catch (Exception e) {
      throw new AuthException("Failed to create authority with error: " + e.getMessage());
    }
  }

  private RowMapper<Authority> authorityRowMapper = ((rs, rowNum) -> {
    return new Authority(rs.getInt("AUTHORITY_ID"),
        rs.getInt("USER_ID"),
        rs.getInt("AUTHORITY")
    );
  });
}
