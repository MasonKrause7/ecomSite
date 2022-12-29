package com.domain.ecommerce.repository;

import com.domain.ecommerce.dto.UserAddressDTO;
import com.domain.ecommerce.dto.UserDTO;
import com.domain.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Candelario Aguilar Torres
 **/
public interface UserDTORepository extends JpaRepository<User,Long> {
    Optional<UserAddressDTO>findUserByEmail(String email);

    List<UserDTO> findAllByAuthority(String authority);

    Optional<UserAddressDTO> findByUserId(Long id);
    List<UserAddressDTO> findAllBy();
}
