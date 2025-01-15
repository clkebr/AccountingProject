package com.account.repository;

import com.account.entity.Invoice;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	List<Invoice> findAllByCompanyId(Long id);


	List<Invoice> findAllByCompanyIdAndInvoiceType(Long companyId, InvoiceType invoiceType);

	List<Invoice> findTopByCompanyIdAndInvoiceStatusOrderByDateDesc(Long companyId, InvoiceStatus invoiceStatus);
}
