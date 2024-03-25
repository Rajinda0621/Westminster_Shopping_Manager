package com.onlineshopping;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class Product implements Serializable {

    private String productID;
    private String productName;
    private int noOfAvailableItems;
    private double price;

    public Product(String productID, String productName, int noOfAvailableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.noOfAvailableItems = noOfAvailableItems;
        this.price = price;
    }



    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNoOfAvailableItems() {
        return noOfAvailableItems;
    }

    public void setNoOfAvailableItems(int noOfAvailableItems) {
        this.noOfAvailableItems = noOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    @Override
    public String toString() {
        return " Product ID: '" + productID + '\'' +
                ", Product Name: '" + productName + '\'' +
                ", No Of Available Items: " + noOfAvailableItems +
                ", Price: " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return noOfAvailableItems == product.noOfAvailableItems && price == product.price && Objects.equals(productID, product.productID) && Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, productName, noOfAvailableItems, price);
    }
}
