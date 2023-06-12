package com.account.service.implementation;

import com.account.dto.InvoiceDto;
import com.account.dto.InvoiceProductDto;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import com.account.service.DashboardService;
import com.account.service.InvoiceProductService;
import com.account.service.InvoiceService;
import com.account.service.SecurityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final InvoiceProductService invoiceProductService;
    private final InvoiceService invoiceService;
    private final SecurityService securityService;

    public DashboardServiceImpl(InvoiceProductService invoiceProductService, InvoiceService invoiceService, SecurityService securityService) {
        this.invoiceProductService = invoiceProductService;
        this.invoiceService = invoiceService;
        this.securityService = securityService;
    }

    @Override
    public Map<String, BigDecimal> getSumOfTotal() {

        Map<String,BigDecimal> map = new HashMap<>();
        map.put("totalCost", calculateTotalCost());
        map.put("totalSales",calculateTotalSale());
        map.put("profitLoss",calculateTotalProfitLoss());
        return map;
    }

    @Override
    public List<InvoiceDto> getLast3Transaction() {

        return invoiceService.getLast3TransactionByStatus( InvoiceStatus.APPROVED);
    }

    private BigDecimal calculateTotalProfitLoss() {
        Long companyId = securityService.getLoggedInUser().getCompany().getId();
        List<InvoiceProductDto> allApprovedSalesByCompany = invoiceProductService.findAllByInvoiceTypeAndInvoiceStatusAndCompanyId(InvoiceType.SALES, InvoiceStatus.APPROVED, companyId);

        return allApprovedSalesByCompany.stream()
                .map(InvoiceProductDto::getProfitLoss)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalSale() {
        Long companyId = securityService.getLoggedInUser().getCompany().getId();
        List<InvoiceProductDto> allApprovedSalesByCompany = invoiceProductService.findAllByInvoiceTypeAndInvoiceStatusAndCompanyId(InvoiceType.SALES, InvoiceStatus.APPROVED, companyId);

        return allApprovedSalesByCompany.stream()
                .map(each -> (each.getPrice().multiply(BigDecimal.valueOf(1 + (each.getTax() / 100)))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalCost() {
        Long companyId = securityService.getLoggedInUser().getCompany().getId();
        List<InvoiceProductDto> allApprovedByCompany = invoiceProductService.findAllByInvoiceTypeAndInvoiceStatusAndCompanyId(InvoiceType.PURCHASE, InvoiceStatus.APPROVED, companyId);

        return allApprovedByCompany.stream()
                .map(each -> (each.getPrice().multiply(BigDecimal.valueOf(1 + (each.getTax() / 100)))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
