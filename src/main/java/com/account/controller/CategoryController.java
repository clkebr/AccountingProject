package com.account.controller;

import com.account.dto.CategoryDto;
import com.account.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/list")
	public String getCategoryList(Model model) {
		model.addAttribute("categories", categoryService.findAllCategoryByCompanySorted());
		return "/category/category-list";
	}

	@GetMapping("/create")
	public String createCategory(Model model) {
		model.addAttribute("newCategory", new CategoryDto());
		return "/category/category-create";
	}

	@PostMapping("/create")
	public String postCategory(@Valid @ModelAttribute("newCategory") CategoryDto categoryDto, BindingResult result) {
		if (result.hasErrors()) return "/category/category-create";
		categoryService.saveCategory(categoryDto);
		return "redirect:/categories/list";
	}

	@GetMapping("/update/{id}")
	public String updateCategory(@PathVariable Long id, Model model) {
		model.addAttribute("category", categoryService.findById(id));
		return "/category/category-update";
	}

	@PostMapping("/update/{id}")
	public String saveCategory(@Valid @ModelAttribute("category") CategoryDto categoryDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) return "/category/category-update";
		categoryService.updateCategory(categoryDto);
		return "redirect:/categories/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") Long id) {
		categoryService.deleteById(id);

		return "redirect:/categories/list";
	}
}
