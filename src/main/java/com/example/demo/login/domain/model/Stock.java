package com.example.demo.login.domain.model;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class Stock {
    @CsvBindByName(column = "在庫ID", required = false)
    private Integer stockId;
    @CsvBindByName(column = "書籍ID", required = true)
    private Integer bookId;
    @CsvBindByName(column = "状態", required = true)
    private String state;
//    private State state;

    public Stock() {}

    public Stock(Integer bookId, String state) {
      this.bookId = bookId;
      this.state = state;
    }
}
