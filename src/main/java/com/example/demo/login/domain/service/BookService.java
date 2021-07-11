package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Book;
import com.example.demo.login.domain.model.Stock;
import com.example.demo.login.domain.repository.mybatis.BookMapper;
import com.example.demo.login.domain.repository.mybatis.StockMapper;

@Transactional
@Service
public class BookService {
    @Autowired
    BookMapper mapper;
    @Autowired
    StockMapper stockMapper;

    public boolean insert(Book book) {
      if (mapper.insertOne(book) <= 0) {
          return false;
      }
      return true;
    }

    public boolean insertListFromFile(List<Book> bookList) {
      for (Book book: bookList) {
        if (mapper.withIdInsertOne(book) <= 0) {
          return false;
        }
      }
      return true;
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

    private boolean updateOne(Book book) {
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
    public boolean deleteOne(int bookId) {
      if (mapper.deleteOne(bookId) <= 0) {
        return false;
      }
        return true;
    }

    public boolean deleteAll() {
      if (mapper.deleteAll() <= 0) {
          return false;
      }
      return true;
  }

    public int getLastInsertId() {
      //return this.lastInsertId;
      return 0;
    }

    public boolean registBook(
      Book book, int stock, String state) {
      this.insert(book);
      for(int i = 0; i < stock; i++) {
        stockMapper.insertOneByRegistBook(book.getBookId(), state);
      }
      return true;
    }

    public boolean updateBook(Book book) {
      int currentStock = stockMapper.getStockCount(book.getBookId());
      int updateStock = book.getStock();

      if(currentStock > updateStock) {
        return false;
      }

      if(currentStock < updateStock) {
        Stock stock = new Stock(book.getBookId(), "stock");
        for(; updateStock > currentStock; currentStock++) {
          stockMapper.insertOne(stock);
        }
      }

      this.updateOne(book);
      return true;
    }

    public boolean deleteBook(Integer bookId) {
      stockMapper.deleteBook(bookId);
      this.deleteOne(bookId);
      return true;
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
