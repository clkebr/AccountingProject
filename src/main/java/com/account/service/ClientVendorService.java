package com.account.service;

import com.account.dto.ClientVendorDto;

import java.util.List;

public interface ClientVendorService {
    List<ClientVendorDto> findAllByCompany();


    void save(ClientVendorDto clientVendorDto);

    ClientVendorDto findById(Long id);

    void updateClientVendor(Long id, ClientVendorDto clientVendorDto);

    void deleteById(Long id);
}
