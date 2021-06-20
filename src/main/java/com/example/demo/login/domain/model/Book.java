package com.example.demo.login.domain.model;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class Book {
    @CsvBindByName(column = "書籍ID", required = false)
    private Integer bookId;
    @CsvBindByName(column = "タイトル", required = true)
    private String title;
    @CsvBindByName(column = "著者", required = true)
    private String author;
    @CsvBindByName(column = "出版社", required = true)
    private String publisher;

    private Integer stock;
    private Integer rest;

//    private Date releaseDate;
//    private String format;
//    private int pageCount;
//    private String kind;
//    private List<String> keywords;
}
