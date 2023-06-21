package com.account.converter;

import com.account.service.CompanyService;
import com.account.dto.CompanyDto;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CompanyDtoConverter implements Converter<String, CompanyDto> {

    private final CompanyService companyService;

    public CompanyDtoConverter(@Lazy CompanyService companyService) {
        this.companyService = companyService;
    }

    @SneakyThrows
    @Override
    public CompanyDto convert(String id){
        return companyService.findById(Long.parseLong(id));
    }

}