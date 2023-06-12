package com.account.controller;

import com.account.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String get(Model model){

        model.addAttribute("summaryNumbers",dashboardService.getSumOfTotal() );
        model.addAttribute("invoices", dashboardService.getLast3Transaction());
//        model.addAttribute("exchangeRates",client.getCurrency());

        return "dashboard_final";
    }
}
