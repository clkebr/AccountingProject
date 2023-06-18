package com.account.controller;

import com.account.dto.UserDto;
import com.account.service.CompanyService;
import com.account.service.RoleService;
import com.account.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        model.addAttribute("userRoles",roleService.findRoles());
        model.addAttribute("companies",companyService.findCompanies());

        return "/user/user-update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid @ModelAttribute UserDto userDto, BindingResult result){
        if(result.hasErrors()) return "redirect:/users/update/"+id;

        boolean emailExist = userService.emailExist(userDto);
        if (result.hasErrors() || emailExist){
            if (emailExist) {
                result.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }
            return "/user/user-update";
        }
        userService.update(userDto);
        return "redirect:/users/list";
    }

    @GetMapping("/create")
    public String create( Model model){
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("userRoles", roleService.findRoles());
        model.addAttribute("companies", companyService.findCompanies());
        return "/user/user-create";
    }

    @PostMapping("/create")
    public  String create(@Valid @ModelAttribute("newUser") UserDto userDto, BindingResult result, Model model){
        boolean emailExist = userService.emailExist(userDto);

        if (result.hasErrors() || emailExist){
            if (emailExist) {
                result.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }

            return "user/user-create";
        }
        userService.save(userDto);

        return "redirect:/users/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
       userService.deleteUserById(id);

        return "redirect:/users/list";
    }
}
