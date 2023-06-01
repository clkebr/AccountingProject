package com.account.service;

import com.account.dto.InvoiceProductDto;

import java.util.List;

public interface InvoiceProductService {
    List<InvoiceProductDto> findByInvoiceId(Long id);

}
