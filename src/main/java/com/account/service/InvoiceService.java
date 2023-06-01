package com.account.service;

import com.account.dto.InvoiceDto;
import com.account.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDto> findAllByCompany(InvoiceType purchase);
}
