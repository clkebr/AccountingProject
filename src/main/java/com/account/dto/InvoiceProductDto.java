package com.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class InvoiceProductDto {
    private Long id;

    private BigDecimal price ;

    private Integer tax ;

    private BigDecimal total;

    private BigDecimal profitLoss;

    private Integer remainingQty;

    private InvoiceDto invoice;

    private ProductDto product;

    private Integer quantity;

}
