package com.domain.ecommerce.controllers;

import com.domain.ecommerce.dto.UserDTO;
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

    @GetMapping("/get_employees")
    public ResponseEntity<List<UserDTO>> readEmployee() {

            return ResponseEntity.ok(adminService.readAllEmployees());


    }

    @GetMapping("/get_employees/{id}")
    public ResponseEntity<UserDTO> readEmployee(@PathVariable Long id) {

        return ResponseEntity.ok(adminService.findEmployeeById(id));


    }



    @DeleteMapping("delete_employee/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        return ResponseEntity.ok().body(adminService.deleteUser(id));
    }
}
