package com.domain.ecommerce.service;

import com.domain.ecommerce.dto.UserAddressDTO;
import com.domain.ecommerce.dto.UserDTO;
import com.domain.ecommerce.models.Authorites;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserDTORepository;
import com.domain.ecommerce.repository.UserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Candelario Aguilar Torres
 **/
@Service
@Transactional
public class AdminService {
    private final UserRepository userRepository;
    private final UserDTORepository userDTORepository;
    private final PasswordEncoder bcryptpasswordEncoder;
    public AdminService(UserRepository userRepository, UserDTORepository userDTORepository, PasswordEncoder bcryptpasswordEncoder) {
        this.userRepository = userRepository;
        this.userDTORepository = userDTORepository;
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
        users.addAll(userDTORepository.findAllByAuthority(Authorites.ADMIN.name()));
        users.addAll(userDTORepository.findAllByAuthority(Authorites.EMPLOYEE.name()));
        return users;


    }

    public UserAddressDTO findEmployeeById(Long id) {
        return userDTORepository.findByUserId(id).get();
    }



    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "successfully deleted employee";
    }

    public List<UserAddressDTO>findAllBy() {
        return userDTORepository.findAllBy();
    }
}
