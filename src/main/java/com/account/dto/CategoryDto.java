package com.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String description;
    private CompanyDto companyDto;
    private boolean hasProduct;
}
