package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.Items;
import com.domain.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/api/items")
    @ResponseStatus(HttpStatus.OK)
    public List<Items> getItems() {
        return adminService.getAllItems();
    }


    @GetMapping("/api/items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Items getItem(@PathVariable Long id) {
        System.out.println(id);
        return adminService.getItem(id);
    }

    @PostMapping("/api/items")
    @ResponseStatus(HttpStatus.CREATED)
    public void createItem(@RequestBody Items item) {
        adminService.createItem(item);
    }

    @DeleteMapping("api/items/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteItem(@PathVariable Long id) {
        adminService.deleteItem(id);
    }

}
