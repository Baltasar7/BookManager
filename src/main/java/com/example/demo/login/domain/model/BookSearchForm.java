package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class BookSearchForm {
    // Not validation
    private String title;

    // Not validation
    private boolean lendable;
}
