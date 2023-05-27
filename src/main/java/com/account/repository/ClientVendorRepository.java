package com.account.repository;

import com.account.entity.ClientVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor,Long> {


    List<ClientVendor> findAllByCompanyTitle(String company);
}
