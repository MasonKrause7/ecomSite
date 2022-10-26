package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.Items;
import com.domain.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class AdminController {
    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    //retrive list of all items in the database
    @GetMapping
    public List<Items> getItems() {
        return adminService.getAllItems();
    }


}
