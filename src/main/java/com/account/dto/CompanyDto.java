package com.account.dto;

import com.account.enums.CompanyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data @NoArgsConstructor
public class CompanyDto {

    private Long id;

    @NotBlank
    @Size(max=100 , min= 2)
    private String title;

    @NotBlank
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" // +111 (202) 555-0125  +1 (202) 555-0125
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"                                  // +111 123 456 789
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", message = "type in the correct format like +111 (202) 555-0125, +1 (202) 555-0125 or +111 123 456 789")
    private String phone;

    @NotBlank
    @Size(max=100 , min= 2)
    @Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*", message = "Website should have a valid format.")
    private String website;
    @NotNull @Valid
    private AddressDto address;
    private CompanyStatus companyStatus;
}
