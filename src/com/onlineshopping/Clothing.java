package com.onlineshopping;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Clothing extends Product implements Serializable{


    private String size;
    private String colour;


    public Clothing(String productID, String productName, int noOfAvailableItems, double price, String size, String colour) {
        super(productID, productName, noOfAvailableItems, price);
        this.size = size;
        this.colour = colour;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }



    public String getColour() {
        return colour;
    }




    @Override
    public String toString() {
        return "Product type: Clothing, " +super.toString()+
                ", Size: '" + size + '\'' +
                ", Colour: " + colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Clothing clothing = (Clothing) o;
        return Objects.equals(size, clothing.size) && Objects.equals(colour, clothing.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size, colour);
    }
}
