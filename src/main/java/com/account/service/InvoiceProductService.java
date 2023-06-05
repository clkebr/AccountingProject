package com.account.service;

import com.account.dto.InvoiceProductDto;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;

import java.util.List;

public interface InvoiceProductService {
    List<InvoiceProductDto> findByInvoiceId(Long id);

    List<InvoiceProductDto> findInvoiceProductByInvoiceId(Long id);

    InvoiceProductDto findInvoiceProductById(long parseLong);

    InvoiceProductDto saveInvoiceProductDto(InvoiceProductDto invoiceProductDto);

    void deleteInvoiceProductById(Long invoiceProductId);

    List<InvoiceProductDto> findInvoiceProductByInvoiceStatus(InvoiceStatus approved);

    List<InvoiceProductDto> findInvoiceProductByInvoiceType(InvoiceType type);
}
