package com.domain.ecommerce.models;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String phoneNumber;

    public Admin() {}

    public Admin(String fristName, String lastName, String userName, String password, String phoneNumber) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return firstName.equals(admin.firstName) && lastName.equals(admin.lastName) && userName.equals(admin.userName) && password.equals(admin.password) && phoneNumber.equals(admin.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, userName, password, phoneNumber);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }





}
