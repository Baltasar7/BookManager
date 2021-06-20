package com.example.demo.login.domain.model;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class User {
    @CsvBindByName(column = "ユーザID", required = true)
    private String userId;
    @CsvBindByName(column = "パスワード", required = true)
    private String password;
    @CsvBindByName(column = "ユーザ名", required = true)
    private String userName;
    @CsvBindByName(column = "所属", required = true)
    private String department;
    @CsvBindByName(column = "役割", required = true)
    private String role;
}
