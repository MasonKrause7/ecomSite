package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.Product;
import com.domain.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/employees")
public class AdminController {
    private final AdminService adminService;


    @Autowired

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    //retrive list of all items in the database
    @CrossOrigin
    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getItems() {
        return adminService.getAllItems();
    }

    @CrossOrigin
    @GetMapping("/items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Product> getItem(@PathVariable Long id) {
        System.out.println(id);
        return adminService.getItem(id);
    }
    @CrossOrigin
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public void createItem(@RequestBody Product item) {
        adminService.createItem(item);
    }
    @CrossOrigin
    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteItem(@PathVariable Long id) {
        adminService.deleteItem(id);
    }

}
