package com.domain.ecommerce;

import com.domain.ecommerce.DAO.ItemDAO;
import com.domain.ecommerce.models.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication//CommandLineRunner interface to add dummy data when application is run
public class EcommerceApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
       @Autowired
    private ItemDAO itemDAO;
    @Override
    public void run(String... args) throws Exception {
        //create dummy items and add them to database
       Items item1 = new Items("gameboy","green gameboy","urltoimage",15.99,3);
        itemDAO.create(item1);
        Items item2 = new Items("tv","20","urltoimage",200.99,1);
        itemDAO.create(item2);
        Items item3 = new Items("hp laptop","15 hp laptop","urltoimage",400,8);
        itemDAO.create(item3);
        Items item4 = new Items("Ski mask","beddazzeld ski mask for your next robbery","urltoimage",35,2);
        itemDAO.create(item4);

        //retrieve list of items and print names to console
        List<Items> itemsList = itemDAO.list();
        for(Items item : itemsList) {
            System.out.println(item.getItemName());
        }

        itemDAO.delete(1L);
        item3.setItemName("nintendo Switch");
        itemDAO.update(item3,3L," item_description");

    }
}
