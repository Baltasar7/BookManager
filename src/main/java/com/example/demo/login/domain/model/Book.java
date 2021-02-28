package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class Book {
    private String bookId; // Todo:DBで自動付与対応した時点で廃止
    private String title;
    private String author;
    private String publisher;
//    private Date releaseDate;
//    private String format;
//    private int pageCount;
//    private String kind;
//    private List<String> keywords;
}
