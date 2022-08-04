package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.AuthException;
import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.model.Authority;
import com.fanyacode.fanyacode.model.Post;
import com.fanyacode.fanyacode.model.User;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
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
  private static final String SQL_FIND_BY_USER_ID = "SELECT AUTHORITY_ID, USER_ID, AUTHORITY FROM AUTHORITIES WHERE USER_ID = ?";
//  private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM AUTHORITIES WHERE USER_ID = ?";
  private static final String SQL_FIND_ALL = "SELECT * FROM AUTHORITIES";
  private static final String SQL_UPDATE = "UPDATE AUTHORITIES SET AUTHORITY = ? WHERE USER_ID = ?";

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

  @Override
  public List<Authority> findAll() {
    return jdbcTemplate.query(SQL_FIND_ALL, authorityRowMapper);
  }

  @Override
  public Authority findByUserId(Integer userId) {
    return jdbcTemplate.queryForObject(SQL_FIND_BY_USER_ID, new Object[]{userId}, authorityRowMapper);
  }

  @Override
  public void update(Integer userId, Integer authority) throws BadRequestException {
    try {
      jdbcTemplate.update(SQL_UPDATE, new Object[]{authority, userId});
    }catch (Exception e) {
      throw new BadRequestException("Invalid request");
    }
  }

  private RowMapper<Authority> authorityRowMapper = ((rs, rowNum) -> {
    return new Authority(rs.getInt("AUTHORITY_ID"),
        rs.getInt("USER_ID"),
        rs.getInt("AUTHORITY")
    );
  });
}
