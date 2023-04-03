package com.account.entity;

import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor
@Table(name = "invoices")
public class Invoice extends BaseEntity{

    String invoiceNo;

    @Enumerated(EnumType.STRING)
    InvoiceStatus invoiceStatus;

    @Enumerated(EnumType.STRING)
    InvoiceType invoiceType;

    @Column(columnDefinition = "DATE")
    LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_vendor_id")
    ClientVendor clientVendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    Company company;


}
