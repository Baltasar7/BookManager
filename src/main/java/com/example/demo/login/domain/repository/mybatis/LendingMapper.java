package com.example.demo.login.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Lending;
import com.example.demo.login.domain.model.LendingView;

@Mapper
public interface LendingMapper {
  public int countAll() throws DataAccessException;
  public int countUser(Integer userId) throws DataAccessException;
  public int insertOne(Lending lending) throws DataAccessException;
//  public List<Lending> selectAll() throws DataAccessException;
  public List<LendingView> selectAll() throws DataAccessException;
  public Lending selectOne(Integer lendingId) throws DataAccessException;
  public List<LendingView> selectUser(Integer userId) throws DataAccessException;
  public int updateOne(Lending lending) throws DataAccessException;
  public int deleteOne(Integer lendingId) throws DataAccessException;
  public void lendingCsvOut() throws DataAccessException;
}
