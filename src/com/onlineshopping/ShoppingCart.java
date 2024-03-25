package com.onlineshopping;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> listOfShoppingCartProducts = new ArrayList<>();//List to store all the products in shopping cart

    public void addToCart(Product product){
        listOfShoppingCartProducts.add(product);
    }

    public List<Product> getListOfProducts() {
        return listOfShoppingCartProducts;
    }

}
