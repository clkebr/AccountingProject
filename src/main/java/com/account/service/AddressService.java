package com.account.service;

import com.account.dto.AddressDto;

import java.util.List;

public interface AddressService extends CrudService<AddressDto, Long> {
    List<String> getAllCountries();
}
