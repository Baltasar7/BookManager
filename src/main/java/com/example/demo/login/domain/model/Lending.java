package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class Lending {
    private Integer lendingId;
		private String userId;
    private Integer bookId;
}
