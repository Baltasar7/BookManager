package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Book;
import com.example.demo.login.domain.repository.BookDao;

@Repository("BookDaoNamedJdbcImpl")
public class BookDaoNamedJdbcImpl implements BookDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM m_book";
        SqlParameterSource params = new MapSqlParameterSource();
        return jdbc.queryForObject(sql, params, Integer.class);
    }

    @Override
    public int insertOne(Book book) {
        String sql = "INSERT INTO m_book("
                +   " title,"
                +   " author,"
                +   " publisher)"
                + " VALUES("
                +   " :title,"
                +   " :author,"
                +   " :publisher)";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author", book.getAuthor())
                .addValue("publisher", book.getPublisher());
        return jdbc.update(sql, params);
    }

    @Override
    public Book selectOne(Integer bookId) {
        String sql = "SELECT * FROM m_book WHERE book_id = :bookId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("bookId", bookId);
        Map<String, Object> map = jdbc.queryForMap(sql, params);

        Book book = new Book();
        book.setBookId((Integer)map.get("book_id"));
        book.setTitle((String)map.get("title"));
        book.setAuthor((String)map.get("author"));
        book.setPublisher((String)map.get("publisher"));

        return book;
    }

    @Override
    public List<Book> selectMany() {
        String sql = "SELECT * FROM m_book";
        SqlParameterSource params = new MapSqlParameterSource();
        List<Map<String, Object>> getList = jdbc.queryForList(sql, params);
        List<Book> bookList = new ArrayList<>();

        for(Map<String, Object> map: getList) {
	          Book book = new Book();
	          book.setBookId((Integer)map.get("book_id"));
	          book.setTitle((String)map.get("title"));
	          book.setAuthor((String)map.get("author"));
	          book.setPublisher((String)map.get("publisher"));
            bookList.add(book);
        }
        return bookList;
    }

    @Override
    public int updateOne(Book book) {
        String sql = "UPDATE m_book"
                + " SET"
                +   " title = :title,"
                +   " author = :author,"
                +   " publisher = :publisher"
                +   " WHERE book_id = :bookId";
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("bookId", book.getBookId())
            .addValue("title", book.getTitle())
            .addValue("author", book.getAuthor())
            .addValue("publisher", book.getPublisher());
        return jdbc.update(sql, params);
    }

    @Override
    public int deleteOne(Integer bookId) {
        String sql = "DELETE FROM m_book WHERE book_id = :bookId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("bookId", bookId);
        int rowNumber = jdbc.update(sql, params);
        return rowNumber;
    }

    @Override
    public void bookCsvOut() {
        String sql = "SELECT * FROM m_book";
        BookRowCallbackHandler handler = new BookRowCallbackHandler();
        jdbc.query(sql, handler);
    }
}