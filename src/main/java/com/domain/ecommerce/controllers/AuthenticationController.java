package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.Address;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthenticationController {

private final UserRepository userRepository;
@Autowired
public AuthenticationController(UserRepository userRepository) {
    this.userRepository = userRepository;
}
@PostMapping ("/signup")
@CrossOrigin
public ResponseEntity<String> signIn(/*@RequestBody User user*/) {
    User user = new User();
    user.setFirstName("candelario");
    Address address = new Address();
    address.setStreet("20th");
    address.setCity("clarkesbille");
    address.setPostalCode(12345);
    address.setState("TN");
    user.addAddress(address);
    System.out.println(user);
    return ResponseEntity.ok("successful");
}

}