package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Book;

public interface BookDao {
    public int count() throws DataAccessException;
    public int insertOne(Book book) throws DataAccessException;
    public Book selectOne(Integer bookId) throws DataAccessException;
    public List<Book> selectAll() throws DataAccessException;
    public int updateOne(Book book) throws DataAccessException;
    public int deleteOne(Integer bookId) throws DataAccessException;
    public void bookCsvOut() throws DataAccessException;
}
