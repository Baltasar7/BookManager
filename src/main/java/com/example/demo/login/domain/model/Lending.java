package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class Lending {
    private Integer lendingId;
    private Integer stockId;
		private String userId;

		public Lending() {}

		public Lending(String userId, int stockId) {
			this.stockId = stockId;
			this.userId = userId;
		}
}
