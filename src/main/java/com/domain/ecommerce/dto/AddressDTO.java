package com.domain.ecommerce.dto;

/**
 * @author Candelario Aguilar Torres
 **/
public interface AddressDTO {
        Long getAddressId();
        String getHouseNumber();
        String getStreet();
        String getCity();
        String getState();
        int getPostalCode();
        String getCountry();

}
