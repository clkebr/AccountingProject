package com.account.dto;

import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class InvoiceDto {

    private Long id;

    private LocalDate date;

    private String invoiceNo;

    private InvoiceStatus invoiceStatus;

    private InvoiceType invoiceType;

    private ClientVendorDto clientVendor;

    private CompanyDto companyDto;

    private BigDecimal price ;

    private BigDecimal tax;

    private  BigDecimal total ;

    private List<InvoiceProductDto> invoiceProducts;



}
