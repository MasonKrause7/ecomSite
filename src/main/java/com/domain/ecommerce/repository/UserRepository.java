package com.domain.ecommerce.repository;


import com.domain.ecommerce.dto.UserAddressDTO;
import com.domain.ecommerce.dto.UserDTO;
import com.domain.ecommerce.dto.UserDTO;
import com.domain.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {


   Optional<User> findUserByEmail(String email);

   List<UserDTO> findAllByAuthority(String authority);

   Optional<UserAddressDTO>findByUserId(Long id);






}
