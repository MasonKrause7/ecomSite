package com.domain.ecommerce.service;

import com.domain.ecommerce.models.Address;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Candelario Aguilar Torres
 **/
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).get();

    }
/* Checks if the current authenticated user is equal to the user being requested to be updated */
    public boolean checkUser(Long id) {
        String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        String user = findUserById(id).getEmail();

        if(authenticatedUser.equals(user))
            return true;
        else
            return false;

    }

    public void updateUser(Long id, String field,String newFieldData) {
        User user = findUserById(id);
        switch (field) {
            case "firstname": {
                user.setFirstName(newFieldData);
                userRepository.save(user);
                break;
            }
            case "lastname": {
                user.setLastName(newFieldData);
                userRepository.save(user);
                break;
            }
            case "phonenumber": {
                user.setPhoneNumber(newFieldData);
                userRepository.save(user);
                break;
            }
        }
    }

    public void updateUserAddress(Long id, Long addressid, Address address) {
        User user = findUserById(id);

        Set<Address> userAddress = user.getAddress();
        Iterator<Address> iterator = userAddress.iterator();
        while(iterator.hasNext()) {
            Address currentAddress= iterator.next();
            if(currentAddress.getAddressId() == addressid) {
                currentAddress = address;
                userRepository.save(user);
                break;
            }
        }

    }
}
