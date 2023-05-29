package com.account.service.implementation;

import com.account.entity.Product;
import com.account.repository.ProductRepository;
import com.account.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findProductsByCompanyId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
