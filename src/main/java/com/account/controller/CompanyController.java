package com.account.controller;

import com.account.dto.CompanyDto;
import com.account.service.AddressService;
import com.account.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/companies")
public class CompanyController {

   private final CompanyService companyService;
   private final AddressService addressService;

    public CompanyController(CompanyService companyService, AddressService addressService) {
        this.companyService = companyService;
        this.addressService = addressService;
    }

    @GetMapping("/list")
    public  String get(Model model){
        model.addAttribute("companies", companyService.findAll());
        return "company/company-list";
    }
    @GetMapping("/update/{id}")
    public  String update(@PathVariable Long id, Model model){
        model.addAttribute("company", companyService.findById(id));
        model.addAttribute("countries",addressService.getAllCountries() );
        return "company/company-update";
    }

    @PostMapping("/update/{id}")
    public  String save(@PathVariable String id, @Valid @ModelAttribute("company") CompanyDto companyDto, BindingResult result){

        if (result.hasErrors()) return "/company/company-update";

        companyService.updateCompany(id,companyDto); return "redirect:/companies/list";
    }

    @GetMapping("/deactivate/{id}")
    public  String deactivateStatus(@PathVariable Long id, Model model){
        companyService.deactivateCompanyStatus(id);
        model.addAttribute("companies", companyService.findAll());
        return "company/company-list";
    }

    @GetMapping("/activate/{id}")
    public  String activateStatus(@PathVariable Long id, Model model){
        companyService.activateCompanyStatus(id);
        model.addAttribute("companies", companyService.findAll());
        return "company/company-list";
    }
    @GetMapping("/create")
    public  String createCompany( Model model){
        model.addAttribute("newCompany", new CompanyDto());
        return "company/company-create";
    }
    @PostMapping("/create")
    public  String postCompany(@Valid @ModelAttribute("newCompany") CompanyDto companyDto, BindingResult result){

        if (result.hasErrors()) return "/company/company-create";

        companyService.save(companyDto);

        return "redirect:/companies/list";
    }


}
