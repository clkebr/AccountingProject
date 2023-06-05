package com.account.service.implementation;

import com.account.dto.InvoiceProductDto;
import com.account.entity.InvoiceProduct;
import com.account.mapper.MapperUtil;
import com.account.repository.InvoiceProductRepository;
import com.account.service.InvoiceProductService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
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
                .map( (InvoiceProduct invoiceProduct) ->{

                    InvoiceProductDto invoiceProductDto = mapperUtil.convertToType(invoiceProduct, new InvoiceProductDto());
                    invoiceProductDto.setTotal(calculateTotal(invoiceProductDto));
                    return invoiceProductDto;

                })
                .collect(Collectors.toList());
    }

    private BigDecimal calculateTotal(InvoiceProductDto invoiceProductDto) {
        return invoiceProductDto.getPrice().multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity()));
    }

    @Override
    public List<InvoiceProductDto> findInvoiceProductByInvoiceId(Long id) {
        return invoiceProductRepository.findAllByInvoiceId(id)
                .stream()
                .map( (InvoiceProduct invoiceProduct) ->{

                    InvoiceProductDto invoiceProductDto = mapperUtil.convertToType(invoiceProduct, new InvoiceProductDto());
                    invoiceProductDto.setTotal(calculateTotal(invoiceProductDto));
                    return invoiceProductDto;

                })
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDto findInvoiceProductById(long id) {
        InvoiceProductDto invoiceProductDto = mapperUtil.convertToType(invoiceProductRepository.findById(id), new InvoiceProductDto());
        invoiceProductDto.setTotal(calculateTotal(invoiceProductDto));
        return invoiceProductDto;

    }
//todo: check invoiceProductDto´s total whether is empty or not
    @Override
    public InvoiceProductDto saveInvoiceProductDto(InvoiceProductDto invoiceProductDto) {
        InvoiceProduct save = invoiceProductRepository.save(mapperUtil.convertToType(invoiceProductDto, new InvoiceProduct()));
        return mapperUtil.convertToType(save, new InvoiceProductDto());
    }

    @Override
    public void deleteInvoiceProductById(Long invoiceProductId) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductId).orElseThrow(()-> new EntityNotFoundException("InvoiceProduct is not found"));
        invoiceProduct.setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct);

    }


}
