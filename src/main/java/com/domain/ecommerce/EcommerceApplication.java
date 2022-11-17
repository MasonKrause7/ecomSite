package com.domain.ecommerce;

import com.domain.ecommerce.repository.ProductRepository;
import com.domain.ecommerce.repository.UserRepository;
import com.domain.ecommerce.models.Address;
import com.domain.ecommerce.models.Product;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.security.RSAKeyProperties;
import com.domain.ecommerce.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.Transport;

@EnableConfigurationProperties(RSAKeyProperties.class)//needed in order to access properties from the .properties file programmatically. reference RSAKeyProperties.class for example.
@SpringBootApplication//CommandLineRunner interface to add dummy data when application is run
public class EcommerceApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
    @Autowired
    private ProductRepository itemDAO;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder bcryptpasswordEncoder;

    @Autowired
    EmailService email;
    @Override
    public void run(String... args) throws Exception {
        //create dummy items and add them to database
       Product item1 = new Product("gameboy","green gameboy","urltoimage",15.99,3);
        itemDAO.save(item1);
        Product item2 = new Product("tv","20","urltoimage",200.99,1);
        itemDAO.save(item2);
        Product item3 = new Product("hp laptop","15 hp laptop","urltoimage",400,8);
        itemDAO.save(item3);
        Product item4 = new Product("Ski mask","beddazzeld ski mask for your next robbery","urltoimage",35,2);
        itemDAO.save(item4);
        Address address = new Address("1305","20th st","oceano","ca",93445,"United States");
        User user = new User("Candelario","Aguilar","candelarioa42@gmail.com",bcryptpasswordEncoder.encode("password"),"8056022425", "ADMIN",address);
        userRepository.save(user);

        Address address2 = new Address("134","main st","clarksville","tn",93445,"United States");
        User user2 = new User("Frank","Guzman","frankg@gmail.com",bcryptpasswordEncoder.encode("password"),"8056022425", "USER",address2);
        userRepository.save(user2);





    }
}
