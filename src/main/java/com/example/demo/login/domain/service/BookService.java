package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Book;
import com.example.demo.login.domain.repository.mybatis.BookMapper;

@Transactional
@Service
public class BookService {
		@Autowired
		BookMapper mapper;

    public boolean insert(Book book) {
        int rowNumber = mapper.insertOne(book);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public int count() {
        return mapper.count();
    }

    public List<Book> selectAll() {
        return mapper.selectAll();
    }

    public Book selectOne(Integer bookId) {
        return mapper.selectOne(bookId);
    }

    public boolean updateOne(Book book) {
        boolean result = false;
        int rowNumber = mapper.updateOne(book);
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }
/*
    public boolean restIncrement(Book book) {
      boolean result = false;
      int rowNumber = mapper.restIncrement(book);
      if (rowNumber > 0) {
          result = true;
      }
      return result;
  }

    public boolean restDecrement(Book book) {
      boolean result = false;
      int rowNumber = mapper.restDecrement(book);
      if (rowNumber > 0) {
          result = true;
      }
      return result;
  }
*/
    public boolean deleteOne(Integer bookId) {
        int rowNumber = mapper.deleteOne(bookId);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }
/*
    public void bookCsvOut() throws DataAccessException {
        mapper.bookCsvOut();
    }

    public byte[] getFile(String fileName) throws IOException {
        FileSystem fs = FileSystems.getDefault();
        Path p = fs.getPath(fileName);
        byte[] bytes = Files.readAllBytes(p);
        return bytes;
    }
*/
}
