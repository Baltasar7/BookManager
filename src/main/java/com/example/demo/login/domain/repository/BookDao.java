package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Book;

public interface BookDao {
    public int count() throws DataAccessException;
    public int insertOne(Book book) throws DataAccessException;
    public Book selectOne(String bookId) throws DataAccessException;
    public List<Book> selectMany() throws DataAccessException;
    public int updateOne(Book book) throws DataAccessException;
    public int deleteOne(String bookId) throws DataAccessException;
    public void bookCsvOut() throws DataAccessException;
}
