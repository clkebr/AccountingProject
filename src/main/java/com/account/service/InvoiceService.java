package com.account.service;

import com.account.dto.ClientVendorDto;
import com.account.dto.InvoiceDto;
import com.account.dto.InvoiceProductDto;
import com.account.enums.ClientVendorType;
import com.account.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDto> findAllByCompany(InvoiceType invoiceType);

    List<ClientVendorDto> getAllByCompanyAndType(ClientVendorType vendor);

    InvoiceDto createInvoiceDto(InvoiceType invoiceType);

    InvoiceDto saveInvoice(InvoiceDto invoiceDto, InvoiceType invoiceType);

    InvoiceDto findInvoiceById(Long id);

    List<InvoiceProductDto> getInvoiceProductsByInvoiceId(Long invoiceId);

    InvoiceDto updateInvoice(InvoiceDto invoiceDto, InvoiceType type);

    void deleteById(Long id);

    InvoiceProductDto addInvoiceProduct(Long invoiceId, InvoiceProductDto invoiceProductDto);

    void removeInvoiceProductById(Long invoiceProductId);

    void approvePurchaseInvoice(Long id);
}
