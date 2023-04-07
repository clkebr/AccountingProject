package com.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyController {

    @GetMapping("/company-list")
    public  String get(){

        return "company/company-list";
    }
}
