package com.domain.ecommerce;

import com.domain.ecommerce.models.Category;
import com.domain.ecommerce.models.Product;
import com.domain.ecommerce.repository.CategoryRepository;
import com.domain.ecommerce.repository.UserRepository;
import com.domain.ecommerce.models.Address;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.security.RSAKeyProperties;
import com.domain.ecommerce.service.EmailService;
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
    private CategoryRepository catDAO;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder bcryptpasswordEncoder;

    @Autowired
    EmailService email;
    @Override
    public void run(String... args) throws Exception {
        Category shoes = new Category();
        shoes.setName("shoes");
        shoes.setSubCategory("nikes");
        shoes.setSubCategory("addidas");
        catDAO.save(shoes);
        Category airmax = new Category();
        airmax.setName("airmax");
        airmax.setParent(shoes);
        airmax.setProduct(new Product("airmax95","comfy airmax show","www.imageurl.com",109.99));
        catDAO.save(airmax);
        shoes = catDAO.findById(1L).get();

        System.out.println(shoes);











        Address address = new Address("1305","20th st","oceano","ca",93445,"United States");
        User user = new User("Candelario","Aguilar","candelarioa42@gmail.com",bcryptpasswordEncoder.encode("password"),"8056022425", "ADMIN",address);
        userRepository.save(user);

        Address address2 = new Address("134","main st","clarksville","tn",93445,"United States");
        User user2 = new User("Frank","Guzman","frankg@gmail.com",bcryptpasswordEncoder.encode("password"),"8056022425", "USER",address2);
        userRepository.save(user2);





    }
}
