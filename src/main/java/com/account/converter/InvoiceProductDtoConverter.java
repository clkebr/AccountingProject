package com.account.converter;

import com.account.dto.InvoiceProductDto;
import com.account.service.InvoiceProductService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class InvoiceProductDtoConverter implements Converter<String, InvoiceProductDto> {
    private final InvoiceProductService invoiceProductService;

    public InvoiceProductDtoConverter(@Lazy InvoiceProductService invoiceProductService) {

        this.invoiceProductService = invoiceProductService;
    }

    @SneakyThrows
    @Override
    public InvoiceProductDto convert(String id){
        return invoiceProductService.findInvoiceProductById(Long.parseLong(id));
    }

}