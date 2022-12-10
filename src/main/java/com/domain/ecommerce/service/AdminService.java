package com.domain.ecommerce.service;

import com.domain.ecommerce.models.Authorites;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Candelario Aguilar Torres
 **/
@Service
public class AdminService {
    private final UserRepository userRepository;
    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createEmployee(User employee)  {


            employee.setPassword(Authorites.ADMIN.name());
            userRepository.save(employee);
            return "successfully created employee";



    }

    public List<User> readAllEmployees(){
        List<User> users = new ArrayList<>();
        users.addAll(userRepository.findAllByAuthority(Authorites.ADMIN.name()));
        users.addAll(userRepository.findAllByAuthority(Authorites.EMPLOYEE.name()));
        return users;

    }

    public User readEmployee(Long id){
        return userRepository.findById(id).get();

    }



    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "successfully deleted employee";
    }
}
