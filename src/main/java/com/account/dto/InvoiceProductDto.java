package com.account.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvoiceProductDto {
	private Long id;

	private BigDecimal price;

	private Integer tax;

	private BigDecimal total;

	private BigDecimal profitLoss;

	private Integer remainingQty;

	private InvoiceDto invoice;

	private ProductDto product;

	private Integer quantity;

}
