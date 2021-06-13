package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class Stock {
	  private Integer stockId;
	  private Integer bookId;
    private String state;
//    private State state;

    public Stock() {}

    public Stock(Integer bookId, String state) {
    	this.bookId = bookId;
    	this.state = state;
    }
}
