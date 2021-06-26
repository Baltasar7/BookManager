package com.example.demo.login.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Book;

@Mapper
public interface BookMapper {
  public int count() throws DataAccessException;
  public int insertOne(Book book) throws DataAccessException;
  public int withIdInsertOne(Book book) throws DataAccessException;
  public Book selectOne(Integer bookId) throws DataAccessException;
  public List<Book> selectAll() throws DataAccessException;
  public int updateOne(Book book) throws DataAccessException;
//  public int restIncrement(Book book) throws DataAccessException;
//  public int restDecrement(Book book) throws DataAccessException;
  public int deleteOne(Integer bookId) throws DataAccessException;
  public int deleteAll() throws DataAccessException;
  public int getAutoIncrement() throws DataAccessException;
//  public void bookCsvOut() throws DataAccessException;
}
