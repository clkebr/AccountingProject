package com.account.service.implementation;

import com.account.converter.CompanyDtoConverter;
import com.account.dto.CompanyDto;
import com.account.entity.Company;
import com.account.enums.CompanyStatus;
import com.account.mapper.MapperUtil;
import com.account.repository.CompanyRepository;
import com.account.service.CompanyService;
import com.account.service.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private  final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    private final SecurityService securityService;

    private final CompanyDtoConverter companyDtoConverter;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil, SecurityService securityService, CompanyDtoConverter companyDtoConverter) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.companyDtoConverter = companyDtoConverter;
    }

    @Override
    public CompanyDto save(CompanyDto dto) {
        Company company = mapperUtil.convertToType(dto, new Company());

        company.setCompanyStatus(CompanyStatus.PASSIVE);
        company.setInsertDateTime(LocalDateTime.now());
        company.setLastUpdateDateTime(LocalDateTime.now());
        company.setInsertUserId(securityService.getLoggedInUser().getId());
        company.setLastUpdateUserId(securityService.getLoggedInUser().getId());
        company.getAddress().setLastUpdateDateTime(LocalDateTime.now());
        company.getAddress().setInsertDateTime(LocalDateTime.now());
        company.getAddress().setInsertUserId(securityService.getLoggedInUser().getId());
        company.getAddress().setLastUpdateUserId(securityService.getLoggedInUser().getId());
        companyRepository.save(company);

        return mapperUtil.convertToType(companyRepository.findByTitle(dto.getTitle()),new CompanyDto());
    }

    @Override
    public List<CompanyDto> findCompanies() {
        List<Company> companyList;

        if(securityService.isCurrentUserRoot())
            companyList = companyRepository.findAll();
        else {
            companyList = Collections.singletonList(companyRepository.findByTitle(securityService.getLoggedUserCompany()));

        }
        return companyList.stream()
                .map(company-> mapperUtil.convertToType(company,new CompanyDto()))
                .filter(company->company.getId() !=1)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyDto> findAll() {
//        id=1 root company
        return companyRepository.findAllOrderByCompanyStatus().stream()
                .filter(each->each.getId() !=1)
                .map(each-> mapperUtil.convertToType(each,new CompanyDto())).collect(Collectors.toList());
    }

    @Override
    public CompanyDto findById(Long companyId) {
        return mapperUtil.convertToType(companyRepository.findById(companyId),new CompanyDto());
    }
//todo: change the impl
    @Override
    public void updateCompany(String id, CompanyDto companyDto) {

        Company companyToBeSaved = mapperUtil.convertToType(companyDto, new Company());

        Company company = companyRepository.findById(companyToBeSaved.getId()).get();

        company.setTitle(companyToBeSaved.getTitle());
        company.setPhone(companyToBeSaved.getPhone());
        company.setWebsite(companyToBeSaved.getWebsite());
        company.setLastUpdateDateTime(LocalDateTime.now());

        company.getAddress().setAddressLine1(companyToBeSaved.getAddress().getAddressLine1());
        company.getAddress().setAddressLine2(companyToBeSaved.getAddress().getAddressLine2());
        company.getAddress().setCity(companyToBeSaved.getAddress().getCity());
        company.getAddress().setState(companyToBeSaved.getAddress().getState());
        company.getAddress().setCountry(companyToBeSaved.getAddress().getCountry());
        company.getAddress().setZipCode(companyToBeSaved.getAddress().getZipCode());
        company.getAddress().setLastUpdateDateTime(LocalDateTime.now());
        companyRepository.save(company);
    }

    @Override
    public void deactivateCompanyStatus(Long id) {
       Company company= companyRepository.findById(id).get();

//       todo: deactivate company users as well
       company.setCompanyStatus(CompanyStatus.PASSIVE);
       company.setLastUpdateDateTime(LocalDateTime.now());

       companyRepository.save(company);
    }

    @Override
    public void activateCompanyStatus(Long id) {
        Company company= companyRepository.findById(id).get();

        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setLastUpdateDateTime(LocalDateTime.now());

//        todo: activate company users as well

        companyRepository.save(company);
    }
}
