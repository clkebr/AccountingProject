package com.account.repository;

import com.account.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {
    List<InvoiceProduct> findByInvoiceId(Long id);
}