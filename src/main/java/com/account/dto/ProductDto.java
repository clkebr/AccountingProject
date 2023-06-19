package com.account.dto;

import com.account.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    @NotBlank(message = "Product Name is required field.")
    @Size(max = 100, min = 2, message = " Product NAme must be between 2 and 100 characters long")
    private String name;

    private Integer quantityInStock;

    @NotNull(message = "Low Limit Alert is required field")
    @Range(min = 1, message = "Low limit Alert should be alt least 1.")
    private Integer lowLimitAlert;

    @NotNull(message = "please select Product Unit")
    private ProductUnit productUnit;

    @NotNull(message = "Please select a Category")
    private CategoryDto category;

}
