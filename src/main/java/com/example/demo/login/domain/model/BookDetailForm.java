package com.example.demo.login.domain.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class BookDetailForm {
    private String bookId;

    @NotBlank(groups = ValidGroup1.class)
    private String title;

    @NotBlank(groups = ValidGroup1.class)
    private String author;

    @NotBlank(groups = ValidGroup1.class)
    private String publisher;

    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^[0-9]+$", groups = ValidGroup2.class)
    @Min(value = 0 , groups = ValidGroup3.class)
    private String stock;

    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^[0-9]+$", groups = ValidGroup2.class)
    @Min(value = 0 , groups = ValidGroup3.class)
    private String rest;
}
