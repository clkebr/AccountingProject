package com.account.controller;

import com.account.enums.ClientVendorType;
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
    public  String getPurchaseInvoices(Model model){
        model.addAttribute("invoices", invoiceService.findAllByCompany(InvoiceType.PURCHASE));
        return "invoice/purchase-invoice-list";
    }
    @GetMapping("/create")
    public String createPurchaseInvoice(Model model){
        model.addAttribute("newPurchaseInvoice", invoiceService.createInvoiceDto(InvoiceType.PURCHASE));
        model.addAttribute("vendors",invoiceService.getAllByCompanyAndType(ClientVendorType.VENDOR));
        return "/invoice/purchase-invoice-create";
    }
}
