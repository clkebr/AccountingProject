package com.account.service.implementation;

import com.account.dto.InvoiceDto;
import com.account.entity.Invoice;
import com.account.enums.InvoiceType;
import com.account.mapper.MapperUtil;
import com.account.repository.InvoiceRepository;
import com.account.service.InvoiceProductService;
import com.account.service.InvoiceService;
import com.account.service.SecurityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final SecurityService securityService;
    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceProductService invoiceProductService;

    public InvoiceServiceImpl(SecurityService securityService, InvoiceRepository invoiceRepository, MapperUtil mapperUtil, InvoiceProductService invoiceProductService) {
        this.securityService = securityService;
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceProductService = invoiceProductService;
    }


    // list should be sorted by their invoice no in descending order (the latest invoices should be at the top)
    @Override
    public List<InvoiceDto> findAllByCompany(InvoiceType purchase) {
        Long companyId = securityService.getLoggedInUser().getCompany().getId();

        return invoiceRepository.findAllByCompanyId(companyId)
                .stream()
                .filter(invoice -> invoice.getInvoiceType().equals(InvoiceType.PURCHASE))
                .sorted(Comparator.comparing(Invoice::getInvoiceNo)
                        .thenComparing(Invoice::getDate))
                .map((Invoice invoice)-> {
                    InvoiceDto invoiceDto = mapperUtil.convertToType(invoice, new InvoiceDto());
                    invoiceDto.setPrice(getInvoiceTotalPrice(invoiceDto.getId()));
                    invoiceDto.setTax(getInvoiceTotalTaxOf(invoiceDto.getId()));
                    invoiceDto.setTotal(getInvoiceTotalPrice(invoiceDto.getId()).add(getInvoiceTotalTaxOf(invoiceDto.getId())));

                    return invoiceDto;
                }).collect(Collectors.toList());
    }

    private BigDecimal getInvoiceTotalPrice(Long id) {
        return invoiceProductService.findByInvoiceId(id)
                .stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf((long) p.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
    private BigDecimal getInvoiceTotalTaxOf(Long id){
        return invoiceProductService.findByInvoiceId(id).stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf(p.getQuantity() * p.getTax() /100d))
                        .setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
