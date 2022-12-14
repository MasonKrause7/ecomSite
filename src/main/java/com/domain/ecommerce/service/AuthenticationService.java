package com.domain.ecommerce.service;

import com.domain.ecommerce.dto.UserAddressDTO;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserDTORepository;
import com.domain.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class AuthenticationService {
private final UserRepository userRepository;
private final PasswordEncoder bCryptPasswordEncoder;
private final UserDTORepository userDTORepository;
@Autowired
public AuthenticationService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, UserDTORepository userDTORepository) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDTORepository = userDTORepository;
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

public UserAddressDTO findUserAddressDTObyEmail(String email) {
    return userDTORepository.findUserByEmail(email).get();
}


public void setPassword(String email,String newPassword) {
    User user = findUserByEmail(email);
    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
    userRepository.save(user);
}



}
