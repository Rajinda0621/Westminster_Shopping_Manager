package com.onlineshopping;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Electronics extends Product implements Serializable{


    private String brand;
    private int warrantyPeriod;



    public Electronics(String productID, String productName, int noOfAvailableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, noOfAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Product type: Electronics, " +super.toString()+
                ", Brand: '" + brand + '\'' +
                ", Warranty Period: " + warrantyPeriod+" months";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Electronics that = (Electronics) o;
        return warrantyPeriod == that.warrantyPeriod && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brand, warrantyPeriod);
    }
}
