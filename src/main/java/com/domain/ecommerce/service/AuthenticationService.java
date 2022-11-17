package com.domain.ecommerce.service;

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
    if(user.getAuthority() == null) {
        user.setAuthority("USER");

    }

   user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
}
public boolean userExist(String  email) {
    Optional<User> databaseUser = userRepository.findUserByEmail(email);
    if(databaseUser.isPresent()) {
        return true;
    } else {
        return false;
    }

}

public User findUserByEmail(String email) {
    return userRepository.findUserByEmail(email).get();
}

public void setPassword(String email,String newPassword) {
    User user = findUserByEmail(email);
    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
    userRepository.save(user);
}



}
