package com.account.repository;

import com.account.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {



    @Query(value = "SELECT c FROM Company c ORDER BY c.companyStatus")
    List<Company> findAllOrderByCompanyStatus();

    Company findByTitle(String title);
}
