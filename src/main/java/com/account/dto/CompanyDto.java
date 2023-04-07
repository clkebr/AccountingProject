package com.account.dto;

import com.account.enums.CompanyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class CompanyDto {

    private Long id;
    private String title;
    private String phone;
    private String website;
    private AddressDto address;
    private CompanyStatus companyStatus;
}
