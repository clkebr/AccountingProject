package com.account.controller;

import com.account.dto.ClientVendorDto;
import com.account.enums.ClientVendorType;
import com.account.service.AddressService;
import com.account.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    private final AddressService addressService;

    public ClientVendorController(ClientVendorService clientVendorService, AddressService addressService) {
        this.clientVendorService = clientVendorService;
        this.addressService = addressService;
    }

    @GetMapping("/list")
    public String getClientVendorList(Model model){
       model.addAttribute("clientVendors", clientVendorService.findAllByCompany());
       return "/clientVendor/clientVendor-list";
    }

    @GetMapping("/create")
    public String createClientVendor(Model model){
        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        return "/clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public  String postClientVendor(@ModelAttribute("newClientVendor") ClientVendorDto clientVendorDto){
        System.out.println(clientVendorDto);
        clientVendorService.save(clientVendorDto);

        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{id}")
    public  String update(@PathVariable Long id, Model model){
        model.addAttribute("clientVendor", clientVendorService.findById(id));
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("countries", addressService.getAllCountries());
        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public  String save(@PathVariable Long id, @ModelAttribute("clientVendor") ClientVendorDto clientVendorDto){
        clientVendorService.updateClientVendor(id,clientVendorDto);
        return "redirect:/clientVendors/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        clientVendorService.deleteById(id);

        return "redirect:/clientVendors/list";
    }
}
