package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.User;
import com.domain.ecommerce.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Candelario Aguilar Torres
 **/
@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {
    private final AdminService adminService;

    public AdminDashboardController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add_employee")
    public ResponseEntity<Object> createEmployee(@RequestBody User employee){



        return ResponseEntity.ok(adminService.createEmployee(employee));
    }

    @GetMapping("/read_employee")
    public ResponseEntity<List<User>> readEmployee() {

            return ResponseEntity.ok(adminService.readAllEmployees());


    }

    @GetMapping("/read_employee/{id}")
    public ResponseEntity<User> readEmployee(@PathVariable Long id) {

        return ResponseEntity.ok(adminService.readEmployee(id));


    }

    /*
    Needs to be implements properly, currently return random string to avoid compiler error
     */
    @PutMapping("update_employee")
    public ResponseEntity<Object> updateEmployee() {
        return ResponseEntity.ok(" " );
    }

    @DeleteMapping("delete_employee/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        return ResponseEntity.ok().body(adminService.deleteUser(id));
    }
}
