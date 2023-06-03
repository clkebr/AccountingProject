package com.account.controller;

import com.account.dto.InvoiceDto;
import com.account.dto.ProductDto;
import com.account.enums.ClientVendorType;
import com.account.enums.InvoiceType;
import com.account.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/create")
    public  String postClientVendor(@ModelAttribute("newPurchaseInvoice") InvoiceDto invoiceDto){
        invoiceService.saveInvoice(invoiceDto, InvoiceType.PURCHASE);
        return "redirect:/purchaseInvoices/list";
    }

    @GetMapping("/update/{id}")
    public  String update(@PathVariable("id") Long id, Model model){
        model.addAttribute("invoice", invoiceService.findInvoiceById(id));
        model.addAttribute("newInvoiceProduct", new ProductDto());

        return "/invoice/purchase-invoice-update";
    }
}
