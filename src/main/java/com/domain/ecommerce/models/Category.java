package com.domain.ecommerce.models;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Candelario Aguilar Torres
 **/
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;



    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategory = new ArrayList<>();

    public Category() {

    }

    public List<Category> getSubCategory() {
        return subCategory;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category setParent(Category parentCategory) {
        this.parentCategory = parentCategory;
        parentCategory.subCategory.add(this);
        return parentCategory;
    }

    public Category setSubCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.parentCategory = this;
        this.subCategory.add(category);
        return category;
    }

    @Override
    public String toString() {

        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCategory=" + (parentCategory == null ? "null" : parentCategory.name)+
                ", subCategory=" + subCategory.toString() +
                '}';
    }
}
