package com.example.demo.login.domain.model;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import lombok.Data;

@Data
public class Lending {
	  @CsvBindByName(column = "貸出ID", required = false)
    private Integer lendingId;
	  @CsvBindByName(column = "在庫ID", required = true)
    private Integer stockId;
	  @CsvBindByName(column = "ユーザID", required = true)
		private String userId;
	  @CsvCustomBindByName(column = "貸出日", required = false, converter = LendingDateConverter.class)
		private LocalDate lendingDate;

		public Lending() {}

		public Lending(String userId, int stockId) {
			this.stockId = stockId;
			this.userId = userId;
		}
}
