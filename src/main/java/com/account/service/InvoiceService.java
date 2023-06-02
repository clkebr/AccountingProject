package com.account.service;

import com.account.dto.ClientVendorDto;
import com.account.dto.InvoiceDto;
import com.account.enums.ClientVendorType;
import com.account.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDto> findAllByCompany(InvoiceType purchase);

    List<ClientVendorDto> getAllByCompanyAndType(ClientVendorType vendor);

    InvoiceDto createInvoiceDto(InvoiceType invoiceType);
}
