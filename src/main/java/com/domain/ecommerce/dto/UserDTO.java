package com.domain.ecommerce.dto;

import com.domain.ecommerce.models.Address;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Candelario Aguilar Torres
 **/
public interface UserDTO {
  String getUserId();
  String getFirstName();
  String getLastName();
  String getEmail();
  String getPhoneNumber();
  String getAuthority();
  Set<AddressDTO>getAddress();




}
