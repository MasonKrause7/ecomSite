package com.domain.ecommerce.controllers;

import com.domain.ecommerce.models.Category;
import com.domain.ecommerce.models.Product;
import com.domain.ecommerce.service.CategoryService;
import com.nimbusds.jose.JWSVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Candelario Aguilar Torres
 **/
@RestController//ENSURE TO ADD AUTHENICATION FOR POST METHODS
@RequestMapping("/api/categories")
@Profile("personal-dev")
public class CategoryController {
    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;

    }

    @GetMapping("/all-categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        System.out.println("getting all categories");
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
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategoryById(id);
        return ResponseEntity.accepted().body("success");
    }

    @PostMapping("/create-product/{categoryid}/productid")
    public ResponseEntity<Product> createItem(@RequestBody Product product, @PathVariable Long categoryid) {

        return ResponseEntity.created(null).body(categoryService.addProduct(categoryid,product));
    }

    @DeleteMapping("/delete-product/{categoryid}/{productid}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long categoryid,@PathVariable Long productid) {
        categoryService.deleteProduct(categoryid,productid);
                return ResponseEntity.accepted().body("success");
    }

    @GetMapping("/uploadimage")
        public String uploadimage(MultipartFile file) {
        System.out.println("success");
        return "success";
        }
    }




