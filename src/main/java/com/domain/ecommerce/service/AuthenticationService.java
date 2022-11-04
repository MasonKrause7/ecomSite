package com.domain.ecommerce.service;

import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AuthenticationService {
private final UserRepository userRepository;
@Autowired
public AuthenticationService(UserRepository userRepository) {
    this.userRepository = userRepository;
}


public User createUser(User user) {
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


}
