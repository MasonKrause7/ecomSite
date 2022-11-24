package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.Category;
import com.domain.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Candelario Aguilar Torres
 **/
@RestController//ENSURE TO ADD AUTHENICATION FOR POST METHODS
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all-categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/category-by-id/{id}")
    public ResponseEntity<Category> getItem(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @PostMapping( value = "/create-category",consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Category> createItem(@RequestBody String categoryName) {

        return ResponseEntity.created(null).body(categoryService.createCategory(categoryName));
    }



    @DeleteMapping("/delete-category-by-id/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long id) {

        categoryService.deleteCategoryById(id);
        return ResponseEntity.accepted().body("success");
    }


}
