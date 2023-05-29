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
    public void save(ProductDto productDto) {
        Product product = mapperUtil.convertToType(productDto, new Product());
        product.setQuantityInStock(0);
        productRepository.save(product);
    }

}
