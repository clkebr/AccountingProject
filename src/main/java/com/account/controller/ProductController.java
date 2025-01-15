package com.account.controller;

import com.account.dto.ProductDto;
import com.account.enums.ProductUnit;
import com.account.service.CategoryService;
import com.account.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;

	public ProductController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping("/list")
	public String getClientVendorList(Model model) {
		model.addAttribute("products", productService.getProductsByCompany());
		return "/product/product-list";

	}

	@GetMapping("/create")
	public String createProduct(Model model) {
		model.addAttribute("newProduct", new ProductDto());
		model.addAttribute("categories", categoryService.findAllCategoryByCompanySorted());
		model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
		return "/product/product-create";
	}

	@PostMapping("/create")
	public String postProduct(@Valid @ModelAttribute("newProduct") ProductDto productDto, BindingResult bindingResult, Model model) {
		if (productService.isProductNameExist(productDto)) {
			bindingResult.rejectValue("name", " ", "This Product Name already exists.");
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.findAllCategoryByCompanySorted());
			model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));

			return "/product/product-create";
		}
		productService.save(productDto);
		return "redirect:/products/list";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.findProductById(id));
		model.addAttribute("categories", categoryService.findAllCategoryByCompanySorted());
		model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
		return "/product/product-update";
	}

	@PostMapping("/update/{id}")
	public String save(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult, Model model) {
		if (productService.isProductNameExist(productDto)) {
			bindingResult.rejectValue("name", " ", "This Product Name already exists.");
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.findAllCategoryByCompanySorted());
			model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));

			return "/product/product-update";
		}
		productService.updateProduct(id, productDto);
		return "redirect:/products/list";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		productService.deleteById(id);

		return "redirect:/products/list";
	}
}
