package com.example.demo.login.domain.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BookRegistForm {
    private String bookId;

    @NotBlank(groups = ValidGroup1.class)
    private String title;

    @NotBlank(groups = ValidGroup1.class)
    private String author;

    @NotBlank(groups = ValidGroup1.class)
    private String publisher;
}
