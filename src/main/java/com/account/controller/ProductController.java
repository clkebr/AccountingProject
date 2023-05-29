package com.account.controller;

import com.account.dto.ProductDto;
import com.account.enums.ProductUnit;
import com.account.service.CategoryService;
import com.account.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getClientVendorList(Model model){
        model.addAttribute("products",productService.getProductsByCompany());
        return "/product/product-list";

    }
    @GetMapping("/create")
    public String createProduct(Model model){
        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.findAllCategoryByCompanySorted());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
        return "/product/product-create";
    }

    @PostMapping("/create")
    public  String postProduct(@ModelAttribute("newProduct") ProductDto productDto){
        productService.save(productDto);
        return "redirect:/products/list";
    }

}
