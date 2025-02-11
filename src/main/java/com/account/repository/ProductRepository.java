package com.account.repository;

import com.account.entity.Company;
import com.account.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByCategoryId(Long categoryId);

	List<Product> findAllByCategoryCompany(Company company);

	Product findByNameAndCategoryCompany(String name, Company company);
}