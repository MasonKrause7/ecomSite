package com.domain.ecommerce.service;

import com.domain.ecommerce.dto.UserDTO;
import com.domain.ecommerce.models.Authorites;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Candelario Aguilar Torres
 **/
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder bcryptpasswordEncoder;
    public AdminService(UserRepository userRepository, PasswordEncoder bcryptpasswordEncoder) {
        this.userRepository = userRepository;
        this.bcryptpasswordEncoder = bcryptpasswordEncoder;
    }

    public String createEmployee(User employee)  {


            employee.setAuthority(Authorites.EMPLOYEE.name());
            employee.setPassword(bcryptpasswordEncoder.encode(employee.getPassword()));
            userRepository.save(employee);
            return "successfully created employee";



    }

    public List<UserDTO> readAllEmployees(){
        List<UserDTO> users = new ArrayList<>();
        users.addAll(userRepository.findAllByAuthority(Authorites.ADMIN.name()));
        users.addAll(userRepository.findAllByAuthority(Authorites.EMPLOYEE.name()));
        return users;


    }

    public UserDTO  findEmployeeById(Long id) {
        return userRepository.findByUserId(id).get();
    }



    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "successfully deleted employee";
    }
}
