package com.account.controller;

import com.account.dto.UserDto;
import com.account.service.CompanyService;
import com.account.service.RoleService;
import com.account.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    private final CompanyService companyService;


    public UserController(UserService userService,
                          RoleService roleService, CompanyService companyService) {
        this.userService = userService;

        this.roleService = roleService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String get(Model model){
        model.addAttribute("users",userService.findAll());

        return "/user/user-list";
    }

    @GetMapping("/update/{id}")
    public String get(@PathVariable("id") Long id, Model model){
        model.addAttribute("user",userService.findById(id));
        model.addAttribute("userRoles",roleService.findAll());
        model.addAttribute("companies",companyService.findAll());

        return "/user/user-update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute UserDto userdto){
        userService.update(userdto);
        return "redirect:/list";
    }

    @GetMapping("/create")
    public String create( Model model){
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("userRoles", roleService.findRoles());
        model.addAttribute("companies", companyService.findCompanies());
        return "/user/user-create";
    }

    @PostMapping("/create")
    public  String create(@ModelAttribute("newUser") UserDto userDto){
        userService.save(userDto);

        return "redirect:/list";
    }
}
