package com.account.service;

import com.account.dto.InvoiceProductDto;

import java.util.List;
import java.util.Map;

public interface ReportingService {
    List<InvoiceProductDto> getStockData();

    Map<String, Integer> getMonthlyProfitLossDataMap();
}
