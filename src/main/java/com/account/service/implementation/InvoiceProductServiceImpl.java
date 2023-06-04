package com.account.service.implementation;

import com.account.dto.InvoiceProductDto;
import com.account.entity.InvoiceProduct;
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

    @Override
    public List<InvoiceProductDto> findInvoiceProductByInvoiceId(Long id) {
        return invoiceProductRepository.findAllByInvoiceId(id)
                .stream()
                .map(entity->mapperUtil.convertToType(entity, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDto findInvoiceProductById(long id) {
        return mapperUtil.convertToType(invoiceProductRepository.findById(id), new InvoiceProductDto());
    }

    @Override
    public InvoiceProductDto saveInvoiceProductDto(InvoiceProductDto invoiceProductDto) {
        InvoiceProduct save = invoiceProductRepository.save(mapperUtil.convertToType(invoiceProductDto, new InvoiceProduct()));
        return mapperUtil.convertToType(save, new InvoiceProductDto());
    }


}
