package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthenticationController {

@PostMapping("signup")
@CrossOrigin
public ResponseEntity<String> signIn(@RequestBody User user) {
    System.out.println(user);
    return ResponseEntity.ok("successful");
}

}