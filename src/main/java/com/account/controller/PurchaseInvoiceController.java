package com.account.controller;

import com.account.enums.InvoiceType;
import com.account.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;

    public PurchaseInvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping("/list")
    public  String get(Model model){
        model.addAttribute("invoices", invoiceService.findAllByCompany(InvoiceType.PURCHASE));
        return "invoice/purchase-invoice-list";
    }
    
}
