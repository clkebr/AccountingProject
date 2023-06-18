package com.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data @NoArgsConstructor
public class AddressDto {


    private Long id;
    @NotBlank
    @Size(max=100 , min= 2)
    private String addressLine1;

    @Size(max=100)
    private String addressLine2;

    @NotBlank
    @Size(max=50 , min= 2)
    private String city;

    @NotBlank
    @Size(max=50 , min= 2)
    private String state;
    private String country;
    @NotBlank
    @Pattern(regexp = "^\\d{5}([-]|\\s*)?(\\d{4})?$", message = "Zipcode should have a valid form.")
    private String zipCode;
}
