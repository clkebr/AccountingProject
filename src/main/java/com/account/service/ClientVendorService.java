package com.account.service;

import com.account.dto.ClientVendorDto;

import java.util.List;

public interface ClientVendorService {
    List<ClientVendorDto> findAllByCompany();


}
