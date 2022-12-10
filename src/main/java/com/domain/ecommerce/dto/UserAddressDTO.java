package com.domain.ecommerce.dto;

import java.util.Set;

/**
 * @author Candelario Aguilar Torres
 **/
public interface UserAddressDTO {
  String getUserId();
  String getFirstName();
  String getLastName();
  String getEmail();
  String getPhoneNumber();
  String getAuthority();
  Set<AddressDTO>getAddress();




}
