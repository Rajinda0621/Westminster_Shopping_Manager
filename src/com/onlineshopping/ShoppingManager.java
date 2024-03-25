package com.onlineshopping;

import java.io.IOException;
import java.util.List;

public interface ShoppingManager {

    void addProduct(Product product);
    void deleteProduct(String productID);
    void printListOfProducts();

    void saveInAFile(String file) throws IOException;


    void loadFromFile(String file) throws IOException;

    List<Product> getProductList();

}
