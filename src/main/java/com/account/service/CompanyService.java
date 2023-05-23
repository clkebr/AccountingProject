package com.account.service;

import com.account.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    void updateCompany(String id, CompanyDto companyDto);

    void deactivateCompanyStatus(Long id);

    void activateCompanyStatus(Long id);

    List<CompanyDto> findAll();

    CompanyDto findById(Long id);

    CompanyDto save(CompanyDto companyDto);
}
