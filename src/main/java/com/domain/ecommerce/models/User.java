package com.domain.ecommerce.models;

import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    public User() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Roles Role;

    public User(String firstName, String lastName, String userName, String password, String phoneNumber, Roles role, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        Role = role;
        this.address.add(address);
    }

    @ManyToMany(cascade=CascadeType.ALL)
  @JoinTable(
          name = "user_address",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<Address> address = new HashSet<>();



}
