package com.domain.ecommerce;

import com.domain.ecommerce.DAO.ItemDAO;
import com.domain.ecommerce.models.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//CommandLineRunner interface to add dummy data when application is run
public class EcommerceApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
       @Autowired
    private ItemDAO itemDAO;
    @Override
    public void run(String... args) throws Exception {
       Items items = new Items("gameboy","green gameboy","urltoimage",15.99,3);
        itemDAO.create(items);
    }
}
