package com.account.service;

import com.account.dto.ProductDto;

import java.util.List;

public interface ProductService {
	List<ProductDto> findProductsByCompanyId(Long categoryId);

	List<ProductDto> getProductsByCompany();

	ProductDto save(ProductDto productDto);

	ProductDto findProductById(Long id);

	ProductDto updateProduct(Long id, ProductDto productDto);

	void deleteById(Long id);


	boolean isProductNameExist(ProductDto productDto);
}


