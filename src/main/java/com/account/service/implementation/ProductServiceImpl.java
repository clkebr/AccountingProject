package com.account.service.implementation;

import com.account.dto.ProductDto;
import com.account.entity.Company;
import com.account.entity.Product;
import com.account.mapper.MapperUtil;
import com.account.repository.ProductRepository;
import com.account.service.ProductService;
import com.account.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, SecurityService securityService, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<ProductDto> findProductsByCompanyId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(product -> mapperUtil.convertToType(product,new ProductDto()))
                .collect(Collectors.toList());
    }

    //return sorted list ->category than product name
    @Override
    public List<ProductDto> getProductsByCompany() {
        Company company = mapperUtil.convertToType(securityService.getLoggedInUser().getCompany(),new Company());

        return productRepository.findAllByCategoryCompany(company)
                .stream()
                .sorted(Comparator.comparing((Product product) -> product.getCategory().getDescription())
                        .thenComparing(Product::getName))
                .map(
                        ( Product each ) -> mapperUtil.convertToType(  each, new ProductDto())

                )
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        productDto.getCategory().setCompanyDto(securityService.getLoggedInUser().getCompany());
        Product product = mapperUtil.convertToType(productDto, new Product());
        product.setQuantityInStock(0);
       return mapperUtil.convertToType( productRepository.save(product),new ProductDto());
    }

    @Override
    public ProductDto findProductById(Long id) {
        return mapperUtil.convertToType(productRepository.findById(id),new ProductDto());
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Object not found"));;
        product.setProductUnit(productDto.getProductUnit());
        product.setLowLimitAlert(productDto.getLowLimitAlert());
        return mapperUtil.convertToType(productRepository.save(product),new ProductDto());
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Object not found"));;
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    @Override
    public boolean isProductNameExist(ProductDto productDto) {
        Company actualCompany = mapperUtil.convertToType(securityService.getLoggedInUser().getCompany(), new Company());
        Product existingProduct = productRepository.findByNameAndCategoryCompany(productDto.getName(), actualCompany);
        if (existingProduct == null) return false;
        return !existingProduct.getId().equals(productDto.getId());
    }

}
