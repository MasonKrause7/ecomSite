package com.domain.ecommerce.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String imageUrl;
    private double price;
    private int quantity;

    public Items(String itemName, String itemDescription, String imageUrl, double price, int quantity) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public Items() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items = (Items) o;
        return Double.compare(items.price, price) == 0 && quantity == items.quantity && itemId.equals(items.itemId) && itemName.equals(items.itemName) && itemDescription.equals(items.itemDescription) && imageUrl.equals(items.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, itemDescription, imageUrl, price, quantity);
    }

    public Long getItemID() {
        return itemId;
    }

    public void setItemID(Long itemID) {
        this.itemId = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
