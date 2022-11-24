package com.domain.ecommerce.models;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
   
    private Long categoryId;
    private String productName;
    private String productDescription;
    private String imageURL;

    public Product() {
    }

    public Product(Long productId, Long categoryId, String productName, String productDescription, String imageURL) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.imageURL = imageURL;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }




}
