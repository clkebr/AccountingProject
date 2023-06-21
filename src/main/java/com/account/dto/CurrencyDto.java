package com.account.dto;


import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyDto {

    private BigDecimal euro;
    private BigDecimal britishPound;
    private BigDecimal indianRupee;
    private BigDecimal japaneseYen;
    private BigDecimal canadianDollar;

}
