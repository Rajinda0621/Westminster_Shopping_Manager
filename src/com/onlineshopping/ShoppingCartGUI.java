package com.onlineshopping;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ShoppingCartGUI extends JFrame{

    private ShoppingCart shoppingCart;// Shopping cart object

    private static ShoppingCartGUI instance;// singleton instance for shopping cart GUI
    private DefaultTableModel cartTableModel;// Table model
    private JTable cartTable;

    // Labels to display total and discount prices
    private JLabel totalTxt = new JLabel("Total: ");
    private JLabel totalAmount = new JLabel("00.00");
    private JLabel firstPurchaseTxt = new JLabel("First Purchase Discount(10%): ");
    private JLabel firstPurchaseAmount = new JLabel("00.00");
    private JLabel sameCategoryTxt = new JLabel("Three items in same category discount(20%): ");
    private JLabel sameCategoryAmount = new JLabel("00.00");
    private JLabel finalTotalTxt = new JLabel("Final Total: ");
    private JLabel finalTotalAmount = new JLabel("00.00");
    private static int electronicCount;
    private static int clothingCount;


    public ShoppingCartGUI() {
        JPanel panel1 = new JPanel();// Creating the panel 1
        panel1.setBackground(new Color(170, 51, 106));


        cartTableModel = new DefaultTableModel(new String[]{"Product", "Quantity", "Price(£)"}, 0); // Creating table model
        cartTable = new JTable(cartTableModel);// Create table to display shopping cart products
        panel1.add(new JScrollPane(cartTable));// Adding table to a scroll pane




        JPanel panel2 = new JPanel();//Creating panel 2
        panel2.setLayout(new GridLayout(4,2));
        panel2.setBackground(new Color(207, 159, 255));
        //Adding labels to panel 2
        panel2.add(totalTxt);
        panel2.add(totalAmount);
        panel2.add(firstPurchaseTxt);
        panel2.add(firstPurchaseAmount);
        panel2.add(sameCategoryTxt);
        panel2.add(sameCategoryAmount);
        panel2.add(finalTotalTxt);
        panel2.add(finalTotalAmount);






        setLayout(new GridLayout(2,1));
        add(panel1);
        add(panel2);



    }

    public static ShoppingCartGUI getInstance() {
        if (instance == null) {// Check if an instance of shopping cart GUI already exist
            instance = new ShoppingCartGUI();//If not
        }
        return instance;// If there is, return instance
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        populateCartTable();// add the products from cart
        calculateTotal();// Calculate total
        if (Console.userExist == false) {
            calculateFirstPurchaseDiscount();//calculate first purchase discount
        }

    }

    public void populateCartTable(){
        cartTableModel.setRowCount(0);// Clear existing data in table
        for (Product product : shoppingCart.getListOfProducts()) {
            cartTableModel.addRow(new Object[]{product.getProductID()+" , " +product.getProductName(), " 1 ", product.getPrice()});//Add products to the table

        }
    }

    private void calculateTotal() {
        double total = getTotalPrice();



        totalAmount.setText("£"+Double.toString(total));
        if (Console.userExist == false) {  // Check for new user before calculating discount
            calculateFirstPurchaseDiscount();
        }
        calculateCategoryDiscount(); // To calculate category discount

        calculateFinalTotal(); // Update final total after discounts
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product product :shoppingCart.getListOfProducts()  ) {
            totalPrice += product.getPrice();// Calculate total price before discounts
        }
        return totalPrice;
    }

    public void calculateFirstPurchaseDiscount() {
        DecimalFormat df = new DecimalFormat("0.00");// To format double value to two decimal places
        double totalPrice = Double.parseDouble(totalAmount.getText().replaceAll("[^\\d.]", ""));// Get the total price from the totalAmount label and parsing it to a double
        double discount = totalPrice * 0.1;// Calculae 10% discount
        String formatted = df.format(discount);
        firstPurchaseAmount.setText("£"+formatted);// display discount
    }

    private void calculateCategoryDiscount() {
        // variables for count the category type
        electronicCount = 0;
        clothingCount = 0;
        DecimalFormat df = new DecimalFormat("0.00");
        for (Product product : shoppingCart.getListOfProducts()) {
            if (product instanceof Electronics) {
                electronicCount++;//If the product is electronics
            } else if (product instanceof Clothing) {
                clothingCount++;//If clothing
            }
        }
        if (electronicCount >= 3 || clothingCount >= 3) {// If there are 3 products in same category
            double totalPrice = Double.parseDouble(totalAmount.getText().replaceAll("[^\\d.]", ""));// Get the total price from the totalAmount label and parsing it to a double
            double discount = totalPrice * 0.2;// Calculating 20% discount
            String formatted = df.format(discount);
            sameCategoryAmount.setText("£"+formatted);
        } else {
            sameCategoryAmount.setText("£00.00");
        }
    }

    private void calculateFinalTotal() {
        double total = Double.parseDouble(totalAmount.getText().replaceAll("[^\\d.]", ""));// Get the total price from the totalAmount label and parsing it to a double
        double firstPurchaseDiscount = Double.parseDouble(firstPurchaseAmount.getText().replaceAll("[^\\d.]", ""));// Get the first purchase discount amount from the firstPurchaseAmount label and parsing it to a double
        double categoryDiscount = Double.parseDouble(sameCategoryAmount.getText().replaceAll("[^\\d.]", ""));// Get the same category discount amount from the sameCategoryAmount label and parsing it to a double

        double finalTotal = total - firstPurchaseDiscount - categoryDiscount;// Calculating final price
        finalTotalAmount.setText("£"+Double.toString(finalTotal));// Display final price
    }




}
