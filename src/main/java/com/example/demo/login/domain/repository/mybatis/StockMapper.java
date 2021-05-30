package com.example.demo.login.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Stock;

@Mapper
public interface StockMapper {
  public int count() throws DataAccessException;
  public int getStockCount(int bookId) throws DataAccessException;
  public int getRestCount(int bookId) throws DataAccessException;
  public int insertOne(Stock stock) throws DataAccessException;
  public Stock selectOne(int stockId) throws DataAccessException;
  public List<Stock> selectAll() throws DataAccessException;
  public int updateOne(Stock stock) throws DataAccessException;
  public int deleteOne(int stockId) throws DataAccessException;
//  public void stockCsvOut() throws DataAccessException;
}
