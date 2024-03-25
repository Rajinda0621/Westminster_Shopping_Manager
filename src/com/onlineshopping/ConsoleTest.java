package com.onlineshopping;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ConsoleTest {


    @Test
    public void addProduct() {
        ShoppingManager shoppingManager = new WestminsterShoppingManager();

        Electronics p1 = new Electronics("laptop01", "Laptop", 10, 1000, "Dell", 12);
        shoppingManager.addProduct(p1);

        assertTrue(shoppingManager.getProductList().contains(p1));

        assertEquals(1, shoppingManager.getProductList().size());
    }

    @Test
    public void deleteProduct() {
        ShoppingManager shoppingManager = new WestminsterShoppingManager();

        Electronics p1 = new Electronics("laptop01", "Laptop", 10, 1000, "Dell", 12);
        shoppingManager.addProduct(p1);

        shoppingManager.deleteProduct("laptop01");

        assertFalse(shoppingManager.getProductList().contains(p1));

        assertEquals(0, shoppingManager.getProductList().size());
    }






}