package com.account.service.implementation;

import com.account.dto.CategoryDto;
import com.account.entity.Category;
import com.account.mapper.MapperUtil;
import com.account.repository.CategoryRepository;
import com.account.service.CategoryService;
import com.account.service.ProductService;
import com.account.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, SecurityService securityService, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.productService = productService;
    }

    @Override
    public List<CategoryDto> findAllCategoryByCompanySorted() {

        return categoryRepository.findAllByCompanyTitle(securityService.getLoggedUserCompany())
                .stream()
                .map(category -> {
                    CategoryDto categoryDto = mapperUtil.convertToType(category, new CategoryDto());
                    categoryDto.setHasProduct(hasProduct(categoryDto.getId()));
                    return categoryDto;
                })
                .sorted(Comparator.comparing(CategoryDto::getDescription))
                .collect(Collectors.toList());

    }

    @Override
    public CategoryDto findById(Long id) {
        CategoryDto categoryDto = mapperUtil.convertToType(categoryRepository.findById(id), new CategoryDto());
        categoryDto.setHasProduct(hasProduct(categoryDto.getId()));
        return categoryDto;
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) {
        categoryDto.setCompanyDto(securityService.getLoggedInUser().getCompany());
        Category category = mapperUtil.convertToType(categoryDto, new Category());
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).get();
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);

    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).get();
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }

    private boolean hasProduct(Long categoryId) {
        return productService.findProductsByCompanyId(categoryId).size() >0;
    }
}
