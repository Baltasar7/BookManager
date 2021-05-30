package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class Stock {
	  private Integer stockId;
	  private Integer bookId;
    private State state;
}
