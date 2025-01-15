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
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

	private final InvoiceService invoiceService;
	private final ProductService productService;

	private final SecurityService securityService;

	public PurchaseInvoiceController(InvoiceService invoiceService, ProductService productService, SecurityService securityService) {
		this.invoiceService = invoiceService;
		this.productService = productService;
		this.securityService = securityService;
	}


	@GetMapping("/list")
	public String getPurchaseInvoices(Model model) {
		model.addAttribute("invoices", invoiceService.findAllByCompany(InvoiceType.PURCHASE));
		return "invoice/purchase-invoice-list";
	}

	@GetMapping("/create")
	public String createPurchaseInvoice(Model model) {
		model.addAttribute("newPurchaseInvoice", invoiceService.createInvoiceDto(InvoiceType.PURCHASE));
		model.addAttribute("vendors", invoiceService.getAllByCompanyAndType(ClientVendorType.VENDOR));
		return "/invoice/purchase-invoice-create";
	}

	@PostMapping("/create")
	public String postPurchaseInvoice(@ModelAttribute("newPurchaseInvoice") InvoiceDto invoiceDto) {
		InvoiceDto dto = invoiceService.saveInvoice(invoiceDto, InvoiceType.PURCHASE);
		return "redirect:/purchaseInvoices/update/" + dto.getId();
	}

	@GetMapping("/update/{id}")
	public String retrievePurchaseInvoice(@PathVariable("id") Long id, Model model) {

		model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
		model.addAttribute("invoice", invoiceService.findInvoiceById(id));
		model.addAttribute("products", productService.getProductsByCompany());
		model.addAttribute("vendors", invoiceService.getAllByCompanyAndType(ClientVendorType.VENDOR));
		model.addAttribute("invoiceProducts", invoiceService.getInvoiceProductsByInvoiceId(id));

		return "/invoice/purchase-invoice-update";
	}

	@PostMapping("/update/{id}")
	public String updatePurchaseInvoice(@ModelAttribute("newPurchaseInvoice") InvoiceDto invoiceDto) {

		InvoiceDto dto = invoiceService.updateInvoice(invoiceDto, InvoiceType.PURCHASE);

		return "redirect:/purchaseInvoices/update/" + dto.getId();
	}

	@GetMapping("/print/{id}")
	public String printPurchaseInvoice(@PathVariable("id") Long id, Model model) {
		model.addAttribute("invoice", invoiceService.findInvoiceById(id));
		model.addAttribute("invoiceProducts", invoiceService.getInvoiceProductsByInvoiceId(id));
		model.addAttribute("company", securityService.getLoggedInUser().getCompany());
		return "/invoice/invoice_print";
	}

	@GetMapping("/delete/{id}")
	public String deletePurchaseInvoice(@PathVariable("id") Long id) {
		invoiceService.deleteById(id);

		return "redirect:/purchaseInvoices/list";
	}

	@PostMapping("/addInvoiceProduct/{id}")
	public String addInvoiceProduct(@PathVariable("id") Long id, @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto) {

		invoiceService.addInvoiceProduct(id, invoiceProductDto);

		return "redirect:/purchaseInvoices/update/" + id;
	}

	@GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
	public String removeInvoiceProduct(@PathVariable("invoiceId") Long invoiceId, @PathVariable("invoiceProductId") Long invoiceProductId) {
		invoiceService.removeInvoiceProductById(invoiceProductId);
		return "redirect:/purchaseInvoices/update/" + invoiceId;
	}

	@GetMapping("/approve/{id}")
	public String approvePurchaseInvoice(@PathVariable("id") Long id) {
		invoiceService.approvePurchaseInvoice(id);
		return "redirect:/purchaseInvoices/list";
	}

}
