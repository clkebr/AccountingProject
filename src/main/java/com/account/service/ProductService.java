package com.account.service;

import com.account.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findProductsByCompanyId(Long categoryId);

    List<ProductDto> getProductsByCompany();

    void save(ProductDto productDto);
}


