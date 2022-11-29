package com.domain.ecommerce.service;

import com.domain.ecommerce.models.Category;
import com.domain.ecommerce.models.Product;
import com.domain.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Candelario Aguilar Torres
 **/
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        return category;
    }

    public Product addProduct(Long categoryid,Product product) {
       Category category = findCategoryById(categoryid);
       category.setProduct(product);
       categoryRepository.save(category);
       return product;

    }
    public void deleteProduct(Long categoryid, Long productid) {
        Category category = findCategoryById(categoryid);
        List<Product> productList = category.getProducts();
        for(Product product: productList) {
            if(product.getId().equals(productid)) {
                productList.remove(product);
                categoryRepository.save(category);
            }
        }


    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
