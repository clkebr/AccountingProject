package com.account.service.implementation;

import com.account.dto.ClientVendorDto;
import com.account.entity.Address;
import com.account.entity.ClientVendor;
import com.account.mapper.MapperUtil;
import com.account.repository.ClientVendorRepository;
import com.account.service.ClientVendorService;
import com.account.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {
    private  final ClientVendorRepository clientVendorRepository;
    private  final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public List<ClientVendorDto> findAllByCompany() {
        return clientVendorRepository.findAllByCompanyTitle(securityService.getLoggedUserCompany()).stream()
                .map(entity -> mapperUtil.convertToType(entity,new ClientVendorDto()))
                .sorted(Comparator.comparing(ClientVendorDto::getClientVendorName))
                .sorted(Comparator.comparing(clientVendor -> clientVendor.getClientVendorType().name()))
                .collect(Collectors.toList());


    }

    @Override
    public void save(ClientVendorDto clientVendorDto) {
        clientVendorDto.setCompany(securityService.getLoggedInUser().getCompany());
        ClientVendor clientVendor = mapperUtil.convertToType(clientVendorDto, new ClientVendor());

                clientVendorRepository.save(clientVendor);

    }

    @Override
    public ClientVendorDto findById(Long id) {
        return mapperUtil.convertToType(clientVendorRepository.findById(id),new ClientVendorDto());
    }

    @Override
    public void updateClientVendor(Long id, ClientVendorDto clientVendorDto) {
        clientVendorDto.setCompany(securityService.getLoggedInUser().getCompany());
        ClientVendor byId = clientVendorRepository.findById(id).get();
        byId.setAddress(mapperUtil.convertToType(clientVendorDto.getAddress(),new Address()));
        byId.setPhone(clientVendorDto.getPhone());
        byId.setClientVendorName(clientVendorDto.getClientVendorName());
        byId.setClientVendorType(clientVendorDto.getClientVendorType());
        byId.setWebsite(clientVendorDto.getWebsite());
        clientVendorRepository.save(byId);
    }

    @Override
    public void deleteById(Long id) {
        ClientVendor clientVendor = clientVendorRepository.findById(id).get();
        clientVendor.setIsDeleted(true);
        clientVendorRepository.save(clientVendor);
    }
}


















