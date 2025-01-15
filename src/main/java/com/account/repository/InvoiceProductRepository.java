package com.account.repository;

import com.account.entity.InvoiceProduct;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {
	List<InvoiceProduct> findByInvoiceId(Long id);

	List<InvoiceProduct> findAllByInvoiceId(Long id);

	List<InvoiceProduct> findAllByInvoiceInvoiceStatus(InvoiceStatus status);

	List<InvoiceProduct> findAllByInvoiceInvoiceType(InvoiceType type);

	List<InvoiceProduct> findAllByInvoiceInvoiceTypeAndInvoiceInvoiceStatusAndInvoiceCompanyId(InvoiceType invoiceType, InvoiceStatus invoiceStatus, Long id);
}
