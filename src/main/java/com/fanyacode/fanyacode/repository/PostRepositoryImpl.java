package com.fanyacode.fanyacode.repository;

import com.fanyacode.fanyacode.exception.BadRequestException;
import com.fanyacode.fanyacode.exception.ResourceNotFoundException;
import com.fanyacode.fanyacode.model.Post;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepository{
  private static final String SQL_FIND_ALL = "SELECT POST_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, POST_DATE FROM POSTS WHERE USER_ID = ? AND CATEGORY_ID = ?";
  private static final String SQL_FIND_BY_ID = "SELECT POST_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, POST_DATE FROM POSTS WHERE USER_ID = ? AND CATEGORY_ID = ? AND POST_ID = ?";
  private static final String SQL_CREATE = "INSERT INTO POSTS (POST_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, POST_DATE) VALUES(NEXTVAL('POSTS_SEQ'), ?, ?, ?, ?, ?)";
  private static final String SQL_UPDATE = "UPDATE POSTS SET AMOUNT = ?, NOTE = ?, POST_DATE = ? WHERE USER_ID = ? AND CATEGORY_ID = ? AND POST_ID = ?";
  private static final String SQL_DELETE = "DELETE FROM POSTS WHERE USER_ID = ? AND CATEGORY_ID = ? AND POST_ID = ?";

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public List<Post> findAll(Integer userId, Integer categoryId) {
    return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId, categoryId}, postRowMapper);
  }

  @Override
  public Post findById(Integer userId, Integer categoryId, Integer postId) throws ResourceNotFoundException {
    try {
      return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId, postId}, postRowMapper);
    }catch (Exception e) {
      throw new ResourceNotFoundException("Post not found");
    }
  }

  @Override
  public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long postDate) throws BadRequestException {
    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, categoryId);
        ps.setInt(2, userId);
        ps.setDouble(3, amount);
        ps.setString(4, note);
        ps.setLong(5, postDate);
        return ps;
      }, keyHolder);
      return (Integer) keyHolder.getKeys().get("POST_ID");
    }catch (Exception e) {
      throw new BadRequestException("Invalid request");
    }
  }

  @Override
  public void update(Integer userId, Integer categoryId, Integer postId, Post post) throws BadRequestException {
    try {
      jdbcTemplate.update(SQL_UPDATE, new Object[]{post.getAmount(), post.getNote(), post.getPostDate(), userId, categoryId, postId});
    }catch (Exception e) {
      throw new BadRequestException("Invalid request");
    }
  }

  @Override
  public void removeById(Integer userId, Integer categoryId, Integer postId) throws ResourceNotFoundException {
    int count = jdbcTemplate.update(SQL_DELETE, new Object[]{userId, categoryId, postId});
    if(count == 0)
      throw new ResourceNotFoundException("Post not found");
  }

  private RowMapper<Post> postRowMapper = ((rs, rowNum) -> {
    return new Post(rs.getInt("POST_ID"),
        rs.getInt("CATEGORY_ID"),
        rs.getInt("USER_ID"),
        rs.getDouble("AMOUNT"),
        rs.getString("NOTE"),
        rs.getLong("POST_DATE")
    );
  });
}
