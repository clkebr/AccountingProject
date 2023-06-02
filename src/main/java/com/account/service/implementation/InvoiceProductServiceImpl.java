package com.account.service.implementation;

import com.account.dto.InvoiceProductDto;
import com.account.mapper.MapperUtil;
import com.account.repository.InvoiceProductRepository;
import com.account.service.InvoiceProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {
    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<InvoiceProductDto> findByInvoiceId(Long id) {

         return invoiceProductRepository.findByInvoiceId(id)
                .stream()
                .map(each -> mapperUtil.convertToType(each, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }


}
