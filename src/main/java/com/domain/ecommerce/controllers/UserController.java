package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.Address;
import com.domain.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Candelario Aguilar Torres
 **/

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestParam String field, @RequestBody String newFieldData) {
        System.out.println("hit controller");
        System.out.println("field = " + field);
        boolean authorizedToUpdate = userService.checkUser(id);
        if (authorizedToUpdate) {
            userService.updateUser(id, field, newFieldData);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/update/address/{id}")
    public ResponseEntity<Object> updateUserAddress(@PathVariable Long id, @RequestParam Long addressid,@Valid @RequestBody Address address)  {
        boolean authorizedToUpdate = userService.checkUser(id);
        if(authorizedToUpdate) {
            userService.updateUserAddress(id,addressid,address);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}


