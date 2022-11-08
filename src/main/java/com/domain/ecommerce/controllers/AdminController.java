package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.Product;
import com.domain.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/employees")
public class AdminController {
    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    //retrive list of all items in the database
    @GetMapping("/items")
    public List<Product> getItems() {

       var user = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(user.getAuthorities());
        return adminService.getAllItems();
    }

    @GetMapping("/items/{id}")
    public Optional<Product> getItem(@PathVariable Long id) {
        System.out.println(id);
        return adminService.getItem(id);
    }
    @PostMapping("/items")
    public void createItem(@RequestBody Product item) {
        adminService.createItem(item);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        adminService.deleteItem(id);
    }

}
