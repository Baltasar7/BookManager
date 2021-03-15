package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Book;
import com.example.demo.login.domain.repository.BookDao;

@Transactional
@Service
public class BookService {

    @Autowired
    @Qualifier("BookDaoNamedJdbcImpl")
    BookDao dao;

    public boolean insert(Book book) {
        int rowNumber = dao.insertOne(book);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public int count() {
        return dao.count();
    }

    public List<Book> selectMany() {
        return dao.selectMany();
    }

    public Book selectOne(Integer bookId) {
        return dao.selectOne(bookId);
    }

    public boolean updateOne(Book book) {
        boolean result = false;
        int rowNumber = dao.updateOne(book);
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public boolean deleteOne(Integer bookId) {
        int rowNumber = dao.deleteOne(bookId);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public void bookCsvOut() throws DataAccessException {
        dao.bookCsvOut();
    }

    public byte[] getFile(String fileName) throws IOException {
        FileSystem fs = FileSystems.getDefault();
        Path p = fs.getPath(fileName);
        byte[] bytes = Files.readAllBytes(p);
        return bytes;
    }
}
