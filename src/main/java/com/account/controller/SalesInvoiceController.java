package com.account.controller;

import com.account.dto.InvoiceDto;
import com.account.dto.InvoiceProductDto;
import com.account.enums.ClientVendorType;
import com.account.enums.InvoiceType;
import com.account.service.InvoiceService;
import com.account.service.ProductService;
import com.account.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {
    private final InvoiceService invoiceService;
    private final SecurityService securityService;

    private final ProductService productService;

    public SalesInvoiceController(InvoiceService invoiceService, SecurityService securityService, ProductService productService) {
        this.invoiceService = invoiceService;
        this.securityService = securityService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public  String getSalesInvoices(Model model){
        model.addAttribute("invoices", invoiceService.findAllByCompany(InvoiceType.SALES));
        return "invoice/sales-invoice-list";
    }

    @GetMapping("/create")
    public String createSalesInvoice(Model model){
        model.addAttribute("newSalesInvoice", invoiceService.createInvoiceDto(InvoiceType.SALES));
        model.addAttribute("clients",invoiceService.getAllByCompanyAndType(ClientVendorType.CLIENT));
        return "/invoice/sales-invoice-create";
    }

    @PostMapping("/create")
    public  String postSalesInvoice(@ModelAttribute("newSalesInvoice") InvoiceDto invoiceDto){
        invoiceService.saveInvoice(invoiceDto, InvoiceType.SALES);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/print/{id}")
    public  String printPurchaseInvoice(@PathVariable("id") Long id, Model model){
        model.addAttribute("invoice", invoiceService.findInvoiceById(id));
        model.addAttribute("invoiceProducts",invoiceService.getInvoiceProductsByInvoiceId(id));
        model.addAttribute("company", securityService.getLoggedInUser().getCompany());
        return "/invoice/invoice_print";
    }

    @GetMapping("/update/{id}")
    public  String retrieveSalesInvoice(@PathVariable("id") Long id, Model model){

        model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
        model.addAttribute("invoice", invoiceService.findInvoiceById(id));
        model.addAttribute("products", productService.getProductsByCompany());
        model.addAttribute("clients",invoiceService.getAllByCompanyAndType(ClientVendorType.CLIENT));
        model.addAttribute("invoiceProducts",invoiceService.getInvoiceProductsByInvoiceId(id));

        return "/invoice/sales-invoice-update";
    }

    @PostMapping("/update/{id}")
    public  String updateSalesInvoice( @ModelAttribute("newInvoiceProduct") InvoiceDto invoiceDto){
        InvoiceDto dto = invoiceService.updateInvoice(invoiceDto, InvoiceType.SALES);
        return "redirect:/salesInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public  String addSalesProduct(@PathVariable("id") Long id, @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto){

        invoiceService.addInvoiceProduct(id, invoiceProductDto);

        return "redirect:/salesInvoices/update/"+id;
    }

    @GetMapping("/delete/{id}")
    public String deleteSalesInvoice(@PathVariable("id") Long id){
        invoiceService.deleteById(id);

        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/approve/{id}")
    public  String approveSalesInvoice(@PathVariable("id") Long id){
        invoiceService.approvePurchaseInvoice(id);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
    public  String removeInvoiceProduct(@PathVariable("invoiceId") Long invoiceId,@PathVariable("invoiceProductId") Long invoiceProductId){
        invoiceService.removeInvoiceProductById(invoiceProductId);
        return "redirect:/salesInvoices/update/"+invoiceId;
    }
}
