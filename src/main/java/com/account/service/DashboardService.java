package com.account.service;

import com.account.dto.InvoiceDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DashboardService {
    Map<String, BigDecimal> getSumOfTotal();

    List<InvoiceDto> getLast3Transaction();
}
