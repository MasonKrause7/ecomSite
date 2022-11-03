package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.Address;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserRepository;
import com.domain.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class AuthenticationController {

private final AuthenticationService authenticationService;

@Autowired
public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
}

@PostMapping ("/signup")
@ResponseStatus(HttpStatus.CREATED)
public void signIn(@RequestBody User user) throws Exception {
    boolean exists = authenticationService.existingUser(user.getEmail());

    if(exists) {
        throw new Exception("User already exists");
    } else {
        authenticationService.createUser(user);
        System.out.println("user created");
    }



}
@GetMapping("/signup")
public User getUserTest(){
    User user = new User();
    user.setFirstName("candelario");
    Address address = new Address();
    address.setStreet("20th");
    address.setCity("clarkesbille");
    address.setPostalCode(12345);
    address.setState("TN");
    user.addAddress(address);
    return user;
    }
}