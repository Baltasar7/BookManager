package com.example.demo.login.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

@Mapper
public interface UserMapper {
  public int count() throws DataAccessException;
  public int insertOne(User user) throws DataAccessException;
  public User selectOne(String userId) throws DataAccessException;
  public List<User> selectAll() throws DataAccessException;
  public int updateOne(User user) throws DataAccessException;
  public int deleteOne(String userId) throws DataAccessException;
  public int deleteAll() throws DataAccessException;
//  public void userCsvOut() throws DataAccessException;
}
