package com.domain.ecommerce.service;

import com.domain.ecommerce.models.Roles;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthenticationService {
private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
@Autowired
public AuthenticationService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
}


public User createUser(User user) {
    if(user.getRole() == null) {
        user.setRole(Roles.ROLE_USER);
    }

   user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
}
public boolean existingUser(String  email) {
    Optional<User> databaseUser = userRepository.findUserByEmail(email);
    if(databaseUser.isPresent()) {
        return true;
    } else {
        return false;
    }
}


public User findByUserName(String username) {
   return userRepository.findUserByEmail(username).get();

}

}
