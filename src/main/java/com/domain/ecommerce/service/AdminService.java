package com.domain.ecommerce.service;

import com.domain.ecommerce.repository.ProductRepository;
import com.domain.ecommerce.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final ProductRepository itemDAO;


    @Autowired
    public AdminService(ProductRepository itemDAO) {

        this.itemDAO = itemDAO;
    }
    //get all products from repository; add validation as needed
    public List<Product> getAllItems() {

        return itemDAO.findAll();
    }
    //get a single item from the database; add validation as needed
    public Optional<Product> getItem(Long id) {

        return itemDAO.findById(id);
    }

    public void createItem(Product item) {

        itemDAO.save(item);
    }

    public void deleteItem(Long id) {

        itemDAO.deleteById(id);
    }
}
