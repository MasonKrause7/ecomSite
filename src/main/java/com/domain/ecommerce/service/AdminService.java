package com.domain.ecommerce.service;

import com.domain.ecommerce.DAO.ItemDAO;
import com.domain.ecommerce.models.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class AdminService {
    private final ItemDAO itemDAO;


    @Autowired
    public AdminService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }
    //get all products from repository; add validation as needed
    public List<Items> getAllItems() {
        return itemDAO.list();
    }
    //get a single item from the database; add validation as needed
    public Items getItem(Long id) {
        return itemDAO.get(id);
    }

    public void createItem(Items item) {
        itemDAO.create(item);
    }

    public void deleteItem(Long id) {
        itemDAO.delete(id);
    }
}
