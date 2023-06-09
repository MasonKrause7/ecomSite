package com.domain.ecommerce;

import com.domain.ecommerce.models.*;
import com.domain.ecommerce.repository.UserRepository;
import com.domain.ecommerce.security.RSAKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;


@Transactional
@EnableConfigurationProperties(RSAKeyProperties.class)//needed in order to access properties from the .properties file programmatically. reference RSAKeyProperties.class for example.
@SpringBootApplication//CommandLineRunner interface to add dummy data when application is run
public class EcommerceApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }



    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder bcryptpasswordEncoder;

    @Override
    public void run(String... args) throws Exception {




        Address address = new Address("1305","20th st","oceano","ca",93445,"United States");
        User user = new User("Candelario","Aguilar","candelarioa42@gmail.com",bcryptpasswordEncoder.encode("password"),"8056022425", Authorites.ADMIN.name(),address);
        userRepository.save(user);

        Address address2 = new Address("134","main st","clarksville","tn",93445,"United States");
        User user2 = new User("Frank","Guzman","frankg@gmail.com",bcryptpasswordEncoder.encode("password"),"8056022425", Authorites.USER.name(), address2);
        userRepository.save(user2);

        Address address3 = new Address("134","vista st","nashiville","tn",93445,"United States");
        User user3 = new User("Jeff","Liberty","jliberty@gmail.com",bcryptpasswordEncoder.encode("password"),"8056022425", Authorites.EMPLOYEE.name(), address3);
        userRepository.save(user3);

        Address address4 = new Address("134","vista st","nashiville","tn",93445,"United States");
        User user4 = new User("Mason","Krause","masongkrause@yahoo.com",bcryptpasswordEncoder.encode("password"),"8056022425", Authorites.EMPLOYEE.name(), address4);
        userRepository.save(user4);





    }
}
