package com.onlineshopping;

import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{

    private List<Product> productList = new ArrayList<>(); // List to store products
    private static final int max_products = 50;// maximum amount of products

    @Override
    public void addProduct(Product product) {// Method to add products
        if (productList.size() <= max_products){
            if (productList.contains(product)){
                System.out.println("Product already exists");
            }else {
                productList.add(product);// Adding product
            }
        }else {
            System.out.println("Product quantity is full");
        }

    }

    @Override
    public void deleteProduct(String productID) {// Method to delete product
        boolean found = false;
        if (productList.size() > 0){
            for (Product product : productList){
                if(product.getProductID().equals(productID)){//If the given product Id exist
                    found = true;
                    System.out.println("Removing Product....");
                    System.out.println("(Product ID: "+ product.getProductID());
                    System.out.printf("\nProduct type: %s ", product instanceof Electronics ? "Electronic" : "Clothing");
                    System.out.println("\nProduct name: "+ product.getProductName());
                    System.out.println("Product price: "+product.getPrice());
                    System.out.println("No of items: "+ product.getNoOfAvailableItems()+")");
                    productList.remove(product);
                    System.out.println("\n"+productList.size() + " products remaining");
                    break;
                }
            }
            if (!found){
                System.out.println("Invalid Product ID");
            }

        }else{
            System.out.println("No products added");
        }



    }

    @Override
    public void printListOfProducts() {// Method to print list of product
        if (productList.isEmpty()) {
            System.out.println("No products added");
            return;
        }
        Collections.sort(productList, new Comparator<Product>() {// Sort product Id alphabetically
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductID().compareTo(p2.getProductID());
            }
        });

        // Print the sorted list of products
        System.out.println("**List of products**");
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    @Override
    public void saveInAFile(String file) throws IOException {// method to save products in a file
        FileOutputStream fo = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fo);

        for(Product p: productList){
            os.writeObject(p);
        }

        os.flush();
        fo.close();
        os.close();
    }


    @Override
    public void loadFromFile(String file) throws IOException {
        productList.clear();

        FileInputStream fi = new FileInputStream(file);
        try (ObjectInputStream oi = new ObjectInputStream(fi)) {
            for (; ; ) {
                try {
                    Product p = (Product) oi.readObject();
                    productList.add(p);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + e.getMessage());
                }
            }
            fi.close();
            oi.close();
            System.out.println("\nData loaded from file successfully.");
        } catch (FileNotFoundException e) {
            saveInAFile(file);
        }
    }

    @Override
    public List<Product> getProductList(){
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductID().compareTo(p2.getProductID());
            }
        });
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}



