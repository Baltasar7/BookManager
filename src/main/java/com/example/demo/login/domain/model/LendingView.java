package com.example.demo.login.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LendingView {
    private Integer lendingId;
    private Integer stockId;
    private String title;
    private String userName;
    private String state;
    private LocalDate lendingDate;
    private LocalDate limitDate;
}
