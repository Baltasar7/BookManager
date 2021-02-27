package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class User {
    private String userId; //ユーザーID
    private String password; //パスワード
    private String userName; //ユーザー名
    private String department; //所属部署
    private String role; //ロール
}
