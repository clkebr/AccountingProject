package com.account.service;

import com.account.dto.CompanyDto;

public interface CompanyService extends CrudService<CompanyDto,Long>{

    void updateCompany(String id, CompanyDto companyDto);

    void deactivateCompanyStatus(Long id);

    void activateCompanyStatus(Long id);
}
