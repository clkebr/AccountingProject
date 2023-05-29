package com.account.controller;

import com.account.dto.CategoryDto;
import com.account.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getCategoryList(Model model){
        model.addAttribute("categories", categoryService.findAllCategoryByCompanySorted());
        return "/category/category-list";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("newCategory", new CategoryDto());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public  String postCategory(@ModelAttribute("newCategory") CategoryDto categoryDto){
        categoryService.saveCategory(categoryDto);
        return "redirect:/categories/list";
    }

    @GetMapping("/update/{id}")
    public  String updateCategory(@PathVariable Long id, Model model){
        model.addAttribute("category", categoryService.findById(id));
        return "/category/category-update";
    }
    @PostMapping("/update/{id}")
    public  String saveCategory( @ModelAttribute("category") CategoryDto categoryDto){
        categoryService.updateCategory(categoryDto);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteById(id);

        return "redirect:/categories/list";
    }
}
