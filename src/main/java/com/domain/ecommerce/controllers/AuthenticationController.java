package com.domain.ecommerce.controllers;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.SignUpException;
import com.domain.ecommerce.models.Address;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
public ResponseEntity<Object> signIn(@RequestBody User user) throws SignUpException {
    boolean exists = authenticationService.existingUser(user.getEmail());
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/signup").toUriString());
    if (exists) {
        throw new SignUpException("User already exists");
    } else {
        authenticationService.createUser(user);
        return ResponseEntity.created(uri).body(authenticationService.createUser(user));
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