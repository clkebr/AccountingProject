package com.account.service.implementation;

import com.account.dto.InvoiceProductDto;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import com.account.service.InvoiceProductService;
import com.account.service.ReportingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ReportingServiceImpl implements ReportingService {
    private final InvoiceProductService invoiceProductService;

    public ReportingServiceImpl(InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public List<InvoiceProductDto> getStockData() {
        return invoiceProductService.findInvoiceProductByInvoiceStatus(InvoiceStatus.APPROVED);
    }

    @Override
    public Map<String, Integer> getMonthlyProfitLossDataMap() {
        Map<String, Integer> profitLossDataMap = new TreeMap<>();
        List<InvoiceProductDto> invoiceProductDtoList = invoiceProductService.findInvoiceProductByInvoiceType(InvoiceType.SALES);
        invoiceProductDtoList.forEach((InvoiceProductDto invoiceProductDto) -> {
                    int year = invoiceProductDto.getInvoice().getDate().getYear();
                    String month = invoiceProductDto.getInvoice().getDate().getMonth().toString();
                    BigDecimal profitLoss = invoiceProductDto.getProfitLoss();
                    String timeWindow = year + " " + month;
                    profitLossDataMap.put(timeWindow, profitLossDataMap.getOrDefault(timeWindow, 0) + profitLoss.intValue());

                });
        return profitLossDataMap;
    }
}
