package com.onlineshopping;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Console{
    static ShoppingManager manager = new WestminsterShoppingManager(); //Manages products
    final static Scanner input = new Scanner(System.in);//user input

    static boolean userExist = false;//To check if the user exist

    public static void main(String[] args) throws IOException {
        // Load product data from file if it exists
        Path path = Paths.get("data.txt");
        if (Files.exists(path)){
            manager.loadFromFile("data.txt");// Loads product data from file
        }

        // Load user list from file
        try {
            User.loadUserListFromFile("users.txt");
        } catch (IOException e) {
            System.out.println("Error loading user list: " + e.getMessage());
        }

        System.out.println("--------------WESTMINSTER SHOPPING CENTER---------------------");
        System.out.println("\nWelcome Manager!       ");

        manager.loadFromFile("data.txt");

        menu:
        while (true){
            displayConsoleMenu();// Shows the menu of options

            try {

                int user_input = input.nextInt();// Gets user's choice from the menu

                switch (user_input){
                    case 1:
                        addProduct();// Calling method to add a new product
                        break;
                    case 2:
                        deleteProduct();//Calling delete product method
                        break;
                    case 3:
                        manager.printListOfProducts();//Print all the products
                        break;
                    case 4:
                        saveData();//Save data to a file
                        break;
                    case 5:
                        addUser();//Add user and start the GUI
                        break;
                    case 6:
                        System.out.println("Thank You!");
                        break menu;//Exit the application
                    default:
                        System.out.println("Invalid input. Try again");//For invalid inputs
                        break;
                }

            }catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                input.next();
            }

        }

    }

    public static void displayConsoleMenu(){ //Method to display console menu
        System.out.println("\nPlease select: ");
        System.out.println("\n1: Add a product");
        System.out.println("2: Delete a product");
        System.out.println("3: Display all the products");
        System.out.println("4: Save data");
        System.out.println("5: Run GUI");
        System.out.println("6: Exit");
    }

    public static void addProduct() {

        if (manager.getProductList().size() >= 50) {
            System.out.println("Maximum product limit (50) reached. Cannot add more products.");
            return;
        }

        Product product = null;//Hold the product to be added


        String type = ""; //Type of the input
        while (true) {
            System.out.println("Please choose the type of the product\n(A. Electronic/B. Clothing): ");
            type= input.next().toUpperCase();// Input for the category type

            if (type.equals("A") || type.equals("B")) {
                break;
            } else {//Error handling for category type input
                System.out.println("Invalid choice. Please enter A for Electronic or B for Clothing.");
            }
        }

        System.out.println("Enter product ID: ");
        String id = input.next().trim();//Get product type input


        System.out.println("Enter product name: ");
        String name = input.next();//Get the name of the product


        int noOfItems;
        while (true) {
            System.out.println("Enter no of items: ");
            try {
                noOfItems = Integer.parseInt(input.next().trim());// Get number of items input
                if (noOfItems > 0) {//Checking if the number is a positive
                    break;
                } else {//Input validation
                    System.out.println("Number of items must be greater than 0. Please try again.");
                }
            } catch (NumberFormatException e) {//Input validation
                System.out.println("Invalid input. Please enter a positive integer value.");
            }
        }


        double price;
        while (true) {
            System.out.println("Enter price: ");//Get the price of the product
            try {
                price = Double.parseDouble(input.next().trim());
                if (price > 0) {//Check if the input price is a positive or negative number
                    break;
                } else {//Input validation
                    System.out.println("Price must be greater than 0. Please try again.");
                }
            } catch (NumberFormatException e) {//input validation
                System.out.println("Invalid input.");
            }
        }

        switch (type) {
            case "A":// If the user choose Electronic as the product category type
                System.out.println("Enter brand: ");
                String brand = input.next();//Get the product brand


                int months;
                while (true) {
                    System.out.println("Enter warranty period (months): ");
                    try {
                        months = Integer.parseInt(input.next().trim());//Get the warranty period of product
                        if (months >= 0) {//Check if the warranty period is >= to 0
                            break;
                        } else {//input validation
                            System.out.println("Warranty period cannot be negative. Please enter a non-negative integer value.");
                        }
                    } catch (NumberFormatException e) {//input validation
                        System.out.println("Invalid input.");
                    }
                }

                product = new Electronics(id, name, noOfItems, price, brand, months);// Create electronic product object
                break;

            case "B":// If the user choose Clothing as the product category type

                String size;
                while (true) {
                    System.out.println("Enter size: \n(XS, S, M, L, XL)");
                    size = input.next().toUpperCase().trim();//Get the size of the clothing

                    if (Arrays.asList("XS", "S", "M", "L", "XL").contains(size)) {//Check if the given input is a valid clothing size
                        break;
                    } else {//Input validation
                        System.out.println("Invalid size. Please enter one of the following options: XS, S, M, L, XL");
                    }
                }

                System.out.println("Enter colour: ");
                String colour = input.next();//Get the colour of the clothing

                product = new Clothing(id, name, noOfItems, price, size, colour);//Create clothing product object
                break;

            default:// If the input given for product type category is invalid
                System.out.println("Invalid input. Try again");
                return;
        }

        // Add the product to the manager's list if it was created successfully
        if (product != null) {
            manager.addProduct(product);
            System.out.println("Product added successfully!");
        }
    }


public static void deleteProduct() {// Method for deleting a product
    boolean found = false;// Boolean variable to check if there is a product with the given product ID

    System.out.println("Please enter Product ID: ");
    String productID = input.next();// Get the product ID
    List<Product> productList = manager.getProductList();// Get the product list and assign it to a new list to check the product ID
    if (productList.size() > 0) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                found = true;// Assign true if there is a product with the given product ID
                manager.deleteProduct(productID);// Calling the delete product method in westminster shopping manager
                break;
            }
        }

        if (found == false) {//Input validation
            System.out.println("Invalid Product ID");
        }
    } else {// If there are no products added to the application yet
        System.out.println("No products added");
    }
}


    public static void saveData() throws IOException {
        manager.saveInAFile("data.txt");// Calling save in a file method in westminster shopping manager to save product data in a file
        System.out.println("Data saved successfully");// Print if saved successfully
    }

    public static void openGUI(){// Method to start the GUI

        GUIFrame frame = new GUIFrame();// Create a new frame object
        frame.setTitle("Westminster Shopping Centre");// Set the title of the GUI
        frame.setSize(700,600);// Set the size of the frame
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//To exit the GUI

    }

    public static void addUser(){// Method to add users

        System.out.println("Enter user name: ");
        String username = input.next();// Get the username

        if (User.userExists(username)) {//Check if the user exist
            User existingUser = User.getUserByUsername(username);
            System.out.println("Enter password: ");// Get the existing user's password
            String password = input.next();
            if (password.equals(existingUser.getPassword())) {//Check if the password matches
                System.out.println("Welcome, " + username + "!");
                userExist = true;// Assign the userExist boolean variable to true
                openGUI();// Calling method to start the GUI
            } else {
                System.out.println("Invalid password.");// If the given password does not match
            }
            return;
        }

        userExist = false;//Assign false if the user is new

        System.out.println("Enter password: ");
        String password = input.next();// Get the new user's password

        System.out.println("Welcome new user: "+ username);

        User user = new User(username,password);// Create a new user object
        user.addToUserList(user);// Adding to the user list


        try {
            User.saveUserListToFile("users.txt");// Save user data to a file
        } catch (IOException e) {
            System.out.println("Error saving user list: " + e.getMessage());// Error handling
        }

        openGUI();// Open the GUI
        if (userExist == false) {//If the user is new, give 10% discount
            ShoppingCartGUI.getInstance().calculateFirstPurchaseDiscount();
        }

    }


}