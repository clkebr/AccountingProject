package com.account.service.implementation;

import com.account.dto.AddressDto;
import com.account.entity.Address;
import com.account.repository.AddressRepository;
import com.account.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDto save(AddressDto object) {
        return null;
    }

    @Override
    public List<AddressDto> findAll() {
        return null;
    }

    @Override
    public AddressDto findById(Long aLong) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void update(AddressDto object) {

    }

    @Override
    public List<String> getAllCountries() {
        return addressRepository.findAll().stream()
                .map(Address::getCountry).distinct().collect(Collectors.toList());
    }
}
