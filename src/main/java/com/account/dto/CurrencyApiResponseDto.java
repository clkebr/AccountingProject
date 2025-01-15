package com.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"date",
		"usd"
})
@Data
public class CurrencyApiResponseDto {

	@JsonProperty("date")
	private LocalDate date;
	@JsonProperty("usd")
	private Usd usd;


	@Data
	public static class Usd {
		private BigDecimal eur;
		private BigDecimal gbp;
		private BigDecimal inr;
		private BigDecimal jpy;
		private BigDecimal cad;

	}

}
