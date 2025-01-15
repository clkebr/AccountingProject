package com.account.service;

import com.account.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
	List<CategoryDto> findAllCategoryByCompanySorted();

	CategoryDto findById(Long id);

	void saveCategory(CategoryDto categoryDto);

	void updateCategory(CategoryDto categoryDto);

	void deleteById(Long id);
}
