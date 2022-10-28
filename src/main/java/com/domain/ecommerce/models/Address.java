package com.domain.ecommerce.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Address")
public class Address {
    public Address(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long address_id;
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private int postalCode;
    private String country;


    public Address(String houseNumber, String street, String city, String state, int postalCode, String country) {
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;

    }

    @ManyToMany(mappedBy = "address")
    private Set<User> user = new HashSet<>();





}
